package com.joyfulmath.animatatorsamples.threedimension;


import com.joyfulmath.animatatorsamples.R;
import com.joyfulmath.animatatorsamples.utils.DisplayUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CameraThreeDimentionActivity extends Activity implements OnClickListener {
	
	private Button mCamera = null;
	private ImageView mImageView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_threedimention_camera);
		initView();
	}

	private void initView() {
		mCamera = (Button) findViewById(R.id.btn_camera);
		mCamera.setOnClickListener(this);
		
		mImageView = (ImageView) findViewById(R.id.animation_img);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_camera:
			startCamera3dAnimation();
			break;
		}
	}

	private void startCamera3dAnimation() {
		DisplayMetrics dm = DisplayUtils.getDisplayMetrics(this);
		float centerx = mImageView.getWidth()*0.5f;
		float centery = mImageView.getHeight()*0.5f;
		Rotate3dAnimation animation = new Rotate3dAnimation(0f, 720f, centerx, centery, 1f, true);
		animation.setDuration(3000);
		mImageView.startAnimation(animation);
	}
	
}
