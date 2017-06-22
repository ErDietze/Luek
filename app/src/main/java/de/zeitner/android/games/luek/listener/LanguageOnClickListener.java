package de.zeitner.android.games.luek.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Storage;
import de.zeitner.android.games.luek.widget.ExtendedImageView;

public class LanguageOnClickListener implements OnClickListener {

	/*
	 * Attributes
	 */
	
	private Locale myLocale;
	
	/** Storage Object to access read and write methods from and to harddisk */
	private Storage storage;
	
	/** Current View Object */
	private View myView;
	
	/** Container of saved options */
	private HashMap<String, String> configData;
	
	/** Container of dynamically generated / created ImageViews */
	private ArrayList<ExtendedImageView> generatedImageViews;
	
	private static String choosenLanguage;
	
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
		return configData;
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
	
	/*
	 * Constructors
	 */
	
	public LanguageOnClickListener(View myView, ArrayList<ExtendedImageView> aiv) {
		this.setMyView(myView);
		this.setStorage(new Storage(this.getMyView()));
		this.setConfigData(this.getStorage().getHashMap());
		this.setGeneratedImageViews(aiv);
		this.choosenLanguage = this.getConfigData().get("language");
	}
	
	public LanguageOnClickListener() {
		this(new View(null), new ArrayList<ExtendedImageView>());
	}
	
	/**
	 * Changes the current language to given code
	 * 
	 * @author Eray Soenmez
	 * @since 15.10.2014 
	 * @version 1.3
	 * 
	 * @param lang		language code like "de" or "en" as String
	 */
	public void changeLang(String lang)	{
	    if (lang.equalsIgnoreCase(""))
	    	return;
	    myLocale = new Locale(lang);
	    saveLocale(lang);
	    Locale.setDefault(myLocale);
	    android.content.res.Configuration config = new android.content.res.Configuration();
	    config.locale = myLocale;
	    this.getMyView().getContext().getResources().updateConfiguration(config, this.getMyView().getContext().getResources().getDisplayMetrics());
	}
	
	/**
	 * Loads the current set language
	 * 
	 * @author Eray Soenmez
	 * @since 15.10.2014 
	 * @version 1.3
	 *
	 */
	public void loadLocale() {
	    String langPref = "Language";
	    SharedPreferences prefs = this.getMyView().getContext().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
	    String language = prefs.getString(langPref, "");
	    changeLang(language);
	}
	
	/**
	 * Saves the language to given code
	 * 
	 * @author Eray Soenmez
	 * @since 15.10.2014 
	 * @version 1.3
	 * 
	 * @param lang		language code like "de" or "en" as String
	 */
	public void saveLocale(String lang)	{
	    String langPref = "Language";
	    SharedPreferences prefs = this.getMyView().getContext().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(langPref, lang);
	    editor.commit();
	}
	
	/**
	 * Standard Override of the onClick method.
	 * It defines what happens if the user clicks/touches
	 * an image (flag).
	 * 
	 * @author Eray Soenmez
	 * @since 14.10.2014 
	 * @version 1.3
	 *
	 */
	@Override
	public void onClick(View v) {
		
		/* Mark the touched image with a border */
		for(int i = 0; i < this.getGeneratedImageViews().size(); i++){
	        GradientDrawable gd = new GradientDrawable();
	        gd.setCornerRadius(0);
	        gd.setStroke(0, 0xFFFFFF00);
	       
	        this.getGeneratedImageViews().get(i).setPadding(0, 0, 0, 0);
			this.getGeneratedImageViews().get(i).setBackgroundDrawable(gd);
			
			if(this.getGeneratedImageViews().get(i).getId() == v.getId()){
		        gd.setCornerRadius(5);
		        gd.setStroke(15, 0xFFFFFF00);
		       
		        this.getGeneratedImageViews().get(i).setPadding(5, 5, 5, 5);
				this.getGeneratedImageViews().get(i).setBackgroundDrawable(gd);
				this.choosenLanguage = ((ExtendedImageView)v).getName();
				Toast.makeText(this.getMyView().getContext(), this.choosenLanguage, Toast.LENGTH_SHORT).show();
			}
		}
		
		/* ISSUE this has to be in an own listener! */
		switch(v.getId()){
			case R.id.btn_language_exit: {
				changeLang(this.choosenLanguage);
				this.getStorage().replaceFileString("language="+this.getConfigData().get("language"), "language="+Locale.getDefault().getLanguage());
				//Toast.makeText(this.getMyView().getContext(), this.getMyView().getContext().getString(R.string.settingssavedtext) + " (" + Locale.getDefault().getLanguage() + ")", Toast.LENGTH_SHORT).show();
				
				FragmentManager fm = ((Activity) this.getMyView().getContext()).getFragmentManager();
				fm.popBackStack();
				break;
			}
		}
	}
}
