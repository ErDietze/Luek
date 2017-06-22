package de.zeitner.android.games.luek.fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Storage;
import de.zeitner.android.games.luek.widget.CustomMainList;

public class MainFragment extends Fragment implements OnClickListener {
	
	private final static String TAG = "MainFragment";
	
	private Locale myLocale;
	
	ListView list;
	View myView;
	ArrayList<String> cpacks = new ArrayList<String>();
	ArrayList<Drawable> cpackspreviews = new ArrayList<Drawable>();
	Storage storage;
	HashMap<String, String> cfg;
	
	private ListView getList() {
		return list;
	}

	private void setList(ListView list) {
		this.list = list;
	}

	private View getMyView() {
		return myView;
	}

	private void setMyView(View myView) {
		this.myView = myView;
	}
	
	private Storage getStorage() {
		return storage;
	}

	private void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	private HashMap<String, String> getCfg() {
		return cfg;
	}

	private void setCfg(HashMap<String, String> cfg) {
		this.cfg = cfg;
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
	    getActivity().getResources().updateConfiguration(config, this.getActivity().getResources().getDisplayMetrics());
	}
	
	/**
	 * Loads the current set language
	 * 
	 * @author Eray Soenmez
	 * @since 15.10.2014 
	 * @version 1.3
	 *
	 */
	public void loadLocale()	{
	    String langPref = "Language";
	    SharedPreferences prefs = getActivity().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
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
	    SharedPreferences prefs = getActivity().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(langPref, lang);
	    editor.commit();
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
	
	private void createContentPackCfg(String name){
		File file;
		String path = getActivity().getFilesDir().getAbsolutePath() + File.separator + name;
		try {
	        file = new File(path);
	        if(!file.exists() || !file.isFile()){
	        	this.getStorage().setPath(name);
				this.getStorage().writeToFile("show=all");
				this.getStorage().writeToFile("autoscroll=off");

				Log.v(TAG,"creating contentpack cfg: " + name);
	        }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	private void createDefaultConfig(){
		File file;
	
		try {
	        file = new File(getActivity().getFilesDir().getAbsolutePath() + File.separator + "config.txt");
	        if(!file.exists() || !file.isFile()){				
				this.getStorage().writeToFile("language=de");
				Log.v(TAG,"creating default config file");
	        }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.main_fragment, container, false);
		this.setMyView(myView);
		this.setStorage(new Storage(this.getMyView()));
		
		createDefaultConfig();
		this.setCfg(this.getStorage().getHashMap());
		
		changeLang(this.getCfg().get("language"));
		
		ImageView iv_main_language_settings = (ImageView)this.getMyView().findViewById(R.id.iv_main_language_settings);
		ImageView iv_exit_application = (ImageView)this.getMyView().findViewById(R.id.iv_exit_app);
		
		iv_main_language_settings.setOnClickListener(this);
		iv_exit_application.setOnClickListener(this);
		
		int id = this.getMyView().getResources().getIdentifier("lang_" + this.getCfg().get("language"), "drawable", "de.zeitner.android.games.flashcard");
		iv_main_language_settings.setImageResource(id);
		
	    cpacks = new ArrayList<String>(); 
	    cpackspreviews = new ArrayList<Drawable>();
	    
	    String[] fields;
		try {
			fields = this.getMyView().getContext().getAssets().list("contentpacks");
			for(String cpackname : fields){
				createContentPackCfg(cpackname + ".txt");
				cpacks.add(cpackname);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < cpacks.size(); i++){
			cpackspreviews.add(loadImageFromAsset(this.getMyView(), "contentpacks/" + cpacks.get(i) + "/de/gfx/preview.png"));
		}
		
		CustomMainList adapter = new CustomMainList(this.getActivity(), cpacks, cpackspreviews);
	    this.setList((ListView)myView.findViewById(R.id.listv_content));
        this.getList().setAdapter(adapter);
        this.getList().setDividerHeight(30);
        this.getList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Toast.makeText(MainFragment.this.getActivity(), "You clicked at " + cpacks.get(position), Toast.LENGTH_SHORT).show();
		      Bundle infos = new Bundle();
		      infos.putString("Package", cpacks.get(position));
              ModeFragment myModeFragment = new ModeFragment();
              FragmentManager fragmentManager = getFragmentManager();
              myModeFragment.setArguments(infos);
              if(fragmentManager.findFragmentById(R.layout.mode_fragment) == null){
            	  FragmentTransaction transaction = fragmentManager.beginTransaction();
            	  transaction.replace(R.id.MainActivityRelativeLayout, myModeFragment);
            	  transaction.addToBackStack("ModeFragment");
            	  transaction.commit();
              }
            }
        });
		return myView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.iv_main_language_settings: {
		        LanguageFragment myLanguageFragment = new LanguageFragment();
		        FragmentManager fragmentManager = getFragmentManager();
		        if(fragmentManager.findFragmentById(R.layout.language_fragment) == null){
			        FragmentTransaction transaction = fragmentManager.beginTransaction();
			        transaction.replace(R.id.MainActivityRelativeLayout, myLanguageFragment);
			        transaction.addToBackStack("LanguageFragment");
			        transaction.commit();
		        }
		        break;
			}
			case R.id.iv_exit_app: {
				getActivity().moveTaskToBack(true);
				getActivity().finish();
				System.exit(0);
				break;
			}
		}
	}
}
