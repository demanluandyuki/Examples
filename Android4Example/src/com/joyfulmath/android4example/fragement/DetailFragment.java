package com.joyfulmath.android4example.fragement;

import com.joyfulmath.android4example.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

	private static final String TAG = "framentdemo.DetailFragment";
	private String name;
	TextView mDetailText;
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "[onCreateView] ");
		View view = inflater.inflate(R.layout.layout_detail_fragment, container, false);
		mDetailText = (TextView) view.findViewById(R.id.detail_text);
		return view;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
		mDetailText.setText(name);
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
		Log.d(TAG, "[onStop] name:"+name);
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, "[onDestroyView] name:"+name);
	}
	
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Log.d(TAG, "[setName] name:"+name);
		this.name = name;
	}
}
