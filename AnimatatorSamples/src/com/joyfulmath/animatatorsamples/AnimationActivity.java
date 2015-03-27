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
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationActivity extends Activity implements OnClickListener{

	private static final String TAG = "animatatorsamples";
	Button mAlphaAnimation = null;
	Button mRotateAnimation = null;
	Button mScaleAnimation = null;
	ImageView mImage = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_main);
		mAlphaAnimation = (Button) this.findViewById(R.id.btn_alphaanimation);
		mAlphaAnimation.setOnClickListener(this);
		mRotateAnimation = (Button) this.findViewById(R.id.btn_rotateanimation);
		mRotateAnimation.setOnClickListener(this);
		
		mScaleAnimation = (Button) this.findViewById(R.id.btn_scaleanimation);
		mScaleAnimation.setOnClickListener(this);
		
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
		case R.id.btn_scaleanimation:
			startScaleAnimation();
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
	}
	
	private void startScaleAnimation()
	{
		float fromX; //1.0f to 0.0f
		float toX; 
		float fromY; 
		float toY;
        int pivotXType; //
        float pivotXValue;
        int pivotYType; 
        float pivotYValue;	
        //Animation.ABSOLUTE, 		//the last place with X ordinate with absolute diff 20f
        //Animation.RELATIVE_TO_SELF,   //specified dimension to self
        //Animation.RELATIVE_TO_PARENT.	//specified dimension to parent move 100%=1f with diff (%parent diff)
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f
//				,Animation.ABSOLUTE, 20f, Animation.RELATIVE_TO_PARENT,
//				2f
				);
		//ScaleAnimation(float fromX, float toX, float fromY, float toY) 类似Animation.ABSOLUTE,且x,y =0;
		scaleAnim.setDuration(3000);
		scaleAnim.setFillAfter(true);
		scaleAnim.setInterpolator(new OvershootInterpolator());
		mImage.startAnimation(scaleAnim);
		
	}

}
