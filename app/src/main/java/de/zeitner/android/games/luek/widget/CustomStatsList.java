package de.zeitner.android.games.luek.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import de.zeitner.android.games.luek.R;

public class CustomStatsList extends ArrayAdapter<String> {

	private final static String TAG = "CustomStatsList";
	
	private Activity activity;
	private ListView list;
	private ArrayList<Drawable> cpackitems;
	private ArrayList<String> cpacknames;
	private ArrayList<String> statvalues;
	
	private Activity getActivity() {
		return activity;
	}

	private void setActivity(Activity activity) {
		this.activity = activity;
	}

	private ListView getList() {
		return list;
	}

	private void setList(ListView list) {
		this.list = list;
	}

	public CustomStatsList(Activity context, ArrayList<String> cpacknames, String packageName, ArrayList<Drawable> cpackitems, ArrayList<String> statvalues, ListView list) {
		super(context, R.layout.stats_list_item, cpacknames);
		this.setActivity(context);
		this.setList(list);
		this.cpackitems = cpackitems;
		this.cpacknames = cpacknames;
		this.statvalues = statvalues;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		View rowView = inflater.inflate(R.layout.stats_list_item, null, true);
		
		ImageView iv_stats_item_preview = (ImageView) rowView.findViewById(R.id.iv_stats_item_preview);
		TextView tv_stats = (TextView) rowView.findViewById(R.id.tv_stats);
		
		int correct;
		int wrong;
		
		correct = Integer.parseInt(statvalues.get(position).split("#")[0]);
		wrong = Integer.parseInt(statvalues.get(position).split("#")[1]);
				
		StatsImageView iv_statvalues = (StatsImageView) rowView.findViewById(R.id.iv_statsvalues);
		iv_statvalues.setStats(correct, wrong);
		
		iv_stats_item_preview.setImageDrawable(cpackitems.get(position));
		
		return rowView;
	}
}
