package de.zeitner.android.games.luek.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.LuekSpiel;
import de.zeitner.android.games.luek.listener.BtnControlListener;

public class LuekFragment extends Fragment {

    Button btnFinishedTablet;
    Button btnFinishedPhone;
    int clicked = 1;


    private final static String TAG = "LuekFragment";
    private String packageName;

    private String getPackageName() {
        return packageName;
    }

    private void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.luek_fragment, container, false);

        Bundle infos = getArguments();
        this.setPackageName(infos.getString("Package"));



        LuekSpiel ls = new LuekSpiel((TableLayout) myView.findViewById(R.id.LuekTableLayout), 2, 3, myView, this.getPackageName());
        ls.start();


        return myView;
    }
}
