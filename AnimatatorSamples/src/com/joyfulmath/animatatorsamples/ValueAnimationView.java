package com.joyfulmath.animatatorsamples;

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

public class ValueAnimationView extends View {

	private static final String TAG = "ValueAnimationView";
	private Context mContext = null;
	private Paint mPaint = null;
	private float mX = 0;
	float topY = 0;  
	private ValueAnimator mValueAnimator = null;
	public ValueAnimationView(Context context) {
		this(context, null);
	}

	public ValueAnimationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ValueAnimationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.d(TAG, "ValueAnimationView");
		mContext = context;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "onDraw:mX\t"+mX);
		canvas.drawColor(0xffffff00);
		canvas.save();
		canvas.translate(0, 0);
		canvas.drawText(TAG, mX, mX-topY, mPaint);
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
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
	public void createAnimator()
	{
		if(mValueAnimator == null)
		{
			mValueAnimator = ValueAnimator.ofFloat(0.0f, 50.0f);
			mValueAnimator.setDuration(1000);
			mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					mX = (Float) animation.getAnimatedValue();
					invalidate();
				}
			});
		}

	}
	
	public void startValueAmimator()
	{
		Log.d(TAG, "startValueAmimator");
		createAnimator();
		mValueAnimator.start();
	}
}
