package de.zeitner.android.games.luek.widget;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Storage;

public class CustomSettingsList extends ArrayAdapter<String>{
	
	private final static String TAG = "CustomSettingsList";
	
	private Activity context;
	private ArrayList<String> cpacknames;
	private ArrayList<Drawable> cpackitems;
	public ArrayList<Integer> selectedPositions;
	private ListView list;
	
	Storage storage;
	HashMap<String, String> cfg;
	
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
		
	private ListView getList() {
		return list;
	}

	private void setList(ListView list) {
		this.list = list;
	}

	public CustomSettingsList(Activity context,	ArrayList<String> cpacknames, ArrayList<Drawable> cpackitems, ListView lv) {
		super(context, R.layout.settings_list_item, cpacknames);
		this.context = context;
		this.cpacknames = cpacknames;
		this.cpackitems = cpackitems;
		this.selectedPositions = new ArrayList<Integer>();
		this.setList(lv);
		
		for(int i = 0; i < cpacknames.size(); i++){
			Log.v(TAG, cpacknames.get(i));
		}
	}
	
	public void toggle(ListView lv, int pos, View v){
	    if (lv.isItemChecked(pos)) {
	        lv.setItemChecked(pos,false);
	        ((CheckBox)v).setChecked(false);
	    } else {
	        lv.setItemChecked(pos,true);
	        ((CheckBox)v).setChecked(true);
	    }
	}
	
	public View getViewByPosition(int pos, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition ) {
		    return listView.getAdapter().getView(pos, null, listView);
		} else {
		    final int childIndex = pos - firstListItemPosition;
		    return listView.getChildAt(childIndex);
		}
	}	
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {            
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.settings_list_item, null, true);
		this.setStorage(new Storage(view));

		final int pos = position;
		
		ExtendedImageView iv_settings_item_preview = (ExtendedImageView) rowView.findViewById(R.id.iv_settings_item_preview);
		final CheckBox cb_settings_item_choose = (CheckBox) rowView.findViewById(R.id.cb_settings_item_choose);
		final CheckBox cb_all = (CheckBox) context.findViewById(R.id.cb_settings_all);
		cb_settings_item_choose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(cb_all.isChecked()){
//					cb_all.setChecked(false);
//				}
				getList().setItemChecked(pos, ((CheckBox)v).isChecked());
		    	//notifyDataSetChanged();
		    }
		});
		
		/* Check all checked CheckBoxes within the List */
		SparseBooleanArray checked = list.getCheckedItemPositions();
		if(checked != null){
			for (int i = 0; i <= checked.size(); i++){
			    if (checked.get(i)){
			    	TableLayout tl = (TableLayout)(list.getChildAt(i));
			    	if(tl != null){
		                TableRow tr = (TableRow)(tl.getChildAt(0));
		                CheckBox cb = (CheckBox)(tr.getChildAt(2));
		                cb.setChecked(true);
			    	}
			    }
			}
		}

//		if (cb_all.isChecked()){
//			cb_settings_item_choose.setChecked(cb_all.isChecked());
//		}
		
		iv_settings_item_preview.setImageDrawable(cpackitems.get(position));
		iv_settings_item_preview.setName(cpacknames.get(position));
						
		return rowView;
	}
}