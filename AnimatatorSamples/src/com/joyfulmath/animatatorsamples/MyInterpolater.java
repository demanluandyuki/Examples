package com.joyfulmath.animatatorsamples;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.BaseInterpolator;

public class MyInterpolater extends BaseInterpolator {
	
	private static final String TAG = "CustomInterpolater";
	private float a = -1.0f;
	private float x2 = 2.0f;
	/*we defined an two arc y = a(x-x1)(x-x2)
	 * 
	 * */
//	public CustomInterpolater(float a)
//	{
//		super();
//		Log.i(TAG, "CustomInterpolater");
//		this.a = a;
//		getX2();
//	}
//	
//	public CustomInterpolater()
//	{
//		super();
//		Log.i(TAG, "CustomInterpolater");
//		this.a = 2;
//		getX2();
//	}
//	
//    public CustomInterpolater(Context context, AttributeSet attrs) {
//    }
	
	public void getX2()
	{
		//1 = a(1-x2);
		if(a!=0)
		{
			x2 = 1-1/a;
		}
		else  if(a == 0)
		{
			//make sure a can not be zero
			a = -1.0f;
			x2 = 1+1;
		}
	}
	
	@Override
	public float getInterpolation(float input) {
		// TODO Auto-generated method stub
//		return a*input*(input-x2);
		if (input <= 0.5)
			return input * input;
			else
			return (1 - input) * (1 - input);

	}
	
	
	
}
