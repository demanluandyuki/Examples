package com.htc.globalsearch.imagesearch.service.buildtype;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import com.htc.globalsearch.imagesearch.service.GeneralBuildType;
import com.htc.globalsearch.imagesearch.service.ImageSearchOperator;
import com.htc.globalsearch.imagesearch.service.provider.ImageSearchProvider.SearchMetaData;

public class ContactImageBuildType extends GeneralBuildType {

	private static final String TAG = "ImageSearch.ContactsImageBuildType";
	private static ArrayList<ContactRecord> mImageContacts = null;
	private static ContactsChangedObserver mContactObserver = null;
	private static onImageDbUpdate mUpdateListener = null;
	public static void onRebuild()
	{
		rebuild();
	}
	
	public static void onUpdate()
	{
		updateImageContact();
	}
	
	public static void onUpdateEnd()
	{
		mImageContacts = null;
	}
	
	public static void registerContentObserver(onImageDbUpdate mUpdateListener) {
		Log.i(TAG, "[registerContentObserver]");
		if (mContentResolver != null) {
			mContactObserver = new ContactsChangedObserver(new Handler());
			mContentResolver.registerContentObserver(
					ContactsContract.Data.CONTENT_URI, true, mContactObserver);
			ContactImageBuildType.mUpdateListener = mUpdateListener;
		} else {
			Log.w(TAG, "mContentResolver is null");
		}

	}
	
	public static void unRegisterContentObserver()
	{
		mUpdateListener = null;
		Log.i(TAG, "[unRegisterContentObserver]");
		if (mContentResolver != null) {
			mContentResolver.unregisterContentObserver(mContactObserver);
		}
	}
	
	private static void rebuild() {
		try {
			Log.i(TAG, "[rebuild]");
			ArrayList<ContactRecord> mContactIdRecord = readContactId();
			int length = mContactIdRecord.size();
			if (length == 0) {
				throw new IllegalArgumentException("[rebuild] there is no image in contacts");
			}

			for (ContactRecord mRecord : mContactIdRecord) {
				Bitmap bitmap = getContactBitmap(mRecord.contact_id);
				if (bitmap != null) {
					mRecord.setBitmapSize(bitmap.getByteCount());
					ArrayList<Integer> persionIds = addToAlbum(bitmap);
					// add to database
					int persionLength = persionIds.size();
					if (persionLength != 1) {
						Log.i(TAG,
								"[rebuild] we only store contact head with one face! contactId "
										+ mRecord.contact_id);
						break;
					}
					addorupdateSearchDatabase(persionIds.get(0), mRecord);
				}
			}
		} catch (Exception e) {
			Log.w(TAG, "[rebuild] wrong" + e.getMessage());
		}

	}
	
	private static ArrayList<ContactRecord> readContactId() {
		ArrayList<ContactRecord> mContactIds = null;
		String[] projection = new String[2];
		projection[0] = ContactsContract.Data.CONTACT_ID;
		projection[1] = ContactsContract.CommonDataKinds.Contactables.CONTACT_LAST_UPDATED_TIMESTAMP;
		String selection = ContactsContract.Contacts.Data.MIMETYPE + "=?";
		String[] whereArg = new String[1];
		whereArg[0] = "vnd.android.cursor.item/photo";
		Cursor cursor = mContentResolver.query(
				ContactsContract.Data.CONTENT_URI, projection, selection,
				whereArg, null);
		if (cursor != null) {
			int count = cursor.getCount();
			if(count>0)
			{
				mContactIds = new ArrayList<ContactRecord>(count);
			}
			while (cursor.moveToNext()) {
				int contactId = cursor.getInt(cursor
						.getColumnIndex(ContactsContract.Data.CONTACT_ID));
				long timestamp = cursor
						.getLong(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.CONTACT_LAST_UPDATED_TIMESTAMP));
				ContactRecord record = new ContactRecord(contactId, 0, timestamp);
				mContactIds.add(record);
			}
			cursor.close();
		}
		
		if(mContactIds == null)
		{
			mContactIds = new ArrayList<ContactRecord>();
			mContactIds.clear();
		}
		
