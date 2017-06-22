package de.zeitner.android.games.luek.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Storage;
import de.zeitner.android.games.luek.listener.LanguageOnClickListener;
import de.zeitner.android.games.luek.widget.ExtendedImageView;

public class LanguageFragment extends Fragment implements OnClickListener {
	
	/*
	 * Attributes
	 */
	
	final String TAG = "LanguageFragment";
	
	/** Storage Object to access read and write methods from and to harddisk */
	private Storage storage;
	
	/** Current View Object */
	private View myView;
	
	/** Container of saved options */
	private HashMap<String, String> configData;
	
	/** Container of dynamically generated / created ImageViews */
	private ArrayList<ExtendedImageView> generatedImageViews;
	
	/*
	 * Getter & Setter
	 */
	
	private Storage getStorage() {
		return this.storage;
	}

	private void setStorage(Storage storage) {
		this.storage = storage;
	}

	private View getMyView() {
		return this.myView;
	}

	private void setMyView(View myView) {
		this.myView = myView;
	}

	private HashMap<String, String> getConfigData() {
		return this.configData;
	}

	private void setConfigData(HashMap<String, String> configData) {
		this.configData = configData;
	}
		
	private ArrayList<ExtendedImageView> getGeneratedImageViews() {
		return this.generatedImageViews;
	}

	private void setGeneratedImageViews(ArrayList<ExtendedImageView> generatedImageViews) {
		this.generatedImageViews = generatedImageViews;
	}
	
	/**
	 * This methods grabs the file (text) from the assets set
	 * and returns the content as String. 
	 * 
	 * @author Eray Soenmez
	 * @since 16.10.2014 
	 * @version 1.3
	 * 
	 * @param myView			Current View
	 * @param filename			Fullpath with filename.extension
	 * @return String			text of the file
	 */
	public String loadDataFromAsset(View myView, String filename){
	  	String text = "";
        try {
            InputStream is = myView.getContext().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        }
        catch (IOException ex) {
            Log.v(TAG, ex.getMessage());
        }
		return text;
	}
	
	/**
	 * This methods grabs the file (image) from the assets set
	 * and returns as Drawable. 
	 * 
	 * @author Eray Soenmez
	 * @since 16.10.2014 
	 * @version 1.3
	 * 
	 * @param myView			Current View
	 * @param filename			Fullpath with filename.extension
	 * @return Drawable			image
	 */
	public Drawable loadImageFromAsset(View myView, String filename){
	  	Drawable image = null;
	  	 try {
	            InputStream ims = myView.getContext().getAssets().open(filename);
	            image = Drawable.createFromStream(ims, null);
	        }
	        catch(IOException ex) {
	            Log.v(TAG, ex.getMessage());
	        }
	  	 return image;
	}

	/**
	 * This method sets for all elements in this View
	 * the right image for the current theme.
	 * 
	 * @author Eray Soenmez
	 * @since 16.10.2014 
	 * @version 1.3
	 * 
	 * @param myView
	 */
	private void loadTheme(View myView){	
		/* LanguageFragment Elements */
		
//		TableLayout langfrag = (TableLayout)myView.findViewById(R.id.LanguageTableLayout);
//		langfrag.setBackgroundDrawable(loadImageFromAsset(myView, "themes/" + this.getConfigData().get("theme") + "/appbackground/languagescreen.png"));
//		
//		ImageButton btn_language_exit = (ImageButton) myView.findViewById(R.id.btn_language_exit);
//		btn_language_exit.setBackgroundDrawable(loadImageFromAsset(myView, "themes/" + this.getConfigData().get("theme") +"/button/exit.png"));
	}

	/**
	 * Standard Override of the onCreateView method.
	 * It operates like a constructor by initialising some
	 * data, which will be used here.
	 * 
	 * @author Eray Soenmez
	 * @since 14.10.2014 
	 * @version 1.3
	 *
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView;
		myView = inflater.inflate(R.layout.language_fragment, container, false);
		this.setMyView(myView);
		this.setStorage(new Storage(this.getMyView()));
		this.setConfigData(this.getStorage().getHashMap());
		this.setGeneratedImageViews(new ArrayList<ExtendedImageView>());
		
		ImageButton btn_language_exit = (ImageButton)this.getMyView().findViewById(R.id.btn_language_exit);
		btn_language_exit.setOnClickListener(new LanguageOnClickListener(this.getMyView(), this.getGeneratedImageViews()));
		
		this.addFlags();
		//this.loadTheme(myView);
		this.scaleContents(myView);
		
		return myView;
	}
	
	/**
	 * Method to scale the objects of this fragment.
	 * The current View Object is required to get the
	 * elements by id.
	 * 
	 * @author Eray Soenmez
	 * @since 30.09.2014 
	 * @version 1.2
	 * 
	 * @param View 				myView
	 */
	private	void scaleContents(View rootView){
		// Compute the scaling ratio
		DisplayMetrics metrics = getResources().getDisplayMetrics();

		final double YSIZE = metrics.heightPixels / metrics.ydpi;
		final double XSIZE = metrics.widthPixels / metrics.xdpi;

		final double SCREENSIZE = Math.sqrt(XSIZE * XSIZE + YSIZE * YSIZE);
		
		final double BASESCALE = 10.0;
		final double SCALE = 1.0d * (SCREENSIZE / BASESCALE);
		
		scaleObjects(rootView, SCALE, SCALE*1.8);
	} 
	
