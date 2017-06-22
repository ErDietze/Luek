package de.zeitner.android.games.luek;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import de.zeitner.android.games.luek.fragment.MainFragment;
import de.zeitner.android.games.luek.fragment.SplashFragment;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment myMainFragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.findFragmentById(R.layout.main_fragment) == null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.MainActivityRelativeLayout, myMainFragment);
            transaction.addToBackStack("MainFragment");
            transaction.commit();
        }
        
//        SplashFragment mySplashFragment = new SplashFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        if(fragmentManager.findFragmentById(R.layout.splash_fragment) == null){
//  	        FragmentTransaction transaction = fragmentManager.beginTransaction();
//  	        transaction.replace(R.id.MainActivityRelativeLayout, mySplashFragment);
////  	        transaction.addToBackStack("SplashFragment");
//  	        transaction.commit();
//        }
        
//      LearnFragment myLearnFragment = new LearnFragment();
//      FragmentManager fragmentManager = getFragmentManager();
//      if(fragmentManager.findFragmentById(R.layout.learn_fragment) == null){
//	        FragmentTransaction transaction = fragmentManager.beginTransaction();
//	        transaction.replace(R.id.MainActivityRelativeLayout, myLearnFragment);
//	        transaction.addToBackStack("LearnFragment");
//	        transaction.commit();
//      }
        
//      MainFragment myMainFragment = new MainFragment();
//      FragmentManager fragmentManager = getFragmentManager();
//      if(fragmentManager.findFragmentById(R.layout.main_fragment) == null){
//	        FragmentTransaction transaction = fragmentManager.beginTransaction();
//	        transaction.replace(R.id.MainActivityRelativeLayout, myMainFragment);
//	        //transaction.addToBackStack("MainFragment");
//	        transaction.commit();
//      }
        
//        RecognizeFragment myRecognizeFragment = new RecognizeFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        if(fragmentManager.findFragmentById(R.layout.recognize_fragment) == null){
//	        FragmentTransaction transaction = fragmentManager.beginTransaction();
//	        transaction.replace(R.id.MainActivityRelativeLayout, myRecognizeFragment);
//	        transaction.addToBackStack("RecognizeFragment");
//	        transaction.commit();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
