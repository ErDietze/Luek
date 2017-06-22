package de.zeitner.android.games.luek.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Storage;

public class LearnFragment extends Fragment implements OnClickListener{
	
	/*
	 * Attributes
	 */
	
	final String TAG = "LearnFragment";
	
	/** Storage Object to access read and write methods from and to harddisk */
	private Storage storage;
	
	/** Current View Object */
	private View myView;
	
	/** Container of saved options */
	private HashMap<String, String> configData;
	
	private int packIndex;
	
	private String packageName;
	
	private ArrayList<String> configdata;
	
	private MediaPlayer pictureSound;
	
	private Thread thread;
		
	/*
	 * Getter & Setter
	 */
		
	private Storage getStorage() {
		return storage;
	}

	private void setStorage(Storage storage) {
		this.storage = storage;
	}

	private View getMyView() {
		return myView;
	}

	private void setMyView(View myView) {
		this.myView = myView;
	}

	private String getPackageName() {
		return packageName;
	}

	private void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	private HashMap<String, String> getConfigData() {
		return configData;
	}

	private void setConfigData(HashMap<String, String> configData) {
		this.configData = configData;
	}
	
	private int getPackIndex() {
		return packIndex;
	}

	private void setPackIndex(int packIndex) {
		this.packIndex = packIndex;
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
	
	public void loadContext(){
		
		/* Placeholder for the front picture */
		String pic = "";
		
		/* Placeholder for the name-sound file */
		String name = "";
		
		/* Placeholder for the description */
		String description = "";
		
		pic = "contentpacks/" + this.getPackageName() + "/de/gfx/front/" + configdata.get(this.getPackIndex()).split(",")[0];
		name = "contentpacks/" + this.getPackageName() + "/de/sfx/name/" + configdata.get(this.getPackIndex()).split(",")[1];
		description = configdata.get(this.getPackIndex()).split(",")[2];
		
		TextView tv_learn_picturename = (TextView)myView.findViewById(R.id.tv_learn_picturename);
		ImageView iv_learn_current_picture = (ImageView)myView.findViewById(R.id.iv_learn_current_picutre);
		
		MediaPlayer	sound = new MediaPlayer();	
		try {
			AssetFileDescriptor afd = myView.getContext().getAssets().openFd(name);
			sound.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
			sound.prepare();
			pictureSound = sound;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		iv_learn_current_picture.setImageDrawable(loadImageFromAsset(myView, pic));
		tv_learn_picturename.setText(description);
		sound.start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView;
		myView = inflater.inflate(R.layout.learn_fragment, container, false);
		this.setMyView(myView);
		this.setStorage(new Storage(this.getMyView()));
		this.setPackIndex(0);
		pictureSound = new MediaPlayer();
		
		Bundle infos = getArguments();
		this.setPackageName(infos.getString("Package"));
		
		this.getStorage().setPath(this.getPackageName() + ".txt");
		HashMap<String, String> cpackSettings = new HashMap<String, String>();
		cpackSettings = this.getStorage().getHashMap();
		
		/* read config and settings file of the choosen contentpack */
		String configfile = this.loadDataFromAsset(getMyView(), "contentpacks/" + this.getPackageName() + "/de/config.txt");
		//String settingsfile = this.loadDataFromAsset(getMyView(), "contentpacks/" + this.getPackageName() + "/de/" + this.getPackageName() + ".txt");
		
//		/* Split it and add the values into a hashmap to use it like an INI-file */
//		final HashMap<String, String> cpackSettings = new HashMap<String, String>();
//		String[] settingslines = settingsfile.split("\r\n");
//		
//		/* add left and right of = to a hashmap */
//		for(int i = 0; i < settingslines.length; i++){
//			String[] pairs = settingslines[i].split("=");
//			cpackSettings.put(pairs[0], pairs[1]);
//		}
		
		/* Split it and add the values into a hashmap to use it like an INI-file */
		HashMap<String, String> cpackConfig = new HashMap<String, String>();
		String[] configlines = configfile.split("\r\n");
		
		/* add left and right of = to a hashmap */
		for(int i = 0; i < configlines.length; i++){
			String[] pairs = configlines[i].split("=");
			cpackConfig.put(pairs[0], pairs[1]);
		}
		
		/* make pairs between picture, sound */
		configdata = new ArrayList<String>();
		for(int i = 0; i < cpackConfig.size(); i++){
			if(cpackConfig.get("pic"+(i+1)) != null && cpackConfig.get("name"+(i+1)) != null)
				if(cpackSettings.get("show").contains(cpackConfig.get("pic"+(i+1))) || cpackSettings.get("show").equals("all"))
					configdata.add(cpackConfig.get("pic"+(i+1))+","+cpackConfig.get("name"+(i+1))+","+cpackConfig.get("text"+(i+1)));
		}
		Collections.shuffle(configdata);
				
		loadContext();
		
		TextView tv_learn_top_text = (TextView)myView.findViewById(R.id.tv_learn_top_text); 
		ImageView iv_learn_exit = (ImageView)myView.findViewById(R.id.iv_learn_exit);
		ImageView iv_arrowleft = (ImageView)myView.findViewById(R.id.iv_arrowleft);
		final ImageView iv_arrowright = (ImageView)myView.findViewById(R.id.iv_arrowright);
		ImageView iv_learn_repeatsound = (ImageView)myView.findViewById(R.id.iv_learn_repeatsound);
		
		iv_learn_exit.setOnClickListener(this);
		iv_arrowleft.setOnClickListener(this);
		iv_arrowright.setOnClickListener(this);
		iv_learn_repeatsound.setOnClickListener(this);
	
//		iv_arrowleft.setVisibility(View.INVISIBLE);
//		iv_arrowright.setVisibility(View.VISIBLE);
		
		if(!cpackSettings.get("autoscroll").equals("off")){
			final int timer = Integer.valueOf(cpackSettings.get("autoscroll"))*1000;
			this.thread = new Thread(){
			    @Override
			    public void run() {
		            while(LearnFragment.this.getPackIndex() < configdata.size()-2 && !Thread.currentThread().isInterrupted()) {
		                if(!Thread.currentThread().isInterrupted()){
		                	try {
		                		sleep(timer);
			                	getActivity().runOnUiThread(new Runnable() {
								    @Override
								    public void run() {
								    	iv_arrowright.performClick();
								    }
								});
		                	} catch (InterruptedException e){
					            e.printStackTrace();
					            Thread.currentThread().interrupt();
		                	}
		                }
		            }
			    }
			};

			thread.start();
		}
		
		return myView;
	}

	@Override
	public void onClick(View v) {
//		ImageView iv_arrowleft = (ImageView)myView.findViewById(R.id.iv_arrowleft);
//		ImageView iv_arrowright = (ImageView)myView.findViewById(R.id.iv_arrowright);
				
		switch(v.getId()){
			case R.id.iv_arrowleft: {
				if(this.getPackIndex() == 0){
					this.setPackIndex(this.configdata.size()-1);
				} else {
					this.setPackIndex(this.getPackIndex()-1);
				}
				
				loadContext();
				break;
			}
			case R.id.iv_arrowright: {
				if(this.getPackIndex() == this.configdata.size()-1){
					this.setPackIndex(0);
				} else {
					this.setPackIndex(this.getPackIndex()+1);
				}

				loadContext();
				break;
			}
			case R.id.iv_learn_repeatsound: {
				this.pictureSound.start();
				break;
			}
			case R.id.iv_learn_exit: {
				
				FragmentManager fm = ((Activity) this.getMyView().getContext()).getFragmentManager();
				fm.popBackStack();
			
				break;
			}
		}
		
//		if(this.getPackIndex() == 0){
//			iv_arrowleft.setVisibility(View.INVISIBLE);
//			iv_arrowright.setVisibility(View.VISIBLE);
//		} else {
//			if(this.getPackIndex() == configdata.size()-1){
//				iv_arrowleft.setVisibility(View.VISIBLE);
//				iv_arrowright.setVisibility(View.INVISIBLE);
//			} else {
//				iv_arrowleft.setVisibility(View.VISIBLE);
//				iv_arrowright.setVisibility(View.VISIBLE);
//			}
//		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(this.thread != null)
			this.thread.interrupt();
	}
}
