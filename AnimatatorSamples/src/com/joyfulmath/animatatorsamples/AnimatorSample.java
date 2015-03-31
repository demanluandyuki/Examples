package com.joyfulmath.animatatorsamples;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AnimatorSample {
	
	/*valueAnimator 在变化的过程中，onAnimationUpdate的时候，可以获得随着时间变化而得到的值。
	 *valueAnimator 改变的是任意值的变化，可以是x，y坐标，也可以是alpha值。
	 * 
	 * */
	public void valueAnimatorStart()
	{
		ValueAnimator mValue = ValueAnimator.ofFloat(0.0f, 1.0f);
		mValue.setDuration(1000);
		mValue.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				 Log.i("AnimatorSample", ((Float) animation.getAnimatedValue()).toString());
			}
		});
		
		mValue.setInterpolator(new AccelerateDecelerateInterpolator());
		mValue.start();
	}
}
