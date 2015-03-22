package com.joyfulmath.animatatorsamples;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationActivity extends Activity implements OnClickListener{

	private static final String TAG = "animatatorsamples";
	Button mAlphaAnimation = null;
	Button mRotateAnimation = null;
	ImageView mImage = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_main);
		mAlphaAnimation = (Button) this.findViewById(R.id.btn_alphaanimation);
		mAlphaAnimation.setOnClickListener(this);
		mRotateAnimation = (Button) this.findViewById(R.id.btn_rotateanimation);
		mRotateAnimation.setOnClickListener(this);
		mImage = (ImageView) this.findViewById(R.id.animation_img);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.animation, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_alphaanimation:
			startAlphaAnimation();
			break;
		case R.id.btn_rotateanimation:
			startRotateAnimation();
			break;
		}
	}

	private void startRotateAnimation() {
		Log.i(TAG, "[startAlphaAnimation]");
		Animation rotateaAni = new RotateAnimation(0f, 360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		rotateaAni.setDuration(3000);
		rotateaAni.setFillAfter(true);
		rotateaAni.setInterpolator(new LinearInterpolator());		
		mImage.startAnimation(rotateaAni);
		
	}

	private void startAlphaAnimation() {
		Log.i(TAG, "[startAlphaAnimation]");
		AlphaAnimation alphaAni = new AlphaAnimation(1.0f, 0.5f);
		alphaAni.setDuration(3000);
		alphaAni.setFillAfter(true);
		alphaAni.setInterpolator(new OvershootInterpolator());
		alphaAni.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				Log.i(TAG, "[onAnimationStart]");
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				Log.i(TAG, "[onAnimationRepeat]");
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				Log.i(TAG, "[onAnimationEnd]");
			}
		});
		
		mImage.startAnimation(alphaAni);
//		mImage.setAnimation(alphaAni);
//		alphaAni.start();//start?
	}

}
