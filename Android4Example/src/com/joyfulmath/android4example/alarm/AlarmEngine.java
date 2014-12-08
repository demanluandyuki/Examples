package com.joyfulmath.android4example.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmEngine implements IAlarmEngine {

	private static final String TAG = "alarm.AlarmEngine";
	private Context _mContext;
	private AlarmManager am;
	@Override
	public void init(Context context) {
		_mContext = context;
		am = (AlarmManager) _mContext.getSystemService(Context.ALARM_SERVICE);
	}

	@Override
	public void setAlarm() {
		Calendar cal = Utils.getTimeAfterInSecs(30);
		PendingIntent pi = getPendingIntent();
		am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
		Log.i(TAG, "[setAlarm]");
	}

	@Override
	public void closeAlarm() {
		PendingIntent pi = getPendingIntent();
		am.cancel(pi);
	}
	private PendingIntent getPendingIntent() {
		Intent intent = new Intent(_mContext,AlarmReceiver.class);
		intent.putExtra("message", "show shot alarm");
		PendingIntent pi = PendingIntent.getBroadcast(_mContext, 1, intent, 0);
		return pi;
	}
}
