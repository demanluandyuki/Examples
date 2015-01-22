package com.htc.globalsearch.imagesearch.service.buildtype;

import java.util.ArrayList;
import java.util.Vector;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.htc.globalsearch.imagesearch.service.GeneralBuildType;
import com.htc.globalsearch.imagesearch.service.ImageSearchOperator;
import com.htc.globalsearch.imagesearch.service.provider.ImageSearchProvider.SearchMetaData;

public class SdcardImageBuildType extends GeneralBuildType {
	
	private static final String TAG = "ImageSearch.SdcardImageBuildType";
	private static ImageStoreObserver mImageStoreObserver = null;
	private static ArrayList<String> mPathHash = null;
	private static ArrayList<ImageRecord> mUpdateRecords = null;
	private static onImageDbUpdate mUpdateListener = null;

	public static void onRebuild()
	{
		rebuildImageStore();
	}
	
	public static void onUpdate()
	{
		updateImageStore();
	}
	
	public static void onUpdateStart()
	{
		Log.i(TAG, "[onUpdateStart]");
		mUpdateRecords = readImageStore();
		if (mUpdateRecords != null && mUpdateRecords.size() >= 0) {
			mPathHash = new ArrayList<String>(mUpdateRecords.size());
			mPathHash.clear();
			getDatabaseImagePath(mPathHash);
			if (mUpdateRecords.size() == mPathHash.size()) {
				throw new IllegalArgumentException(
						"[onUpdateStart] the imagestore is OK, no need to update!");
			}
		} else {
			mPathHash = new ArrayList<String>(50);
			mPathHash.clear();
			getDatabaseImagePath(mPathHash);
		}	
	}
	
	public static void onUpdateEnd()
	{
		mUpdateRecords = null;
		mPathHash = null;
	}
	
	public static void registerContentObserver(onImageDbUpdate listener) {
		Log.i(TAG, "[registerContentObserver]");
		if (mContentResolver != null) {
			mImageStoreObserver = new ImageStoreObserver(new Handler());
			mContentResolver.registerContentObserver(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, mImageStoreObserver);
			mUpdateListener = listener;
		} else {
			Log.w(TAG, "[registerContentObserver] mContentResolver is null");
		}

	}
	
	public static void unRegisterContentObserver()
	{
		Log.i(TAG, "[unRegisterContentObserver]");
		if (mContentResolver != null) {
			mContentResolver.unregisterContentObserver(mImageStoreObserver);
		}
		mImageStoreObserver = null;
	}
	
	private static boolean rebuildImageStore() {
		boolean imageFlag = false;
		try {
			Log.i(TAG, "[rebuildImageStore]");
			// read bitmap from imagestore
			ArrayList<ImageRecord> mImageRecord = readImageStore();
			if (mImageRecord == null || mImageRecord.size() == 0) {
				throw new IllegalArgumentException(
						"[rebuildImageStore]there is no image in sdacrd");
			} else {
				Log.i(TAG, "[rebuildImageStore] imagestore count " + mImageRecord.size());
			}

			for (ImageRecord record : mImageRecord) {
				ArrayList<Integer> persion_ids = addToAlbum(record.path);
				addorupdateSearchDatabase(persion_ids, record);
			}

		} catch (Exception e) {
			imageFlag = false;
			// e.printStackTrace();
			Log.w(TAG, "[rebuildImageStore] wrong " + e.getMessage());
		}

		return imageFlag;
	}	
	
	private static ArrayList<ImageRecord> readImageStore() {
		ArrayList<ImageRecord> pathVector = null;
		String[] projection = new String[3];
		projection[0] = MediaStore.Images.Media.DATA;
		projection[1] = MediaStore.Images.Media._ID;
		projection[2] = MediaStore.Images.Media.SIZE;
		Cursor cursor = mContentResolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, null);
		if (cursor != null) {
			int count = cursor.getCount();
			if (count > 0) {
				pathVector = new ArrayList<ImageRecord>(count);
				while (cursor.moveToNext()) {
					String Path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					int id = cursor.getInt(cursor
							.getColumnIndex(MediaStore.Images.Media._ID));
					int size = cursor.getInt(cursor
							.getColumnIndex(MediaStore.Images.Media.SIZE));
					ImageRecord record = new ImageRecord(id, Path, size);
					pathVector.add(record);
				}
			}
			cursor.close();
		} else {
			throw new IllegalArgumentException("[readImageStore] nothing in imagestore");
		}

