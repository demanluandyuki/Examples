package com.joyfulmath.android4example.fragement;

import com.joyfulmath.android4example.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class FragementActivity extends Activity implements onClickResult {

	private static final String TAG = "framentdemo.FragementActivity";
	TitleFragment mTitleFragment;
	DetailFragment mDetailFragmentA;
	DetailFragment mDetailFragmentB;
	FragmentManager fm = getFragmentManager();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "[onCreate]");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_fragement_main);
		setDefaultFragment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void setDefaultFragment() {
		Log.d(TAG, "[setDefaultFragment]");
		FragmentTransaction transaction = fm.beginTransaction();
		mTitleFragment = new TitleFragment();
		mTitleFragment.setmClicklistener(this);
		mTitleFragment.SetHighLight(0);
		transaction.replace(R.id.title, mTitleFragment);
		mDetailFragmentA = new DetailFragment();
		mDetailFragmentA.setName("detail_A");
		transaction.replace(R.id.detail, mDetailFragmentA);
//		transaction.addToBackStack("detail_A");
		transaction.commit();
	}

	@Override
	public void onClickIndex(int index) {
		Log.d(TAG, "[onClickIndex] index:" + index);
		FragmentTransaction transaction = fm.beginTransaction();
		switch (index) {
		case 0:
			mDetailFragmentA = new DetailFragment();
			mDetailFragmentA.setName("detail_A");
			transaction.replace(R.id.detail, mDetailFragmentA);
			transaction.addToBackStack("detail_A");
			break;
		case 1:
			mDetailFragmentB = new DetailFragment();
			mDetailFragmentB.setName("detail_B");
			transaction.replace(R.id.detail, mDetailFragmentB);
			transaction.addToBackStack("detail_B");
			break;
		}
		transaction.commit();
	}
}
