package de.zeitner.android.games.luek.fragment;

import java.io.File;
import java.io.IOException;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Spiel;
import de.zeitner.android.games.luek.Storage;
import de.zeitner.android.games.luek.listener.HelpOnClickListener;
import de.zeitner.android.games.luek.widget.InfoboxImageView;

public class RecognizeFragment extends Fragment {
	
	private final static String TAG = "RecognizeFragment";
	private String packageName;
	
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.recognize_fragment, container, false);
		
		Bundle infos = getArguments();
		this.setPackageName(infos.getString("Package"));
		
		createStatsFile(myView);
		
		Spiel s = new Spiel((TableLayout)myView.findViewById(R.id.GameTableLayoutTop), 3, 2, myView, this.getPackageName());
		s.start();
		
		InfoboxImageView iv_infobox = (InfoboxImageView)myView.findViewById(R.id.iv_infobox);
		iv_infobox.setSpiel(s);
				
		ImageView iv_repeatsound = (ImageView)myView.findViewById(R.id.iv_repeatsound);
		iv_repeatsound.setOnClickListener(new HelpOnClickListener(s));
						
		ImageView iv_questionmark = (ImageView)myView.findViewById(R.id.iv_questionmark);
		iv_questionmark.setOnClickListener(new HelpOnClickListener(s));
		return myView;
	}
}
