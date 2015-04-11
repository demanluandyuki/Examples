package com.joyfulmath.animatatorsamples;

import com.joyfulmath.animatatorsamples.ObjectAnimatorView.completeText;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class KeyFrameView extends View {
	private static final String TAG = "KeyFrameView";
	private Context mContext;
	completeText stext = null;
	float topY = -10f;
	private Paint mPaint = null;
	private ObjectAnimator mObjectAnimator;
	public KeyFrameView(Context context) {
		this(context, null);
	}

	public KeyFrameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public KeyFrameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.d(TAG, "ValueAnimationView");
		mContext = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		stext.draw(canvas);
	}
	
	public void CreateAnimator()
	{
		Keyframe kf0 = Keyframe.ofFloat(0, 0);
		kf0.setInterpolator(new BounceInterpolator());
		Keyframe kf1 = Keyframe.ofFloat(0.25f, 10);
		kf1.setInterpolator(new AccelerateDecelerateInterpolator());
		Keyframe kf2 = Keyframe.ofFloat(0.5f, 30);
		Keyframe kf4 = Keyframe.ofFloat(0.75f, 50);
		Keyframe kf3 = Keyframe.ofFloat(1f, 0);
		
		PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("y", kf0, kf1, kf2, kf4, kf3);
		mObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(stext, pvhRotation);
		mObjectAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				invalidate();
			}
		});
	}
	
	public void startValueAmimator()
	{
		Log.d(TAG, "startValueAmimator");
		CreateAnimator();
		mObjectAnimator.start();
	}
	
	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		mPaint = new Paint();
		mPaint.setTextSize(18);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setColor(0xffff0000);
		mPaint.setAntiAlias(true);
		FontMetrics fontMetrics = mPaint.getFontMetrics();
		topY = fontMetrics.top;  
		float ascentY = fontMetrics.ascent;  
		float descentY = fontMetrics.descent;  
		float bottomY = fontMetrics.bottom; 
		Log.d(TAG, "onAttachedToWindow fontMetrics  topY:" + topY
				+ "\t ascentY:" + ascentY + "\t descentY:" + descentY
				+ "\t bottomY:" + bottomY);
		stext = new completeText();
		stext.setText("ObjectAnimatorView");
		stext.setX(0);
		stext.setY(0);
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}



	public class completeText
	{
		public float x;
		public float y;
		
		public String stext = null;
		
		public completeText()
		{
			
		}
		
		public void setText(String s)
		{
			stext = s;
		}
		
		public float getX() {
			return x;
		}
		public void setX(float x) {
			this.x = x;
		}
		public float getY() {
			return y;
		}
		public void setY(float y) {
			this.y = y;
		}
		
		public void draw(Canvas canvas)
		{
			Log.d(TAG, "draw x"+x+"y-topY:"+(y-topY));
			canvas.drawText(stext,x,y-topY,mPaint);
		}
		
	}
}
