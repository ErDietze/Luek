package de.zeitner.android.games.luek.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Storage;

public class ModeFragment extends Fragment implements OnClickListener{
	
	final String TAG = "ModeFragment";
	
	/** Storage Object to access read and write methods from and to harddisk */
	private Storage storage;
	
	/** Current View Object */
	private View myView;
	
	private String packageName;
		
	private Storage getStorage() {
		return storage;
	}
	
	private void setStorage(Storage storage) {
		this.storage = storage;
	}

	private View getMyView() {
		return myView;
	}

	private void setMyView(View myView) {
		this.myView = myView;
	}

	private String getPackageName() {
		return packageName;
	}

	private void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.mode_fragment, container, false);
		this.setMyView(myView);
		
		Bundle infos = getArguments();
		this.setPackageName(infos.getString("Package"));
		
		ImageView iv_mode_exit = (ImageView) this.getMyView().findViewById(R.id.iv_mode_exit);
		Button btn_start_learngame = (Button) this.getMyView().findViewById(R.id.btn_start_learngame);
		Button btn_start_recognizegame = (Button) this.getMyView().findViewById(R.id.btn_start_recognizegame);
		Button btn_start_luekgame = (Button) this.getMyView().findViewById(R.id.btn_start_luekgame);
		
		iv_mode_exit.setOnClickListener(this);
		btn_start_learngame.setOnClickListener(this);
		btn_start_recognizegame.setOnClickListener(this);
		btn_start_luekgame.setOnClickListener(this);
		
		return this.getMyView();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_start_learngame: {
				Bundle infos = new Bundle();
				infos.putString("Package", this.getPackageName());
				LearnFragment myLearnFragment = new LearnFragment();
				FragmentManager fragmentManager = getFragmentManager();
				myLearnFragment.setArguments(infos);
				if(fragmentManager.findFragmentById(R.layout.learn_fragment) == null){
				      FragmentTransaction transaction = fragmentManager.beginTransaction();
				      transaction.replace(R.id.MainActivityRelativeLayout, myLearnFragment);
				      transaction.addToBackStack("LearnFragment");
				      transaction.commit();
				}
				break;
			}
			case R.id.btn_start_recognizegame: {
				Bundle infos = new Bundle();
				infos.putString("Package", this.getPackageName());
				RecognizeFragment myRecognizeFragment = new RecognizeFragment();
				FragmentManager fragmentManager = getFragmentManager();
				myRecognizeFragment.setArguments(infos);
				if(fragmentManager.findFragmentById(R.layout.recognize_fragment) == null){
					FragmentTransaction transaction = fragmentManager.beginTransaction();
				    transaction.replace(R.id.MainActivityRelativeLayout, myRecognizeFragment);
				    transaction.addToBackStack("RecognizeFragment");
				    transaction.commit();
				}
				break;
			}
			case R.id.btn_start_luekgame: {
				Bundle infos = new Bundle();
				infos.putString("Package", this.getPackageName());
				LuekFragment myLuekFragment = new LuekFragment();
				FragmentManager fragmentManager = getFragmentManager();
				myLuekFragment.setArguments(infos);
				if(fragmentManager.findFragmentById(R.layout.luek_fragment) == null){
					FragmentTransaction transaction = fragmentManager.beginTransaction();
				    transaction.replace(R.id.MainActivityRelativeLayout, myLuekFragment);
				    transaction.addToBackStack("LuekFragment");
				    transaction.commit();
				}
				break;
			}
			case R.id.iv_mode_exit: {
				FragmentManager fm = ((Activity) this.getMyView().getContext()).getFragmentManager();
				fm.popBackStack();
				break;
			}
		}
	}
}
