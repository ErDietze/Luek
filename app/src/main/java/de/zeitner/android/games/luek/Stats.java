package de.zeitner.android.games.luek;

import java.util.HashMap;

import android.view.View;

public class Stats {
	
	Storage storage;
	View myView;
	public HashMap<String, String> content;
	
	public Stats(View myView, String file) {
		this.myView = myView;
		this.storage = new Storage(this.myView);
		this.storage.setPath(file);
		content = this.storage.getHashMap();
	}
	
	public void rehash(){
		content = this.storage.getHashMap();
	}
}
