package de.zeitner.android.games.luek.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import de.zeitner.android.games.luek.Spiel;

public class InfoboxImageView extends ImageView {
	
	Spiel spiel;

	private Spiel getSpiel() {
		return spiel;
	}

	public void setSpiel(Spiel spiel) {
		this.spiel = spiel;
	}
	
	public InfoboxImageView(Context context) {
		this(context, null, 0);
	}
	public InfoboxImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public InfoboxImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@SuppressLint("DrawAllocation") @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(this.getSpiel() != null){	
			
			DisplayMetrics metrics = this.getSpiel().getMyView().getResources().getDisplayMetrics();

			final double YSIZE = metrics.heightPixels / metrics.ydpi;
			final double XSIZE = metrics.widthPixels / metrics.xdpi;

			final double SCREENSIZE = Math.sqrt(XSIZE * XSIZE + YSIZE * YSIZE);
			
			final double BASESCALE = 12.0;
			final double SCALE = 1.0d * (SCREENSIZE / BASESCALE);
			
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			
			Rect bounds = new Rect();
		    		    
			/* For Devices (Smartphones) below 6.99 inches and over 1000 pixels (height and width) */
	        if(SCREENSIZE < 6.99 && metrics.heightPixels > 1000 && metrics.widthPixels > 1000){
	        	paint.setTextSize((int)(30*2.3));
	        }
			
	        /* For Devices (Tablets) over 6.99 inch and 1000 pixels (height and width) */
	        if(SCREENSIZE >= 6.99 && metrics.heightPixels > 1000 && metrics.widthPixels > 1000){
	        	paint.setTextSize(40);
	        }
	        
	        /* For Devices (Smartphones) below 5.98 inch and 1000 pixels (height and width) */
	        if(SCREENSIZE < 5.98 && metrics.heightPixels < 1000 && metrics.widthPixels < 1000){
	        	paint.setTextSize(18);
	        }
	        
		    int xPos = (canvas.getWidth() / 2) - (bounds.width() / 2);
		    int yPos = (canvas.getHeight() / 2) - (bounds.height() / 2);
			
		    if(this.getSpiel().getGesuchteKarte() != null){
			    paint.getTextBounds(this.getSpiel().getGesuchteKarte().getDescription(), 0, this.getSpiel().getGesuchteKarte().getDescription().length(), bounds);
			    paint.setTextAlign(Paint.Align.CENTER);
			    canvas.drawText(this.getSpiel().getGesuchteKarte().getDescription(), xPos, yPos+20, paint);
		    }
			
			//canvas.drawText(this.getSpiel().getGesuchteKarte().getDescription(), 40, 50, paint);
		}
	}
}
