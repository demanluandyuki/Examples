package com.joyfulmath.animatatorsamples;

import android.animation.TimeAnimator;
import android.animation.TimeAnimator.TimeListener;
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

public class TimeAnimatorView extends View {
	private static final String TAG = "TimeAnimatorView";
	private Context mContext = null;
	private Paint mPaint = null;
	private float mX = 0;
	float topY = 0;
	private TimeAnimator mTimeAnimator = null;

	public TimeAnimatorView(Context context) {
		this(context, null);
	}

	public TimeAnimatorView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TimeAnimatorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.d(TAG, "ValueAnimationView");
		mContext = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "onDraw:mX\t" + mX);
		canvas.drawColor(0xffffff00);
		canvas.save();
		canvas.translate(0, 0);
		canvas.drawText(TAG, mX, mX - topY, mPaint);
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
		if(mTimeAnimator!=null)
		{
			mTimeAnimator.cancel();
		}
	}

	public void createAnimator() {
		if (mTimeAnimator == null) {
			mTimeAnimator = new TimeAnimator();
			mTimeAnimator.setDuration(1000);
			mTimeAnimator.setInterpolator(new BounceInterpolator());
			mTimeAnimator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					mX = (Float) animation.getAnimatedValue();
					invalidate();
				}
			});

			mTimeAnimator.setTimeListener(new TimeListener() {

				@Override
				public void onTimeUpdate(TimeAnimator animation,
						long totalTime, long deltaTime) {
					// TODO Auto-generated method stub
					Log.d(TAG, "[onTimeUpdate] totalTime:" + totalTime+"deltaTime" + deltaTime);
				}
			});

		}

	}

	public void startValueAmimator() {
		Log.d(TAG, "startValueAmimator");
		createAnimator();
		mTimeAnimator.start();
	}
}
