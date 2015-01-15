package com.joyfulmath.android4example.fragement;

import com.joyfulmath.android4example.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class FragementActivity extends Activity implements onClickResult {

	private static final String TAG = "framentdemo.FragementActivity";
	TitleFragment mTitleFragment;
	DetailFragment mDetailFragment;
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		setDefaultFragment();
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
		mDetailFragment = new DetailFragment();
		mDetailFragment.setName("detail_A");
		transaction.replace(R.id.detail, mDetailFragment);
		transaction.addToBackStack("detail_A");
		transaction.commit();
	}

	@Override
	public void onClickIndex(int index) {
		Log.d(TAG, "[onClickIndex] index:"+index);
		FragmentTransaction transaction = fm.beginTransaction();
		switch (index) {
		case 0:
			mDetailFragment = (DetailFragment) fm.getFragment(null, "detail_A");
			if(mDetailFragment == null)
			{
				mDetailFragment = new DetailFragment();
				mDetailFragment.setName("detail_A");
				transaction.replace(R.id.detail, mDetailFragment);
				transaction.addToBackStack("detail_A");
			}
			break;
		case 1:
			mDetailFragment = (DetailFragment) fm.getFragment(null, "detail_B");
			if(mDetailFragment == null)
			{
				mDetailFragment = new DetailFragment();
				mDetailFragment.setName("detail_A");
				transaction.replace(R.id.detail, mDetailFragment);
				transaction.addToBackStack("detail_B");
			}
			break;
		}		
		transaction.commit();
	}
}
