package com.joyfulmath.animatatorsamples;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Path;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationActivity extends Activity implements OnClickListener{

	private static final String TAG = "animatatorsamples";
	Button mAlphaAnimation = null;
	Button mRotateAnimation = null;
	Button mScaleAnimation = null;
	Button mTranslationAnimation = null;
	Button mSetlationAnimation = null;
	Button mValueAnimator = null;
	ImageView mImage = null;
	AnimatorSample mSamples = null;
	ValueAnimationView mValueView = null;
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
		
		mTranslationAnimation = (Button) this.findViewById(R.id.btn_translationanimation);
		mTranslationAnimation.setOnClickListener(this);
		
		mSetlationAnimation = (Button) this.findViewById(R.id.btn_setaanimation);
		mSetlationAnimation.setOnClickListener(this);
		
		mValueAnimator = (Button) this.findViewById(R.id.btn_valueanimator);
		mValueAnimator.setOnClickListener(this);
		
		mValueView = (ValueAnimationView) this.findViewById(R.id.animator_id);
		
		mImage = (ImageView) this.findViewById(R.id.animation_img);
		mSamples = new AnimatorSample();
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
		case R.id.btn_translationanimation:
			startThanslationAnimation();	
			break;
		case R.id.btn_setaanimation:
			startAniamitionSet();
			break;		
		case R.id.btn_valueanimator:
			mValueView.startValueAmimator();
			break;
		}
	}

	private void startRotateAnimation() {
		Log.i(TAG, "[startRotateAnimation]");
		//Animation.RELATIVE_TO_SELF the center of rotate
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
        int pivotXType; //where is the last place after scale
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
	
	private void startThanslationAnimation()
	{
		Log.i(TAG, "[startThanslationAnimation]");
		//Animation.ABSOLUTE where is the last point show place
		//the start x,y ordinate is the current place with translation 
		TranslateAnimation anmit= new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0.1f,Animation.RELATIVE_TO_PARENT,0.5f,
				Animation.RELATIVE_TO_PARENT,0.2f,Animation.RELATIVE_TO_PARENT, 0.6f);
		anmit.setDuration(3000);
		anmit.setFillAfter(true);
		anmit.setInterpolator(new BounceInterpolator());
		mImage.startAnimation(anmit);	
	}
	
	private void startAniamitionSet()
	{
		Log.i(TAG, "[startAniamitionSet]");
		AnimationSet set = new AnimationSet(false);
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f
				);
		Path path = new Path();
		path.lineTo(0.25f, 0.25f);
		path.lineTo(0.28f, 0.35f);
		path.lineTo(0.38f, 0.55f);
		path.lineTo(1.0f, 1.0f);
//		scaleAnim.setInterpolator(new PathInterpolator(path)); //PathInterpolator��5.1���е��¹���
		scaleAnim.setInterpolator(new MyInterpolater2());
		AlphaAnimation alphaAni = new AlphaAnimation(1.0f, 0.5f);
		alphaAni.setInterpolator(new BounceInterpolator());
		set.addAnimation(alphaAni);
		set.addAnimation(scaleAnim);
		set.setDuration(3000);
		set.setFillAfter(true);
		
		mImage.startAnimation(set);
	}
	
	//baseinterpolater is basd on Added in API level 22
	// we just suing interpolater
	public static class MyInterpolater2 implements Interpolator {
		
		private static final String TAG = "MyInterpolater2";
		private float a = -1.0f;
		private float x2 = 2.0f;
		/*we defined an two arc y = a(x-x1)(x-x2)
		 * 
		 * */
		@Override
		public float getInterpolation(float input) {
			if (input <= 0.5)
				return input * input;
				else
				return (1 - input) * (1 - input);
		}		
		
	}
	
}
