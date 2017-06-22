package de.zeitner.android.games.luek.listener;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import de.zeitner.android.games.luek.R;


public class BtnControlListener implements View.OnClickListener {
    private Context c;

    public Context getC() {
        return c;
    }

    private void setC(Context c) {
        this.c = c;
    }

    public BtnControlListener(Context c) {
        super();
        setC(c);
    }

    @Override
    public void onClick(View view) {


        Toast.makeText(getC(), "clicked", Toast.LENGTH_SHORT).show();


    }
}
