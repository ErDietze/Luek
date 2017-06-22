package de.zeitner.android.games.luek.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.fragment.SettingsFragment;
import de.zeitner.android.games.luek.fragment.StatsFragment;

public class CustomMainList extends ArrayAdapter<String>{
	
	private final static String TAG = "CustomMainList";
	
	private Activity context;
	private ArrayList<String> cpacknames;
	private ArrayList<Drawable> cpackspreviews;
		
	public CustomMainList(Activity context,	ArrayList<String> cpacknames, ArrayList<Drawable> cpackspreviews) {
		super(context, R.layout.list_item, cpacknames);
		this.context = context;
		this.cpacknames = cpacknames;
		this.cpackspreviews = cpackspreviews;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_item, null, true);
		
		final TextView tv_title = (TextView) rowView.findViewById(R.id.tv_cptext);
		ImageView iv_status = (ImageView) rowView.findViewById(R.id.iv_status);
		ImageView iv_preview = (ImageView) rowView.findViewById(R.id.iv_img);
		ImageView iv_settings = (ImageView) rowView.findViewById(R.id.iv_settings);
		ImageView iv_stats = (ImageView) rowView.findViewById(R.id.iv_stats);

		tv_title.setText(cpacknames.get(position));
		iv_preview.setImageDrawable(cpackspreviews.get(position));
		
		iv_settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        SettingsFragment mySettingsFragment = new SettingsFragment();
		        FragmentManager fragmentManager = context.getFragmentManager();
		        Bundle infos = new Bundle();
		        infos.putString("Package", tv_title.getText().toString());
		        mySettingsFragment.setArguments(infos);
		        if(fragmentManager.findFragmentById(R.layout.settings_fragment) == null){
		  	        FragmentTransaction transaction = fragmentManager.beginTransaction();
		  	        transaction.replace(R.id.MainActivityRelativeLayout, mySettingsFragment);
		  	        transaction.addToBackStack("SettingsFragment");
		  	        transaction.commit();
		        }
			}
		});
		
		iv_stats.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        StatsFragment myStatsFragment = new StatsFragment();
		        FragmentManager fragmentManager = context.getFragmentManager();
		        Bundle infos = new Bundle();
		        infos.putString("Package", tv_title.getText().toString());
		        myStatsFragment.setArguments(infos);
		        if(fragmentManager.findFragmentById(R.layout.stats_fragment) == null){
		  	        FragmentTransaction transaction = fragmentManager.beginTransaction();
		  	        transaction.replace(R.id.MainActivityRelativeLayout, myStatsFragment);
		  	        transaction.addToBackStack("StatsFragment");
		  	        transaction.commit();
		        }
			}
		});
		
		//iv_status.setImageResource(imageId[position]);
		return rowView;
	}
}
