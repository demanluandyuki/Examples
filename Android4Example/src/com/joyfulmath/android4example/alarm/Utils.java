package com.joyfulmath.android4example.alarm;

import java.util.Calendar;

public class Utils {
	public static Calendar getTimeAfterInSecs(int secs)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, secs);
		return cal;
	}
	
	public static Calendar getCurrentTime()
	{
		Calendar cal = Calendar.getInstance();
		return cal;
	}
	
	
}