		return pathVector;
	}

	private static void addorupdateSearchDatabase(
			ArrayList<Integer> persion_ids, ImageRecord record) {
		Log.i(TAG, "[addorupdateSearchDatabase] path " + record.path);
		int persionLength = persion_ids.size();
		// read form imagesearch.db
		Vector<Integer> oldPersion_ids = new Vector<Integer>();
		Vector<Integer> oldRawIds = new Vector<Integer>();
		String selection = SearchMetaData.BITMAP_PATH + "=?";
		String[] selectionArg = new String[1];
		selectionArg[0] = record.path;
		// find if there has the database
		Cursor cursor = mContentResolver.query(SearchMetaData.CONTENT_URI,
				null, selection, selectionArg, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int persionid = cursor.getInt(cursor
						.getColumnIndex(SearchMetaData.PERSIOD_ID));
				int id = cursor.getInt(cursor
						.getColumnIndex(SearchMetaData._ID));
				oldPersion_ids.add(persionid);
				oldRawIds.add(id);
			}
			cursor.close();
			int oldLength = oldPersion_ids.size();
			if (oldLength == 0) {
				// just add these
				for (int i = 0; i < persionLength; i++) {
					ContentValues values = new ContentValues();
					values.put(SearchMetaData.PERSIOD_ID, persion_ids.get(i));
					values.put(SearchMetaData.BITMAP_PATH, record.path);
					values.put(SearchMetaData.BITMAP_SIZE, record.size);
					values.put(SearchMetaData.IMAGE_ID, record.id);
					values.put(SearchMetaData.TYPE,
							SearchMetaData.IMAGE_TYPE_PATH);
					Log.i(TAG, "[addorupdateSearchDatabase] insert PERSIOD_ID " + persion_ids.get(i));
					mContentResolver.insert(SearchMetaData.CONTENT_URI, values);
				}
			} else {

			}

		} else {
			for (int i = 0; i < persionLength; i++) {
				ContentValues values = new ContentValues();
				values.put(SearchMetaData.PERSIOD_ID, persion_ids.get(i));
				values.put(SearchMetaData.BITMAP_PATH, record.path);
				values.put(SearchMetaData.TYPE, SearchMetaData.IMAGE_TYPE_PATH);
				values.put(SearchMetaData.BITMAP_SIZE, record.size);
				values.put(SearchMetaData.IMAGE_ID, record.id);
				mContentResolver.insert(SearchMetaData.CONTENT_URI, values);
				Log.i(TAG, "[addorupdateSearchDatabase]insert PERSIOD_ID " + persion_ids.get(i));
			}
		}

	}

	private static void getDatabaseImagePath(ArrayList<String> mPathHash) {
		String[] projection = new String[2];
		projection[0] = SearchMetaData.PERSIOD_ID;
		projection[1] = SearchMetaData.BITMAP_PATH;
		String selection = SearchMetaData.TYPE + "=?";
		String[] selectionArg = new String[1];
		selectionArg[0] = String.valueOf(SearchMetaData.IMAGE_TYPE_PATH);
		Cursor cursor = mContentResolver.query(SearchMetaData.CONTENT_URI,
				projection, selection, selectionArg, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				// int persionId=
				// cursor.getInt(cursor.getColumnIndex(SearchMetaData.PERSIOD_ID));
				String path = cursor.getString(cursor
						.getColumnIndex(SearchMetaData.BITMAP_PATH));
				if (!mPathHash.contains(path)) {
					mPathHash.add(path);// we not add same path twice
				}
			}
			cursor.close();
		}
	}
	
	private static void updateImageStore() {
		try {
			Log.i(TAG, "[updateImageStore]");
			// read bitmap from imagestore
			if (mUpdateRecords == null || mUpdateRecords.size() == 0) {
				deleteAllImagePath();
			} else {
				// when update, what we thought normally is that ,the same path,
				// the image has no changed.
				// so we just find the different one's
				// mPathHash = new HashMap<String, Integer>();
				// mPathHash.clear();
				// getDatabaseImagePath(mPathHash);
				Log.i(TAG, "[updateImageStore] mPathHash size:" + mPathHash.size());

				// contentobserver may callback much time,we just keep the first
				// one
				if (mUpdateRecords.size() == mPathHash.size()) {
					// we think the are the same!
					throw new IllegalArgumentException(
							"[updateImageStore] the imagestore is OK, no need to update!");
				}

				// mPathHash mUpdatePaths
				ArrayList<String> mUpdatePaths = ImageRecord
						.RecordToString(mUpdateRecords);
				// get old record not in new imagestores
				ArrayList<String> mOldPaths = new ArrayList<String>(mPathHash);
				ArrayList<String> mOldUpdatePaths = new ArrayList<String>(
						mUpdatePaths);
				mOldPaths.removeAll(mUpdatePaths);
				{
					for (String dbPath : mOldPaths) {
						deleteImagePath(dbPath);// delete no used image path
					}
				}

				mOldUpdatePaths.removeAll(mPathHash);
				{
					for (String path : mOldUpdatePaths) {
						ArrayList<Integer> persion_ids = addToAlbum(path);
						int index = mUpdatePaths.indexOf(path);
						Log.i(TAG, "[updateImageStore] index "+ index);
						addorupdateSearchDatabase(persion_ids,
								mUpdateRecords.get(index));
					}
				}

			}

		} catch (Exception e) {
			Log.w(TAG, "[updateImageStore] wrong " + e.getMessage());
		}
	}

	

	private static void deleteAllImagePath() {
		// TODO Auto-generated method stub
		String seletction = SearchMetaData.TYPE + "=?";
		String[] seletctionArg = new String[1];
		seletctionArg[0] = String.valueOf(SearchMetaData.IMAGE_TYPE_PATH);

		int count = mContentResolver.delete(SearchMetaData.CONTENT_URI,
				seletction, seletctionArg);
		Log.i(TAG, "[deleteAllImagePath]::" + count);
	}

	private static void deleteImagePath(String path) {
		String seletction = SearchMetaData.BITMAP_PATH + "=?";
		String[] seletctionArg = new String[1];
		seletctionArg[0] = String.valueOf(path);

		int count = mContentResolver.delete(SearchMetaData.CONTENT_URI,
				seletction, seletctionArg);
		Log.i(TAG, "[deleteImagePath] path" + path + " count " + count);
	}

	

	
	private static class ImageStoreObserver extends ContentObserver {

		public ImageStoreObserver(Handler handler) {
			super(handler);
		}

		public void onChange(boolean selfChange) {
			// read and update contact photo
			if(mUpdateListener!=null)
			{
				mUpdateListener.onChangedObserver(ImageSearchOperator.OPTION_UPDATE_TYPE_IMAGE);
			}
		}
	}
	
	public static class ImageRecord {
		public int id;
		public int size;
		public String path;

		public ImageRecord(int id, String path, int size) {
			this.id = id;
			this.size = size;
			this.path = path;
		}

		public static ArrayList<String> RecordToString(
				ArrayList<ImageRecord> records) {
			ArrayList<String> mPatharray = null;
			if (records != null && records.size() > 0) {
				mPatharray = new ArrayList<String>(records.size());
				for (ImageRecord mRecord : records) {
					mPatharray.add(mRecord.path);
				}
			}
			else
			{
				mPatharray = new ArrayList<String>();
				mPatharray.clear();
			}

			return mPatharray;
		}
	}
}
