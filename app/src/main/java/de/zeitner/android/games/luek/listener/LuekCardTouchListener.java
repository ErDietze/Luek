package de.zeitner.android.games.luek.listener;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

public class LuekCardTouchListener implements View.OnTouchListener {

	@SuppressLint("ClickableViewAccessibility") 
	public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        	View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        	view.startDrag(null, shadowBuilder, view, 0);
        	view.setVisibility(View.INVISIBLE);
	    return true;
	} else {
	    return false;
	}
   }
	
}