package de.zeitner.android.games.luek.listener;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.View.DragShadowBuilder;

public class CardShadowBuilder extends DragShadowBuilder {
	private Drawable shadow;
	
	public CardShadowBuilder(View v)	{
		super(v);
		//shadow = new ColorDrawable(Color.LTGRAY);
		shadow = v.getBackground();
	}
	
	@Override
	public void onDrawShadow(Canvas canvas)	{
		shadow.draw(canvas);
	}
	
	@Override
	public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint)	{
		int height, width;
		height = (int) getView().getHeight();
		width = (int) getView().getHeight();
		
		shadow.setBounds(0, 0, width, height);
		shadowSize.set(width, height);
		shadowTouchPoint.set(width/2, height/2);


	}

}
