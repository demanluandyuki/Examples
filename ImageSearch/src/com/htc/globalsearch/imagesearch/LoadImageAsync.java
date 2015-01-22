package com.htc.globalsearch.imagesearch;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.htc.globalsearch.imagesearch.client.GenericResultItem;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class LoadImageAsync {
	
	private static final String TAG = "ImageSearch.LoadImageAsync";
	private ExecutorService pool;
	private final int POOL_SIZE = 5;
	private final int CACHE_SIZE = 30;
	private HashMap<GenericResultItem, SoftReference<Bitmap>> mBitmapCache;
	
	public LoadImageAsync()
	{
		pool = Executors.newFixedThreadPool(POOL_SIZE);
		mBitmapCache = new HashMap<GenericResultItem, SoftReference<Bitmap>>(CACHE_SIZE);
	}
	
	public Bitmap loadBitmapAsync(final GenericResultItem item,final ImageView imageview,final ImageCallBack imagecallback)
	{
		final Handler handle = new Handler(){
            public void handleMessage(Message message) {
        		Log.i(TAG, "[handleMessage]:"+item.getTitle());
            	Bitmap bitmap = (Bitmap) message.obj;
            	if(!item.bTaskStopFlag)
            	{
                	imagecallback.imageLoaded(bitmap,imageview,item);
            	}
            } 
		};
		//in thread loading, and in handle to invalidate
		Log.i(TAG, "[loadBitmapAsync]:"+item.getTitle());
		if(mBitmapCache.containsKey(item))
		{
			Bitmap bitmap = mBitmapCache.get(item).get();
			if(bitmap!=null)
			{
//				return bitmap;
				Message msg = handle.obtainMessage();
				msg.obj = bitmap;
				handle.sendMessage(msg);
				return null;
			}
		}
		

		
		Thread loadingTask = new Thread(){
			public void run(){
				Bitmap bitmap = loadImageFromItem(item);
				mBitmapCache.put(item, new SoftReference<Bitmap>(bitmap));
				if(!item.bTaskStopFlag)
				{
					Message msg = handle.obtainMessage();
					msg.obj = bitmap;
					handle.sendMessage(msg);
				}
			}
		};
		item.bTaskStopFlag = false;
		pool.submit(loadingTask);
		return null;
	}
	
	protected Bitmap loadImageFromItem(GenericResultItem item) {
		// TODO Auto-generated method stub
		return  item.getHeadImage(400, 400);
	}

	public void cancelLoading(GenericResultItem item)
	{
		//cancel the thread
		item.bTaskStopFlag = true;
	}
	
	public void freeCacheBitmap(GenericResultItem item)
	{
		if(mBitmapCache.containsKey(item))
		{
			Bitmap bitmap = mBitmapCache.get(item).get();
			mBitmapCache.remove(item);
			if(bitmap!=null)
			{
				bitmap.recycle();
				bitmap = null;
			}
		}
	}
	
	public void Reset()
	{
		for(SoftReference<Bitmap> sBitmap:mBitmapCache.values())
		{
			if(sBitmap.get()!=null)
			{
				sBitmap.get().recycle();
			}
		}
		mBitmapCache.clear();
	}
	
	public void Release()
	{
		//todo release this loading image engine
		pool.shutdownNow();
		pool = null;
	}
	
	public interface ImageCallBack{
		public void imageLoaded(Bitmap bitmap,ImageView view, GenericResultItem item);
	}
}
