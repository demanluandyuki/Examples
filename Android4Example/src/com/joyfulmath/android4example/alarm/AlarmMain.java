package com.joyfulmath.android4example.alarm;

import android.content.Context;

import com.joyfulmath.android4example.IExpAction;

public class AlarmMain implements IExpAction {
	
	private Context _mContext;
	@Override
	public void doAction(Context context) {
		_mContext = context;
		IAlarmEngine action = new AlarmEngine();
		action.init(_mContext);
		action.setAlarm();
	}

	
}
