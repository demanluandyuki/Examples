package com.htc.globalsearch.imagesearch.client;


import java.lang.ref.SoftReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;

public abstract class GenericResultItem {
	
	public PersonImageItem mPerson = null;
//	public Bitmap mHeadbitamp = null;
	public boolean bTaskStopFlag = false;
	public SoftReference<ImageView> mImageView = null;//for show in adapter
	public int postion = -1;
	public abstract void onLaunch(Context context);
	public abstract Bitmap getHeadImage(int width,int height);
	public abstract String getTitle();
	public abstract String getMsg();
	
	public void Release()
	{
		mPerson = null;
		mImageView.clear();
		mImageView = null;
	}

}
