package de.zeitner.android.games.luek.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.widget.Button;

import de.zeitner.android.games.luek.listener.BtnControlListener;

/**
 * Created by Eric on 21.06.2017.
 */

public class BtnControl extends Button {


    public BtnControl(Context context) {
        super(context);
        init(context);
    }

    public BtnControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BtnControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        super.setOnClickListener(new BtnControlListener(context));
    }
}
