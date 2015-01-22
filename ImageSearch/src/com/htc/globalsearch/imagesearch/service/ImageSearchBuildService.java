package com.htc.globalsearch.imagesearch.service;

import java.util.ArrayList;

import com.htc.globalsearch.imagesearch.service.aidl.IBuildService;
import com.htc.globalsearch.imagesearch.service.aidl.ICallBack;
import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

/*
 * image operator running in service thread and listener the contentobserver
 * */
public class ImageSearchBuildService extends Service implements onServiceEngineListener{
	private static final String TAG = "ImageSearch.BuildService";

	public final static String ACTION = "com.htc.intent.imagesearch.BUILD_IMAGE";
	public final static String ACTION_METHOD = "action.method";
	public final static String ACTION_REBUILD_DB = "action.rebuild.db";
	
	private ImageSearchOperator mImageSearchOperator = null;
	private ImageSearchBuildServiceImpl iServiceImpl = null;
	private RemoteCallbackList<ICallBack> mCallbacks = new RemoteCallbackList<ICallBack>();
	private long mCurrentCookie = -1;
	@Override
	public IBinder onBind(Intent intent) {
		mCurrentCookie = intent.getLongExtra("cookie",-1);
		Log.i(TAG, "[onBind] mCurrentCookie:"+mCurrentCookie);
		iServiceImpl = new ImageSearchBuildServiceImpl(mImageSearchOperator);
		return iServiceImpl;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "[onCreate]");
		super.onCreate();
		mImageSearchOperator = new ImageSearchOperator(this,this);
		mImageSearchOperator.startOperatorThread();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "[onDestroy]");
		super.onDestroy();
		if(mImageSearchOperator!=null)
		{
			mImageSearchOperator.exitOperatorThread();
		}
		iServiceImpl = null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "[onStartCommand]");
		String method = intent.getStringExtra(ACTION_METHOD);
		if(method!=null)
		{
			if(method.equals(ACTION_REBUILD_DB))
			{
				//start rebuild db
				mImageSearchOperator.rebuildImageSearchDbHandle();
			}
		}
		
		return Service.START_NOT_STICKY;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "[onUnbind]");
		if(mImageSearchOperator!=null)
		{
			mImageSearchOperator.abortFindPersonHandle();
		}
		
		return super.onUnbind(intent);
	}
	
	private class ImageSearchBuildServiceImpl extends IBuildService.Stub{

		public ImageSearchOperator mOperator = null;
		public ImageSearchBuildServiceImpl(ImageSearchOperator operator)
		{
			mOperator = operator;
		}
		
		@Override
		public int getServiceStatus() throws RemoteException {
			return mOperator.mOperatorStatus;
		}

		@Override
		public int findPerson(String path, int filter)
				throws RemoteException {
			Log.i(TAG, "findPerson imageFilter:"+filter+" path: "+path);
			mOperator.findPersonHandle(path, filter,mCurrentCookie);
			return 0;
		}

		@Override
		public void registerCallback(ICallBack cb) throws RemoteException {
			// TODO Auto-generated method stub
			if(cb!=null)
			{
				mCallbacks.register(cb);
			}
		}

		@Override
		public void unregisterCallback(ICallBack cb) throws RemoteException {
			// TODO Auto-generated method stub
			if(cb!=null)
			{
				mCallbacks.unregister(cb);
			}
		}
		
	}

	@Override
	public void onEngineStatusUpdate(int status) {
		nofityStatusChanged(status);
	}

	@Override
	public void onQueryResult(long sessionid,String srcPath, int imageFilter,
			ArrayList<PersonImageItem> mResultItems) {
		if(mCurrentCookie == sessionid)
		{
			onFindPersonResult(srcPath,imageFilter,mResultItems);
		}
		else
		{
			Log.w(TAG, "onQueryResult old session:"+sessionid+" currentSession:"+mCurrentCookie);
		}
	}

	private void nofityStatusChanged(int status) {
//		synchronized (this) {
//			int n = mCallbacks.beginBroadcast();
//			Log.i(TAG, "nofityStatusChanged n:"+n);
//			try {
//				for (int i = 0; i < n; i++) {
//					mCallbacks.getBroadcastItem(i).onServiceStatusChanged(status);
//				}
//			} catch (RemoteException e) {
//				Log.e(TAG, "RemoteException:"+e.getMessage());
//			}
//			mCallbacks.finishBroadcast();
//		}
		
	}
	
	private void onFindPersonResult(String srcPath, int imageFilter,
			ArrayList<PersonImageItem> mResultItems)
	{
		try{
			synchronized (this) {
				Log.i(TAG, "onFindPersonResult filter:"+imageFilter);
				int n = mCallbacks.beginBroadcast();
				try {
					for (int i = 0; i < n; i++) {
						mCallbacks.getBroadcastItem(i).onQueryResult(mResultItems, srcPath, imageFilter);
					}
				} catch (RemoteException e) {
					Log.e(TAG, "remote error:"+e);
				}
				mCallbacks.finishBroadcast();
			}
		}catch (Exception e) {
			Log.i(TAG, "onFindPersonResult Wrong:"+e.getMessage());
		}
	}
}
