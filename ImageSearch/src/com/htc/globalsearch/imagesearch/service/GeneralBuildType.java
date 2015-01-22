package com.htc.globalsearch.imagesearch.service;

import java.util.ArrayList;

import com.qualcomm.snapdragon.sdk.face.FacialProcessingConstants;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;

public abstract class GeneralBuildType {

	private static final String TAG = "ImageSearch.GeneralBuildType";
	public static Context mContext = null;
	public static ContentResolver mContentResolver = null;
	
	public GeneralBuildType(){}
	
	public static void setContext(Context context)
	{
		mContext = context;
		if(context == null)
		{
			mContentResolver = null;
		}
		else
		{
			mContentResolver = context.getContentResolver();
		}
	}
	
	public static ArrayList<Integer> addToAlbum(String path) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		//try to scale the bitmap to 96%
		Bitmap newBitmap = null;
		int width = bitmap.getWidth();
		if(width>500)
		{
			//try to get 500
			int scale = width/500;
			width = width/(scale+1);
			int height = bitmap.getHeight()/(scale+1);
			newBitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);
		}
		else
		{
			newBitmap = bitmap;
		}
		return addToAlbum(newBitmap);
	}
	
	public static ArrayList<Integer> addToAlbum(Bitmap bitmap) {
		Log.i(TAG, "[addToAlbum] bitmap");
		ArrayList<Integer> persion_ids = null;
		try {
			if (bitmap != null) {
				persion_ids = FaceRecognizeEngine.AddorFindPerson(bitmap,
						mContext);
			} else {
				Log.w(TAG, "addToAlbum bitmap is null");
			}
		} catch (Exception e) {
			persion_ids = new ArrayList<Integer>(1);
			persion_ids.clear();
			persion_ids.add(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
			Log.w(TAG, "addToAlbum find persion wrong " + e.getMessage());
		}
		return persion_ids;
	}
	
	public interface onImageDbUpdate{
		void onChangedObserver(int op);
	}
}
