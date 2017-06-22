package de.zeitner.android.games.luek.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import de.zeitner.android.games.luek.R;

public class SplashFragment extends Fragment {
	
	View myView;
	
	private void setTimer(int time) {
		final ProgressBar pb_splash = (ProgressBar)myView.findViewById(R.id.pb_splash);
		pb_splash.setMax(0);
		pb_splash.setProgress(0);
		
	    final int actualTime = time * 1000;
	    
	    pb_splash.setMax(100);
	    pb_splash.setProgress(0);
	    CountDownTimer countDownTimer = new CountDownTimer(actualTime, 100) {
	        int totalTime = actualTime;
	        @Override
	        public void onTick(long millisUntilFinished) {
	        	pb_splash.incrementProgressBy(10);
	        }

	        @Override
	        public void onFinish() {
	            pb_splash.setProgress(100);
	            
	            MainFragment myMainFragment = new MainFragment();
	            FragmentManager fragmentManager = getFragmentManager();
	            if(fragmentManager.findFragmentById(R.layout.main_fragment) == null){
	      	        FragmentTransaction transaction = fragmentManager.beginTransaction();
	      	        transaction.replace(R.id.MainActivityRelativeLayout, myMainFragment);
	      	        //transaction.addToBackStack("MainFragment");
	      	        transaction.commit();
	            }
	            
//	            LearnFragment myLearnFragment = new LearnFragment();
//	            FragmentManager fragmentManager = getFragmentManager();
//	            if(fragmentManager.findFragmentById(R.layout.learn_fragment) == null){
//	      	        FragmentTransaction transaction = fragmentManager.beginTransaction();
//	      	        transaction.replace(R.id.MainActivityRelativeLayout, myLearnFragment);
//	      	        transaction.addToBackStack("LearnFragment");
//	      	        transaction.commit();
//	            }
	        }
	    };
	    
	    countDownTimer.start();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.splash_fragment, container, false);
		this.myView = myView;
				
		setTimer(10);
		
		return myView;
	}
}
