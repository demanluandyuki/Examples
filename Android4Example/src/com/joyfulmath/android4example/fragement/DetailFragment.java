package com.joyfulmath.android4example.fragement;

import com.joyfulmath.android4example.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment {

	private static final String TAG = "framentdemo.DetailFragment";
	private String name;

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "[onCreateView] ");
		return inflater.inflate(R.layout.layout_detail_fragment, container, false);
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Log.d(TAG, "[setName] name:"+name);
		this.name = name;
	}
}
