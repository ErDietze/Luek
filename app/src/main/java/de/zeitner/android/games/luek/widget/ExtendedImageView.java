package de.zeitner.android.games.luek.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * This class extends an ImageView to give the ability to set
 * a name for it. It is used for flag and theme imageviews

 * @author Eray Soenmez
 * @since 17.10.2014 
 * @version 1.3
 *
 */
public class ExtendedImageView extends ImageView {
	/*
	 * Constructor
	 */
	
	public ExtendedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setName("Empty");
	}
	
	public ExtendedImageView(Context context) {
		super(context);
		this.setName("Empty");
	}
	
	/*
	 * Attributes
	 */
	private String name;
	
	/*
	 * Getter & Setter
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
