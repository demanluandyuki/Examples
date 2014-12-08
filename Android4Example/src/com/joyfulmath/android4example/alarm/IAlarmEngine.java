package com.joyfulmath.android4example.alarm;

import android.content.Context;

public interface IAlarmEngine {
	void init(Context context);
	void setAlarm();
	void closeAlarm();
}