		return mContactIds;
	}

	public static Bitmap getContactBitmap(int contactId) {

		Bitmap bitmap = null;
		try {
			Uri uri = ContentUris.withAppendedId(
					ContactsContract.Contacts.CONTENT_URI, contactId);
			InputStream inStream = ContactsContract.Contacts
					.openContactPhotoInputStream(mContentResolver, uri);
			bitmap = BitmapFactory.decodeStream(inStream);
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			bitmap = null;
		}
		return bitmap;
	}
	
	private static void addorupdateSearchDatabase(Integer persionId,
			ContactRecord record) {
		Log.i(TAG, "[addorupdateSearchDatabase] persionId " + persionId
				+ " ContactId " + record.contact_id);
		ContentValues values = new ContentValues();
		values.put(SearchMetaData.PERSIOD_ID, persionId);
		values.put(SearchMetaData.CONTACT_ID, record.contact_id);
		values.put(SearchMetaData.TYPE, SearchMetaData.IMAGE_TYPE_CONTACT);
		values.put(SearchMetaData.BITMAP_SIZE, record.bitmapsize);
		values.put(SearchMetaData.LAST_UPDATE_TIME_STAMP, record.lastUpdateTimeStamp);

		String selection = SearchMetaData.CONTACT_ID + "=?";
		String[] selectionArg = new String[1];
		selectionArg[0] = String.valueOf(record.contact_id);
		// find if there has the database
		Cursor cursor = mContentResolver.query(SearchMetaData.CONTENT_URI,
				null, selection, selectionArg, null);
		// if imagesearch.db has already this contactId, it just update the
		// persionId
		// otherwise insert it

		if (cursor != null) {
			int oldPersionId = 0xffff;
			int id = -1;
			if (cursor.moveToFirst()) {
				oldPersionId = cursor.getInt(cursor
						.getColumnIndex(SearchMetaData.PERSIOD_ID));
				id = cursor.getInt(cursor.getColumnIndex(SearchMetaData._ID));

			}
			cursor.close();

			if (oldPersionId == 0xffff) {
				// no old item find
				Log.i(TAG, "[addorupdateSearchDatabase] insert new");
				mContentResolver.insert(SearchMetaData.CONTENT_URI, values);
			} else if (oldPersionId != persionId) {
				Log.i(TAG, "[addorupdateSearchDatabase] update db id:" + id);
				Uri uri = ContentUris.withAppendedId(
						SearchMetaData.CONTENT_URI, id);
				mContentResolver.update(uri, values, null, null);
			}

		} else {
			mContentResolver.insert(SearchMetaData.CONTENT_URI, values);
		}

	}
	
	public static void updateImageContact() {
		try {
			Log.i(TAG, "[updateImageContact]");
			ArrayList<ContactRecord> mContactDbRecords = readContactId();
			int length = mContactDbRecords.size();
			Log.i(TAG, "[updateImageContact] new Contacts count:" + length);
			if (length == 0) {
				// contact head has been deleted.
				deleteAllContactImage();
			} else {
				mImageContacts = new ArrayList<ContactRecord>(
						length);
				// this is read from   imagesearch.db
				getDatabaseContactInfo(mImageContacts);
				Log.i(TAG, "[updateImageContact] contact count in imagesearch.db "
						+ mImageContacts.size());

				ArrayList<Integer> mImageContactIds = ContactRecord
						.RecordToContactIds(mImageContacts);
				ArrayList<Integer> mContactDbContactIds = ContactRecord
						.RecordToContactIds(mContactDbRecords);
				// delete old contactid if not in new contacts Vector
				for (Integer id : mImageContactIds) {
					if (!mContactDbContactIds.contains(id)) {
						deleteContactImage(id);
					}
				}

				// old one has been deleted we just add or update new Contacts
				// Vectors
				Bitmap bitmap = null;
				int index = -1;
				for (ContactRecord updateRecord : mContactDbRecords) {
					bitmap = getContactBitmap(updateRecord.contact_id);
					if (bitmap != null) {
						updateRecord.setBitmapSize(bitmap.getByteCount());
						/**
						 * contactid in imagesearch.db, bitmap size is same,
						 * lasttimeupdate is same, doing nothing
						 * */
						if (mImageContactIds.size()>0 && mImageContactIds.contains(updateRecord.contact_id)) {
							// contains, may be need update
							index = mImageContactIds
									.indexOf(updateRecord.contact_id);
							ContactRecord imageConRecord = mImageContacts
									.get(index);
							if (updateRecord.compareRecord(imageConRecord)) {
								// same item, just retuen
							} else {
								ArrayList<Integer> persionIds = addToAlbum(bitmap);
								// add to database
								if (persionIds.size() != 1) {
									Log.i(TAG,
											"[updateImageContact] we only store contact head with one face! contactId "
													+ updateRecord.contact_id);
									break;
								}
								addorupdateSearchDatabase(persionIds.get(0), updateRecord);
							}
						} else {
							ArrayList<Integer> persionIds = addToAlbum(bitmap);
							// add to database
							if (persionIds.size() != 1) {
								Log.i(TAG,
										"[updateImageContact] we only store contact head with one face! contactId "
												+ updateRecord.contact_id);
								break;
							}
							addorupdateSearchDatabase(persionIds.get(0), updateRecord);
						}

					}

				}
			}
		} catch (Exception e) {
			Log.w(TAG, "[updateImageContact] exception " + e.getMessage());
		}
	}
	
	private static void deleteAllContactImage() {
		String seletction = SearchMetaData.TYPE + "=?";
		String[] seletctionArg = new String[1];
		seletctionArg[0] = String.valueOf(SearchMetaData.IMAGE_TYPE_CONTACT);

		int count = mContentResolver.delete(SearchMetaData.CONTENT_URI,
				seletction, seletctionArg);
		Log.i(TAG, "[deleteAllContactImage] count::" + count);
	}

	private static void deleteContactImage(int contactid) {
		Log.i(TAG, "[deleteContactImage] contactid " + contactid);
		String seletction = SearchMetaData.CONTACT_ID + "=?";
		String[] seletctionArg = new String[1];
		seletctionArg[0] = String.valueOf(contactid);

		int count = mContentResolver.delete(SearchMetaData.CONTENT_URI,
				seletction, seletctionArg);
		Log.i(TAG, "[deleteContactImage] count::" + count);
	}
	
	private static void getDatabaseContactInfo(
			ArrayList<ContactRecord> contactRecords) {
		contactRecords.clear();
		String[] projection = new String[3];
		projection[0] = SearchMetaData.BITMAP_SIZE;
		projection[1] = SearchMetaData.CONTACT_ID;
		projection[2] = SearchMetaData.LAST_UPDATE_TIME_STAMP;
		String selection = SearchMetaData.TYPE + "=?";
		String[] selectionArg = new String[1];
		selectionArg[0] = String.valueOf(SearchMetaData.IMAGE_TYPE_CONTACT);
		Cursor cursor = mContentResolver.query(SearchMetaData.CONTENT_URI,
				projection, selection, selectionArg, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int bitmapsize = cursor.getInt(cursor
						.getColumnIndex(SearchMetaData.BITMAP_SIZE));
				int contactId = cursor.getInt(cursor
						.getColumnIndex(SearchMetaData.CONTACT_ID));
				long lastTimeStamp = cursor.getLong(cursor.getColumnIndex(SearchMetaData.LAST_UPDATE_TIME_STAMP));
				ContactRecord record = new ContactRecord(contactId, bitmapsize, lastTimeStamp);
				contactRecords.add(record);
			}
			cursor.close();
		}
	}
	
	private static class ContactsChangedObserver extends ContentObserver {
		public ContactsChangedObserver(Handler handler) {
			super(handler);
		}

		public void onChange(boolean selfChange) {
			// read and update contact photo
			if(mUpdateListener!=null)
			{
				mUpdateListener.onChangedObserver(ImageSearchOperator.OPTION_UPDATE_TYPE_CONTACT);
			}
		}
	}
	
	public static class ContactRecord{
		public int contact_id;
		public int bitmapsize;
		public long lastUpdateTimeStamp;
		public ContactRecord(int contact_id,int bitmapsize,long lastUpdateTimeStamp) {
			this.contact_id = contact_id;
			this.bitmapsize = bitmapsize;
			this.lastUpdateTimeStamp = lastUpdateTimeStamp;
		}
		
		public void setBitmapSize(int bitmapsize)
		{
			this.bitmapsize = bitmapsize;
		}
		
		public boolean compareRecord(ContactRecord record)
		{
			if((record.contact_id == contact_id) && 
					(record.bitmapsize == bitmapsize) && 
					(record.lastUpdateTimeStamp == lastUpdateTimeStamp))
			{
				return true;
			}
			
			return false;
		}
		
		public static ArrayList<Integer> RecordToContactIds(
				ArrayList<ContactRecord> records) {
			ArrayList<Integer> mContactarray = null;
			if (records != null && records.size() > 0) {
				mContactarray = new ArrayList<Integer>(records.size());
				for (ContactRecord mRecord : records) {
					mContactarray.add(mRecord.contact_id);
				}
			}
			else
			{
				mContactarray = new ArrayList<Integer>();
				mContactarray.clear();
			}

			return mContactarray;
		}
	}
}
