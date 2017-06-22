package de.zeitner.android.games.luek.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Storage;
import de.zeitner.android.games.luek.widget.CustomSettingsList;
import de.zeitner.android.games.luek.widget.ExtendedImageView;

public class SettingsFragment extends Fragment implements OnClickListener{
	
	private final static String TAG = "SettingsFragment";
	
	View myView;
	String packageName;
	ListView list;
	ArrayList<Drawable> cpackitems;
	Storage storage;
	CustomSettingsList adapter;

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

	private ListView getList() {
		return list;
	}

	private void setList(ListView list) {
		this.list = list;
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.settings_fragment, container, false);
		this.setMyView(myView);
		this.storage = new Storage(this.myView);
		
		Bundle infos = getArguments();
		this.setPackageName(infos.getString("Package"));
		
		this.storage.setPath(this.getPackageName() + ".txt");
		HashMap<String, String> settings = new HashMap<String, String>();
		settings = this.storage.getHashMap();
		
		Log.v(TAG, this.getPackageName());
		
		Switch autoscroll = (Switch)this.getMyView().findViewById(R.id.switch_settings_autoscroll);
		RadioButton radio_5 = (RadioButton)this.getMyView().findViewById(R.id.radio0);
		RadioButton radio_10 = (RadioButton)this.getMyView().findViewById(R.id.radio1);
		CheckBox cb_all = (CheckBox)this.getMyView().findViewById(R.id.cb_settings_all);
		ImageView iv_settings_exit = (ImageView)this.getMyView().findViewById(R.id.iv_settings_exit);
		
		autoscroll.setOnClickListener(this);
		radio_5.setOnClickListener(this);
		radio_10.setOnClickListener(this);
		cb_all.setOnClickListener(this);
		iv_settings_exit.setOnClickListener(this);
		
		if(settings != null && settings.get("autoscroll").equals("off")){
			autoscroll.setChecked(false);
		} else {
			autoscroll.setChecked(true);
				
			if(settings.get("autoscroll").equals("5")){
				radio_5.setChecked(true);
			} else {
				radio_10.setChecked(true);
			}
		}
		
		if(autoscroll.isChecked()){
			radio_5.setVisibility(View.VISIBLE);
			radio_10.setVisibility(View.VISIBLE);
		} else {
			radio_5.setVisibility(View.GONE);
			radio_10.setVisibility(View.GONE);
		}
		
	    cpackitems = new ArrayList<Drawable>();
	    ArrayList<String> name = new ArrayList<String>();
	    
	    String[] fields;
		try {
			fields = this.getMyView().getContext().getAssets().list("contentpacks/" + this.getPackageName() + "/de/gfx/front");
			for(String cpackitem : fields){
				cpackitems.add(loadImageFromAsset(this.getMyView(), "contentpacks/" + this.getPackageName() + "/de/gfx/front/" + cpackitem));
				name.add(cpackitem);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Debug only
//		for(int i = 0; i < name.size(); i++){
//			Log.v(TAG, name.get(i));
//		}
		
		this.setList((ListView)myView.findViewById(R.id.listv_settings));
		adapter = new CustomSettingsList(this.getActivity(), name, cpackitems, this.getList());
		this.getList().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        this.getList().setAdapter(adapter);
        			                
		return this.getMyView();
	}
	
//	private void toggleCheckBoxState(ViewGroup vg) {
//		CheckBox cb_all = (CheckBox)this.getMyView().findViewById(R.id.cb_settings_all);
//		
//	    for (int i = 0; i < vg.getChildCount(); i++) {
//	        View v = vg.getChildAt(i);
//	        if (v instanceof CheckBox) {
//	            ((CheckBox) v).setChecked(cb_all.isChecked());
//	        } else if (v instanceof ViewGroup) {
//	            toggleCheckBoxState((ViewGroup) v);
//	        }
//	    }
//	}

	@Override
	public void onClick(View v) {
		RadioButton radio_5 = (RadioButton)this.getMyView().findViewById(R.id.radio0);
		RadioButton radio_10 = (RadioButton)this.getMyView().findViewById(R.id.radio1);
		
		switch(v.getId()){
			case R.id.switch_settings_autoscroll:{
				Switch autoscroll = (Switch)v;
				
				if(autoscroll.isChecked()){
					radio_5.setVisibility(View.VISIBLE);
					radio_10.setVisibility(View.VISIBLE);
				} else {
					radio_5.setVisibility(View.GONE);
					radio_10.setVisibility(View.GONE);
				}
				break;
			}
			case R.id.radio0:{
				radio_10.setChecked(false);
				break;
			}
			case R.id.radio1:{
				radio_5.setChecked(false);
				break;
			}
			case R.id.cb_settings_all:{				
	            for(int i = 0; i < adapter.getCount(); i++){
	            	this.getList().setItemChecked(i, ((CheckBox)v).isChecked());
	            }
				
//				ListView lv = getList();
//			    int size = lv.getAdapter().getCount();
//			        if(((CheckBox)v).isChecked()){
//			            for(int i = 0; i <= size; i++){
//			            	lv.setItemChecked(i, true);
//			            }
//			        } else {
//			            for(int i = 0; i <= size; i++){
//			                lv.setItemChecked(i, false);
//			            }
//			        }
			        
	            ((CustomSettingsList)getList().getAdapter()).notifyDataSetChanged();
				break;
			}
			case R.id.iv_settings_exit:{
				this.storage.setPath(this.getPackageName() + ".txt");
				HashMap<String, String> settings = new HashMap<String, String>();
				settings = this.storage.getHashMap();
				CheckBox chk_all = (CheckBox) this.getMyView().findViewById(R.id.cb_settings_all);
			    String pics = "all";
			    String timer = "off";
			    
			    if(chk_all.isChecked()){
			    	pics = "all";
			    } else {
					SparseBooleanArray checked = this.getList().getCheckedItemPositions();
					if(checked != null){
						for (int i = 0; i <= checked.size(); i++){
						    if (checked.get(i)){
						    	CheckBox check = (CheckBox) this.getList().getChildAt(i).findViewById(R.id.cb_settings_item_choose);
						    	if(check != null && check.isChecked()){
						    	    ExtendedImageView pic = (ExtendedImageView) this.getList().getChildAt(i).findViewById(R.id.iv_settings_item_preview);
						    	    pics += pic.getName() + ",";
						    	}
						    }
						}
					}
			    }
				
				String data = "";
				if(pics.length() == 0){
					data = "all";
				} else {
					if(pics.equals("all")){
						data = pics;
					} else {
						data = pics.substring(0, pics.length()-1);
					}
				}

			    Switch autoscroll = (Switch)this.getMyView().findViewById(R.id.switch_settings_autoscroll);
			    if(autoscroll.isChecked()){
			    	if(radio_5.isChecked()){
			    		timer = "5";
			    	} else {
				    	if(radio_10.isChecked()){
				    		timer = "10";
				    	}
			    	}
			    }
			    
			    this.storage.replaceFileString("show=" + settings.get("show"), "show=" + data);
			    this.storage.replaceFileString("autoscroll=" + settings.get("autoscroll"), "autoscroll=" + timer);
			    
				FragmentManager fm = ((Activity) this.getMyView().getContext()).getFragmentManager();
				fm.popBackStack();
			    
				break;
			}
		}
		
	}
}
