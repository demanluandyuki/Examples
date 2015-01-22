package com.htc.globalsearch.imagesearch.client.resulttype;

import com.htc.globalsearch.imagesearch.client.GenericResultItem;
import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;
import com.htc.globalsearch.imagesearch.utils.ImageSearchUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class SdcardImageResultItem extends GenericResultItem {

	private static final String TAG = "ImageSearch.SdcardImageResultItem";
//	public int imageSize;
	public String name ="";
	public SdcardImageResultItem(PersonImageItem person)
	{
		this.mPerson = new PersonImageItem(person);
		mPerson.type = ImageSearchUtils.FIND_TYPE_IMAGE_STORE;
		name = getFileName(mPerson.Bitmap_Path);

	}
	
	@Override
	public void onLaunch(Context context) {
		Log.i(TAG, "SdcardImageResultItem onlaunch");
		Uri loacluri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(mPerson.image_id));//id is imagestore db's id
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setData(loacluri);
		context.startActivity(intent);
	}

	@Override
	public Bitmap getHeadImage(int width, int height) {
//		Log.i(TAG, "[getHeadImage] start");
		Bitmap newBitmap = null;
		Bitmap bitmap = BitmapFactory.decodeFile(mPerson.Bitmap_Path);
		if(bitmap!=null && width!=0 && height!=0)
		{
			newBitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);
			bitmap.recycle();
		}
		else
		{
			newBitmap = bitmap;
		}
		return newBitmap;
	}

	public String getImageSize()
	{
		return String.valueOf(mPerson.imageSize/1024+"K");
	}



	public String getFileName(String pathandname) {

		int start = pathandname.lastIndexOf("/");
		int end = pathandname.lastIndexOf(".");
		if (start != -1 && end != -1) {
			return pathandname.substring(start + 1, end);
		} else {
			return null;
		}

	} 
	
	@Override
	public String getTitle() {
		//path ->name
		return name;
	}

	@Override
	public String getMsg() {
		return getImageSize();
	}
}
