package com.htc.globalsearch.imagesearch.client;


import java.util.ArrayList;
import java.util.List;

import com.htc.globalsearch.imagesearch.AlertListAdapter.ListAdapterInfo;
import com.htc.globalsearch.imagesearch.client.resulttype.ContactResultItem;
import com.htc.globalsearch.imagesearch.client.resulttype.SdcardImageResultItem;
import com.htc.globalsearch.imagesearch.service.FaceRecognizeEngine;
import com.htc.globalsearch.imagesearch.service.ImageSearchBuildService;
import com.htc.globalsearch.imagesearch.service.aidl.IBuildService;
import com.htc.globalsearch.imagesearch.service.aidl.ICallBack;
import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;
import com.htc.globalsearch.imagesearch.utils.ImageSearchUtils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;

public class SearchEngine extends BroadcastReceiver implements Runnable{

	private static final String TAG = "ImageSearch.Engine";
	private Context mContext = null;
	private ContentResolver mContentResolver = null;
	private onEngineListener mListener=null;
	public Handler mEngineHandler =null;
	public Looper mEngineLooper =null;
	public SearchWorkQueue mWorkQueue = null;
	public int mbgEngineStatus = ImageSearchUtils.OPERATOR_STATUS_NONE;
	private IBuildService iServce = null;
	private long mSessionCookie = -1;
	
	public SearchEngine(Context context,onEngineListener listener) {
		mContext = context;
		mContentResolver = mContext.getContentResolver();
		mListener = listener;
		mbgEngineStatus = ImageSearchUtils.OPERATOR_STATUS_NONE;
		mSessionCookie = generateCookie();
		startHandleThread();
		registerBroadCast();
		bindService();
	}

