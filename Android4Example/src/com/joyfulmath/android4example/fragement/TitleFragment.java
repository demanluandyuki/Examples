package com.joyfulmath.android4example.fragement;

import com.joyfulmath.android4example.R;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TitleFragment extends Fragment implements OnClickListener {

	private static final String TAG = "framentdemo.TitleFragment";
	private Button btnA;
	private Button btnB;
	private int mHighIndex = 0;
	onClickResult mClicklistener;
	/**
	 * @param mClicklistener the mClicklistener to set
	 */
	public void setmClicklistener(onClickResult mClicklistener) {
		this.mClicklistener = mClicklistener;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "[onCreateView] ");
		View view = inflater.inflate(R.layout.layout_title_fragment, container, false);
		btnA = (Button) view.findViewById(R.id.btn_a);
		btnB = (Button) view.findViewById(R.id.btn_b);
		btnA.setOnClickListener(this);
		btnB.setOnClickListener(this);
		return view;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		setHighIndex();
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

	@Override
	public void onClick(View v) {
		ShowFragmentDetail(v.getId());
	}

	private void ShowFragmentDetail(int id) {
		switch(id)
		{
		case R.id.btn_a:
			SetHighLight(0);
			setHighIndex();
			if(mClicklistener!=null)
			{
				mClicklistener.onClickIndex(0);
			}
			break;
		case R.id.btn_b:
			SetHighLight(1);
			setHighIndex();
			if(mClicklistener!=null)
			{
				mClicklistener.onClickIndex(1);
			}
			break;
		}
	}
	
	public void SetHighLight(int index)
	{
		mHighIndex = index;
	}
	
	private void setHighIndex()
	{
		switch(mHighIndex)
		{
		case 0:
			btnA.setTextColor(Color.BLUE);
			btnB.setTextColor(Color.BLACK);
			break;
		case 1:
			btnA.setTextColor(Color.BLACK);
			btnB.setTextColor(Color.BLUE);
			break;	
		}
	}
}
