package com.htc.globalsearch.imagesearch.client.resulttype;

import com.htc.globalsearch.imagesearch.client.GenericResultItem;
import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;
import com.htc.globalsearch.imagesearch.service.buildtype.ContactImageBuildType;
import com.htc.globalsearch.imagesearch.utils.ImageSearchUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactResultItem extends GenericResultItem {

	private static final String TAG = "ImageSearch.ContactResultItem";
	private String mContactName =null;
	private String mNumber =null;
	public ContactResultItem(PersonImageItem person,Context context)
	{
		this.mPerson = new PersonImageItem(person);
		mPerson.type = ImageSearchUtils.FIND_TYPE_CONTACT;
		mNumber = "";
		mContactName ="";
		prepareGetContactFiled(context);
	}
	
	@Override
	/*if width =0 or height =0, it won't resize the bitmap
	 * 
	 * */
	public Bitmap getHeadImage(int width,int height)
	{
		//get image from contactId;
		Bitmap newBitmap = null;
		Bitmap mImage = ContactImageBuildType.getContactBitmap(mPerson.contact_id);
		if(width==0||height ==0)
		{
			newBitmap = mImage;
		}
		else
		{
			newBitmap = ThumbnailUtils.extractThumbnail(mImage, width, height);
			mImage.recycle();
		}
		//resize image by using header,maybe we should release below bitmap later
		return newBitmap;
	}

	@Override
	public void onLaunch(Context context) {
		Log.i(TAG, "ContactResultItem onlaunch");
		Uri loacluri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(mPerson.contact_id));
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setData(loacluri);
		context.startActivity(intent);
	}
	
	public void prepareGetContactFiled(Context context) {
		if (mContactName.isEmpty() && mNumber.isEmpty()) {
			int has_phone_number = 0;
			// need to attach db
			ContentResolver mResolver = context.getContentResolver();
			// get number
			String[] projection = new String[2];
			projection[0] = ContactsContract.Contacts.DISPLAY_NAME;
			projection[1] = ContactsContract.Contacts.HAS_PHONE_NUMBER;

			String selection = ContactsContract.Contacts._ID + "=?";
			String[] selectionArgs = new String[1];
			selectionArgs[0] = String.valueOf(mPerson.contact_id);

			Cursor cursor = mResolver.query(
					ContactsContract.Contacts.CONTENT_URI, projection,
					selection, selectionArgs, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					mContactName = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					has_phone_number = cursor
							.getInt(cursor
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					break;
				}
				cursor.close();
			}

			if (has_phone_number > 0) {
				Cursor cursorPhone = mResolver.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + mPerson.contact_id, null, null);
				if (cursorPhone != null) {
					while (cursorPhone.moveToNext()) {
						mNumber = cursorPhone
								.getString(cursorPhone
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						break;
					}

					cursorPhone.close();
				}
			}
			Log.i(TAG, "[prepareGetContactFiled] "+ mContactName+" number:"+mNumber);
		}
	}

	@Override
	public String getTitle()
	{
		return mContactName;
	}
	
	@Override
	public String getMsg()
	{
		return mNumber;
	}
}