	private void scaleObjects(View myView, double TEXTSCALE, double IMAGESCALE){
		
		ViewGroup.LayoutParams layoutParams = myView.getLayoutParams();
		if (layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT && layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT){
			layoutParams.width	*= IMAGESCALE;
		}
		
		if (layoutParams.height	!= ViewGroup.LayoutParams.MATCH_PARENT && layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT){
			layoutParams.height	*= IMAGESCALE;
		}
		
		if (layoutParams instanceof	ViewGroup.MarginLayoutParams){
			ViewGroup.MarginLayoutParams marginParams =	(ViewGroup.MarginLayoutParams)layoutParams;
			marginParams.leftMargin	*= IMAGESCALE;
			marginParams.rightMargin *= IMAGESCALE;
			marginParams.topMargin *= IMAGESCALE;
			marginParams.bottomMargin *= IMAGESCALE;
		}
		
		myView.setLayoutParams(layoutParams);
		
		myView.setPadding(
				(int)(myView.getPaddingLeft() * IMAGESCALE),
				(int)(myView.getPaddingTop() * IMAGESCALE),
				(int)(myView.getPaddingRight() * IMAGESCALE),
				(int)(myView.getPaddingBottom() * IMAGESCALE)
		);

		if(myView instanceof TextView){
			TextView textView = (TextView)myView;
			textView.setTextSize((float) (textView.getTextSize() * TEXTSCALE));
		}

		if(myView instanceof ViewGroup){
			ViewGroup groupView = (ViewGroup)myView;
			for(int	i = 0; i < groupView.getChildCount(); ++i)
				scaleObjects(groupView.getChildAt(i), TEXTSCALE, IMAGESCALE);
		} 
	}
	
	/**
	 * Adds all available languages as pictures into a TableLayout
	 * It must be there a string named lang_x in strings.xml
	 * x stands for the language code like "de" or "en"
	 * Example: lang_de, lang_en, lang_tr
	 * 
	 * @author Eray Soenmez
	 * @since 15.10.2014 
	 * @version 1.3
	 *
	 */
	private void addFlags(){
		
		/* 
		 * Get all strings which start with "lang"
		 * They will be added to a container 
		 */
	    Field[] fields = de.zeitner.android.games.luek.R.string.class.getFields();
	    List<String> languages = new ArrayList<String>();
	    for (Field field : fields) {
	    	if(field.getName().startsWith("lang")){
	    		languages.add(field.getName());
	    		Log.v(TAG, field.getName());
	    	}
	    }
	    
	    TableLayout LanguageContentTableLayout = (TableLayout) this.getMyView().findViewById(R.id.LanguageContentTableLayout);	    
		
		for(int i = 0; i < languages.size(); i++){	
			TableRow tr = new TableRow(this.getMyView().getContext());
			tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));			
			ExtendedImageView iv = new ExtendedImageView(this.getMyView().getContext());
				
			int id = this.getMyView().getResources().getIdentifier(languages.get(i), "drawable", "de.zeitner.android.games.flashcard");
			iv.setImageResource(id);
			
			/* Mark the right image which fits the current language */
			if(Locale.getDefault().getLanguage().equals(languages.get(i).split("_")[1].toString())){
		        GradientDrawable gd = new GradientDrawable();
		        gd.setCornerRadius(5);
		        gd.setStroke(15, 0xFFFFFF00);
		       
		        iv.setPadding(5, 5, 5, 5);
		        iv.setBackgroundDrawable(gd);
			}
		
			iv.setMaxWidth(400);
	        iv.setMaxHeight(200);
	        iv.setMinimumWidth(400);
	        iv.setMinimumHeight(200);
			iv.setId(id);
			iv.setName(languages.get(i).split("_")[1].toString());
			iv.setOnClickListener(new LanguageOnClickListener(this.getMyView(), this.getGeneratedImageViews()));
			
			TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
			lp.setMargins(10, 10, 10, 10);
	
			iv.setLayoutParams(lp);
			
			this.getGeneratedImageViews().add(iv);
			tr.addView(iv);
			LanguageContentTableLayout.addView(tr);
		}		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_language_exit: {
				FragmentManager fm = ((Activity) this.getMyView().getContext()).getFragmentManager();
				fm.popBackStack();
			}
		}
	}
}
