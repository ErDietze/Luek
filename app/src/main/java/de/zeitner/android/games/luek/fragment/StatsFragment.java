package de.zeitner.android.games.luek.fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Stats;
import de.zeitner.android.games.luek.Storage;
import de.zeitner.android.games.luek.widget.CustomStatsList;

public class StatsFragment extends Fragment implements OnClickListener{
	
	private final static String TAG = "StatsFragment";
	
	View myView;
	ListView list;
	Storage storage;
	CustomStatsList adapter;
	String packageName;
	
	private View getMyView() {
		return myView;
	}

	private void setMyView(View myView) {
		this.myView = myView;
	}

	private ListView getList() {
		return list;
	}

	private void setList(ListView list) {
		this.list = list;
	}

	private Storage getStorage() {
		return storage;
	}

	private void setStorage(Storage storage) {
		this.storage = storage;
	}

	private CustomStatsList getAdapter() {
		return adapter;
	}

	private void setAdapter(CustomStatsList adapter) {
		this.adapter = adapter;
	}

	private String getPackageName() {
		return packageName;
	}

	private void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	private void createStatsFile(View myView){
		Storage s = new Storage(myView);
		File file;
		String path = getActivity().getFilesDir().getAbsolutePath() + File.separator + this.getPackageName() + "-stats.txt";
		try {
	        file = new File(path);
	        if(!file.exists() || !file.isFile()){
	        	
	        	s.setPath(this.getPackageName() + "-stats.txt");
	        	
	    	    String[] fields;
	    		try {
	    			fields = myView.getContext().getAssets().list("contentpacks/" + this.getPackageName() + "/de/gfx/front");
	    			for(String cpackitem : fields){
	    				s.writeToFile(cpackitem + "=0#0");
	    			}
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}

				Log.v(TAG,"creating stats cfg: " + this.getPackageName() + "-stats.txt");
	        }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	private void deleteStatsFile(){
		String path = getActivity().getFilesDir().getAbsolutePath() + File.separator + this.getPackageName() + "-stats.txt";
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
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
		View myView = inflater.inflate(R.layout.stats_fragment, container, false);
		this.setMyView(myView);
	
		Bundle infos = getArguments();
		this.setPackageName(infos.getString("Package"));
		
		createStatsFile(myView);
		
		ArrayList<Drawable> cpackitems = new ArrayList<Drawable>();
		ArrayList<String> cpacknames = new ArrayList<String>();
		ArrayList<String> statvalues = new ArrayList<String>();
	    Stats stats = new Stats(myView, this.getPackageName()+"-stats.txt");
	    String[] fields;
		try {
			fields = this.getMyView().getContext().getAssets().list("contentpacks/" + this.getPackageName() + "/de/gfx/front");
			for(String cpackitem : fields){
				cpackitems.add(loadImageFromAsset(this.getMyView(), "contentpacks/" + this.getPackageName() + "/de/gfx/front/" + cpackitem));
				cpacknames.add(cpackitem);
				statvalues.add(stats.content.get(cpackitem));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageView iv_stats_img = (ImageView) this.getMyView().findViewById(R.id.iv_stats_img);
		TextView tv_stats_cptext = (TextView) this.getMyView().findViewById(R.id.tv_stats_cptext);
		ImageView iv_stats_exit = (ImageView) this.getMyView().findViewById(R.id.iv_stats_exit);
		Button btn_reset_stats = (Button) this.getMyView().findViewById(R.id.btn_reset_stats);
		btn_reset_stats.setOnClickListener(this);
		iv_stats_exit.setOnClickListener(this);
        
		tv_stats_cptext.setText(this.getPackageName());
		iv_stats_img.setImageDrawable(loadImageFromAsset(this.getMyView(), "contentpacks/" + this.getPackageName() + "/de/gfx/preview.png"));
		
		this.setList((ListView)myView.findViewById(R.id.lv_stats));
		this.setAdapter(new CustomStatsList(this.getActivity(), cpacknames, this.getPackageName(), cpackitems, statvalues, this.getList()));
        this.getList().setAdapter(this.getAdapter());
        
		return myView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_reset_stats: {
				deleteStatsFile();
				createStatsFile(getMyView());
				this.getList().invalidate();
				break;
			}
			case R.id.iv_stats_exit:{
				FragmentManager fm = ((Activity) this.getMyView().getContext()).getFragmentManager();
				fm.popBackStack();
				break;
			}
		}
	}
}
