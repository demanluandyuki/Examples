package com.joyfulmath.animatatorsamples;

import android.animation.ObjectAnimator;
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
import android.view.animation.BounceInterpolator;

public class ObjectAnimatorView extends View {
	private static final String TAG = "ObjectAnimatorView";
	private Context mContext = null;
	private Paint mPaint = null;
	private float mX = 0;
	float topY = 0;  
	private ObjectAnimator mObjectAnimator = null;
	completeText text = null;
	public ObjectAnimatorView(Context context) {
		this(context, null);
	}

	public ObjectAnimatorView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ObjectAnimatorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.d(TAG, "ObjectAnimatorView");
		mContext = context;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0xff00ff00);
		canvas.save();
		canvas.translate(0, 0);
		text.draw(canvas);
		canvas.restore();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		Log.d(TAG, "onAttachedToWindow");
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
		text = new completeText();
		text.setText("ObjectAnimatorView");
		text.setX(0);
		text.setY(0);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
	public void createAnimator()
	{
		if(mObjectAnimator == null)
		{
			mObjectAnimator = ObjectAnimator.ofFloat(text, "y", 0.0f,125.0f);
			mObjectAnimator.setDuration(1000);
			mObjectAnimator.setInterpolator(new BounceInterpolator());
			mObjectAnimator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					ObjectAnimator ami = (ObjectAnimator)animation;
					Log.d(TAG, "animation y"+text.getY());
					invalidate();
				}
			});
		}

	}
	
	public void startValueAmimator()
	{
		Log.d(TAG, "startValueAmimator");
		createAnimator();
		mObjectAnimator.start();
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