	private ICallBack.Stub mCallback = new ICallBack.Stub() {
		
		@Override
		public void onServiceStatusChanged(int status) throws RemoteException {
			// TODO Auto-generated method stub
			mbgEngineStatus = status;
		}
		
		@Override
		public void onQueryResult(List<PersonImageItem> items, String path,
				int filter) throws RemoteException {
			Log.i(TAG, "ICallBack onQueryResult filter:"+filter);
			if(items.size()>0)
			{
				Log.i(TAG, "ICallBack onQueryResult items:"+items.size());

				ArrayList<GenericResultItem> mReults = new ArrayList<GenericResultItem>(items.size());
				
				switch(filter)
				{
				case ImageSearchUtils.FIND_TYPE_CONTACT:
					for(PersonImageItem person:items)
					{
						ContactResultItem contactitem = new ContactResultItem(person, mContext);
						mReults.add(contactitem);
					}
					break;
				case ImageSearchUtils.FIND_TYPE_IMAGE_STORE:
					for(PersonImageItem person:items)
					{
						SdcardImageResultItem contactitem = new SdcardImageResultItem(person);
						mReults.add(contactitem);
					}
					break;
				}
				
				if(mWorkQueue!=null)
				{
					mWorkQueue.notifyWorkQueue(mReults,filter);
				}
			}
			else
			{
				Log.i(TAG, "ICallBack onQueryResult items null");
				if(mWorkQueue!=null)
				{
					mWorkQueue.notifyWorkQueue(null,filter);
				}
			}
		}
	};
	
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onServiceDisconnected");
			iServce = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			iServce = IBuildService.Stub.asInterface(service);
			Log.i(TAG, "onServiceConnected");
			try {
				iServce.registerCallback(mCallback);
			} catch (RemoteException e) {
				Log.w(TAG, "onServiceConnected wrong:"+e.getMessage());
			}
		}
	};
	
	private void bindService()
	{
		Log.i(TAG, "[bindService] cookie:"+mSessionCookie);
		Intent intent = new Intent();
		intent.setAction(ImageSearchBuildService.ACTION);
		intent.putExtra("cookie", mSessionCookie);
		mContext.startService(intent);
		mContext.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	
	private void unBindService() {
		try {
			if (iServce != null) {
				iServce.unregisterCallback(mCallback);
			}
			mContext.unbindService(conn);
		} catch (Exception e) {
		}
	}
	
	public void findPersonInService(String path, int imageFilter) throws RemoteException
	{
		Log.i(TAG, "findPersonInService imageFilter:"+imageFilter);
		if(iServce!=null)
		{
			iServce.findPerson(path, imageFilter);
		}
	}

	private void startHandleThread()
	{
		new Thread(this).start();
	}
	
	private void exitHandleThread(){
		if(mEngineLooper!=null)
		{
			mEngineLooper.quit();
			mEngineLooper = null;
		}
		mEngineHandler = null;
		mContentResolver = null;
	}
	
	public boolean isDeviceSupportEngine()
	{
		return FaceRecognizeEngine.checkDeviceSupport();
	}
		
	public void Release() {
		unBindService();
		unRegisterReceiver();

		exitHandleThread();
		Log.i(TAG, "[Release] done");
	}

	public void abortSearchImageAsync()
	{

	}
	
	public void SearchImageAsyncHandle(String path,int imageFilter)
	{
		Log.i(TAG, "SearchImageAsyncHandle imageFilter"+imageFilter);
		//send message to async all search image one by one
		Message msg = mEngineHandler.obtainMessage();
		msg.what = ImageSearchUtils.ENGINE_START_SEARCH;
		Bundle data = new Bundle();
		data.putString("path", path);
		data.putInt("filter", imageFilter);
		msg.setData(data);
		mEngineHandler.sendMessage(msg);
	}
	
	private void searchImageTask(String path,int imageFilter)
	{
		try{
			if(mWorkQueue == null)
			{
				mWorkQueue = new SearchWorkQueue("clientEngine",this);
			}
			mWorkQueue.prepareWorkQueue(imageFilter, path);
			mWorkQueue.query();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void searhImageQueueHandle()
	{
		Message msg = mEngineHandler.obtainMessage();
		msg.what = ImageSearchUtils.ENGINE_SEARCH_QUEUE;
		mEngineHandler.sendMessage(msg);
	}
	
	private void searchImageQueueTask()
	{
		if(mWorkQueue!=null)
		{
			mWorkQueue.query();
		}
	}
	
	public void onSearchResult(String path, ArrayList<GenericResultItem> itmes)
	{
		if(mListener!=null)
		{
			mListener.onSearchResult(path, itmes);
		}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
	}
	
	public void registerBroadCast()
	{

	}
	
	public void unRegisterReceiver()
	{
	}

	
	public void decodeUriToBitmapHandle(Uri uri)
	{
		Message msg = mEngineHandler.obtainMessage();
		msg.what = ImageSearchUtils.ENGINE_OP_DEOCDE_URI;
		Bundle data = new Bundle();
		data.putParcelable("uri", uri);
		msg.setData(data);
		mEngineHandler.sendMessage(msg);
	}
	
	public void prepareSrcImageItemsHandle()
	{
		Message msg = mEngineHandler.obtainMessage();
		msg.what = ImageSearchUtils.ENGINE_OP_PREPARE_SRC_APP;
		mEngineHandler.sendMessage(msg);
	}
	
	private void decodeUriTask(Uri uri) {
		try {
			Log.i(TAG, "DecodeUriTask uri" + uri.toString());
			String[] projects = new String[1];
			projects[0] = MediaStore.Images.Media.DATA;
			Cursor cursor = mContentResolver.query(uri, projects, null, null,
					null);
			String path = null;
			if (cursor != null) {
				while (cursor.moveToNext()) {
					path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					break;
				}
				cursor.close();
			}

			if (!path.isEmpty()) {
				Bitmap bitmap = BitmapFactory.decodeFile(path);
				if (mListener != null) {
					mListener.onEngineDecodeUri(bitmap, path);
				}
			} else {

				if (mListener != null) {
					mListener.onEngineDecodeUri(null, null);
				}
			}

		} catch (Exception e) {
			Log.i(TAG, "DecodeUriTask error:" + e.getMessage());
			if (mListener != null) {
				mListener.onEngineDecodeUri(null, null);
			}
		} finally {
		}
	}
	
	private void prepareSrcSelectItemTask()
	{
		try {
			PackageManager pm = mContext.getPackageManager();
			Intent mainIntent = new Intent(ImageSearchUtils.ACTION_CAMERA_PICKER);
			List<ResolveInfo> resolveInfos = pm
	                .queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
			ArrayList<ListAdapterInfo> infos = new ArrayList<ListAdapterInfo>(2);
			if(!resolveInfos.isEmpty()&& resolveInfos.size()>0)
			{
				Log.i(TAG, "PrepareSrcSelectItemTask Camera is find");
				ResolveInfo reInfo = resolveInfos.get(0);
				String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
				Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
				ListAdapterInfo info = new ListAdapterInfo(appLabel,icon,ImageSearchUtils.REQUEST_CAMERA_PICK);
				infos.add(info);
			}
			
			Intent mainIntent2 = new Intent(ImageSearchUtils.ACTION_GALLERY_PICKER);
			List<ResolveInfo> resolveInfos2 = pm
	                .queryIntentActivities(mainIntent2, PackageManager.MATCH_DEFAULT_ONLY);
			if(!resolveInfos2.isEmpty() && resolveInfos2.size()>0)
			{
				Log.i(TAG, "PrepareSrcSelectItemTask Gallery is find");
				ResolveInfo reInfo2 = resolveInfos2.get(0);
//				String activityName2 = reInfo2.activityInfo.name; // 获得该应用程序的启动Activity的name
//				String pkgName2 = reInfo2.activityInfo.packageName; // 获得应用程序的包名
				String appLabel2 = (String) reInfo2.loadLabel(pm); // 获得应用程序的Label
				Drawable icon2 = reInfo2.loadIcon(pm); // 获得应用程序图标
				ListAdapterInfo info2 = new ListAdapterInfo(appLabel2,icon2,ImageSearchUtils.REQUEST_GALLERY_PICK);
				infos.add(info2);
			}
			if(mListener!=null)
			{
				mListener.onPrepareActivityInfo(infos);
			}
			
		} catch (Exception e) {
			Log.i(TAG, "PrepareSrcSelectItemTask error:"+e.getMessage());
		}
		finally{
		}
	}
	
	public static class EngineHandler extends Handler{
		
		private SearchEngine mSearchEngine = null;
		public EngineHandler(Looper loop, SearchEngine engine)
		{
			super(loop);
			mSearchEngine = engine;
		}

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case ImageSearchUtils.ENGINE_OP_DEOCDE_URI:
				Bundle datauri = msg.getData();
				Uri uri = datauri.getParcelable("uri");
				mSearchEngine.decodeUriTask(uri);
				break;
			case ImageSearchUtils.ENGINE_OP_PREPARE_SRC_APP:
				mSearchEngine.prepareSrcSelectItemTask();
				break;
			case ImageSearchUtils.ENGINE_START_SEARCH:
				Bundle data = msg.getData();
				String path = data.getString("path");
				int imageFilter = data.getInt("filter");
				mSearchEngine.searchImageTask(path, imageFilter);
				break;
			case ImageSearchUtils.ENGINE_SEARCH_QUEUE:
				mSearchEngine.searchImageQueueTask();
				break;
			}
			
		}
		
	}
	
	@Override
	public void run() {
		Looper.prepare();
		mEngineLooper = Looper.myLooper();
		mEngineHandler = new EngineHandler(mEngineLooper,this);
		Log.i(TAG, "frontground engine handle running TID:"+Process.myTid());
		Looper.loop();		
	}

	
	private long generateCookie()
	{
		return System.currentTimeMillis();
	}


	
}
