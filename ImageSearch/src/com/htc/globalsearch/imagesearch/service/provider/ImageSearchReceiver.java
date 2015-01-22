package com.htc.globalsearch.imagesearch.service.provider;

import com.htc.globalsearch.imagesearch.service.ImageSearchBuildService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ImageSearchReceiver extends BroadcastReceiver {

	public final static String TAG = "ImageSearch.Receiver";
	public final static String BUILD_SEARCH_IMAGE_ACTION = "com.htc.intent.action.REBUILDIMAGE";

	
	@Override
	public void onReceive(Context context, Intent intent) {
		String actionStr = intent.getAction();
		if(actionStr.equals(BUILD_SEARCH_IMAGE_ACTION))
		{
			Log.i(TAG, "[onReceive]start rebuild all database");
			//rebuild and update are all running in service!
			startCustomService(context);
		}
		else if(actionStr.equals("android.intent.action.BOOT_COMPLETED"))
		{
			Log.i(TAG, "[onReceive]start servie BOOT_COMPLETED");
			Intent intentservice = new Intent();
			intentservice.setAction(ImageSearchBuildService.ACTION);
			context.startService(intentservice);
		}
	}
	
	private void startCustomService(Context context)
	{
		Intent intent = new Intent();
		intent.setAction(ImageSearchBuildService.ACTION);
		intent.putExtra(ImageSearchBuildService.ACTION_METHOD, ImageSearchBuildService.ACTION_REBUILD_DB);
		context.startService(intent);
	}
		
	


}
