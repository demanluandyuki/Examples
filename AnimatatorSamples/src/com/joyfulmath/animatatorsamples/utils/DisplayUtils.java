package com.joyfulmath.animatatorsamples.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtils {
	
	public static DisplayMetrics getDisplayMetrics(Context context)
	{
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm;
	}
	
}
