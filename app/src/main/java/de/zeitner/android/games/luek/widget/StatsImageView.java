package de.zeitner.android.games.luek.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class StatsImageView extends ImageView {

	private Paint myPaint;
	private int correct;
	private int wrong;
	
	private Paint getMyPaint() {
		return myPaint;
	}

	private void setMyPaint(Paint myPaint) {
		this.myPaint = myPaint;
	}
	
	private void init(){
		this.setMyPaint(new Paint(Paint.ANTI_ALIAS_FLAG));
		correct = 17;
		wrong = 5;
	}
	
	public StatsImageView(Context context) {
		super(context, null, 0);
		init();
	}
	
	public StatsImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		init();
	}

	public StatsImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public void setStats(int correct, int wrong){
		this.correct = correct;
		this.wrong = wrong;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int w = canvas.getWidth();
		int h = canvas.getHeight();
		float radius = 10.0f;
		
		Paint textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTextAlign(Paint.Align.CENTER);
		float textHeight = textPaint.descent() - textPaint.ascent();
		float textOffset = (textHeight / 2) - textPaint.descent();
		textPaint.setTextSize(30);
		
		double percentage_correct = 0;
		double percentage_wrong = 0;
		
		if(correct+wrong != 0){
			percentage_correct = Math.round(((correct*100)/(correct+wrong)));
			percentage_wrong = 100 - percentage_correct;
		}
		
		float calc_width_correct = w-50;
		float calc_width_wrong = 50;
		
		if(percentage_correct != 0){
			calc_width_correct = (float) (w/(100/percentage_correct));
			calc_width_wrong = calc_width_correct;
		}
		
		RectF rect = new RectF(50, 50, calc_width_correct, h-50);
		RectF rect2 = new RectF(calc_width_wrong, 50, w, h-50);
		
		if(correct == 0 && wrong == 0){
			this.getMyPaint().setColor(Color.parseColor("#888888"));
		    this.getMyPaint().setStyle(Style.FILL);
		    rect.set(50, 50, w-50, h-50);
		    canvas.drawRoundRect(rect, radius, radius, this.getMyPaint());
		    canvas.drawText("Keine Daten verfügbar.", rect.centerX(), rect.centerY() + textOffset, textPaint);
		} else {
			/* Correct Rectangle */
			this.getMyPaint().setColor(Color.parseColor("#008800"));
		    this.getMyPaint().setStyle(Style.FILL);
		    canvas.drawRoundRect(rect, radius, 0, this.getMyPaint());
		    
		    if(percentage_correct != 0.0){
		    	canvas.drawText(String.valueOf(percentage_correct), rect.centerX(), rect.centerY() + textOffset, textPaint);
		    }
		    
		    /* Wrong Rectangle */
			this.getMyPaint().setColor(Color.RED);
		    this.getMyPaint().setStyle(Style.FILL);
		    canvas.drawRoundRect(rect2, 0, radius, this.getMyPaint());
		    
		    if(percentage_wrong != 0.0){
		    	canvas.drawText(String.valueOf(percentage_wrong), rect2.centerX(), rect2.centerY() + textOffset, textPaint);
		    }
		}
	}
}
