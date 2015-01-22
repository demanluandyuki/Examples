package com.htc.globalsearch.imagesearch.service;

import java.util.ArrayList;

import com.htc.globalsearch.imagesearch.service.GeneralBuildType.onImageDbUpdate;
import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;
import com.htc.globalsearch.imagesearch.service.buildtype.ApplicationsBuildType;
import com.htc.globalsearch.imagesearch.service.buildtype.ContactImageBuildType;
import com.htc.globalsearch.imagesearch.service.buildtype.SdcardImageBuildType;
import com.htc.globalsearch.imagesearch.service.provider.ImageSearchProvider.SearchMetaData;
import com.htc.globalsearch.imagesearch.utils.ImageSearchUtils;
import com.qualcomm.snapdragon.sdk.face.FacialProcessingConstants;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

/*we describe this class for operation with all db and face recognized  
 * this class can attach with service and search engine
 * any method calling from other class need to call xxxHandle method to sync with handle
 * */
public class ImageSearchOperator implements Runnable,onImageDbUpdate {

	private final static String TAG = "ImageSearch.Operator";
	private final static int OPTION_IDLE = 1;
	private final static int OPTION_REBUID = 2;
	private final static int OPTION_UPDATE = 3;
	private final static int OPTION_FIND_PERSON = 4;
	private final static int OPTION_FIND_PERSON_ID = 5;

	public final static int OPTION_UPDATE_TYPE_CONTACT = 0x10;
	public final static int OPTION_UPDATE_TYPE_IMAGE = 0x11;
	
	public int mOperatorStatus = ImageSearchUtils.OPERATOR_STATUS_NONE;
	private Handler mOperatorHandler = null;
	private Looper mOperatorLooper = null;
	private Context mContext = null;
	private ContentResolver mContentResolver = null;
	private onServiceEngineListener mBuildServiceListener = null;

	public ImageSearchOperator(Context context,onServiceEngineListener listener)
	{
		mContext = context;
		mContentResolver = context.getContentResolver();
		mBuildServiceListener = listener;
	}
	
	public void startOperatorThread() {
		Log.i(TAG, "[startOperatorThread]");
		GeneralBuildType.setContext(mContext);
		registerContentObserver();
		registerReSearchReceiver();
		new Thread(this).start();
	}

	public void exitOperatorThread() {
		notifyServiceStatusChanged(ImageSearchUtils.OPERATOR_STATUS_NONE);
		if (mOperatorLooper != null) {
			mOperatorLooper.quit();
		}
		mOperatorLooper = null;
		mOperatorHandler = null;
		mBuildServiceListener = null;
		SdcardImageBuildType.unRegisterContentObserver();
		ContactImageBuildType.unRegisterContentObserver();
		ApplicationsBuildType.unRegisterPackageChange();
		mContentResolver = null;
		mContext = null;
		GeneralBuildType.setContext(null);
		FaceRecognizeEngine.Release();// facerecognize engine can only release
		Log.i(TAG, "[exitOperatorThread]");
	}
	
	
	
	@Override
	public void onChangedObserver(int op) {
		Log.i(TAG, "onChangedObserver op:"+op);
		updateImageSearchDbHandle(op);		
	}

	public void rebuildImageSearchDbHandle() {
		Message msg = mOperatorHandler.obtainMessage();
		msg.what = OPTION_REBUID;
		mOperatorHandler.sendMessage(msg);
	}

	public void updateImageSearchDbHandle(int op) {
		Message msg = mOperatorHandler.obtainMessage();
		msg.what = OPTION_UPDATE;
		msg.arg2 = op;
		mOperatorHandler.sendMessage(msg);
	}

	public void findPersonHandle(String srcPath, int imageFilter,long sessionId) {
		Log.i(TAG, "findPersonHandle "+srcPath);
		Message msg = mOperatorHandler.obtainMessage();
		msg.what = OPTION_FIND_PERSON;
		Bundle data = new Bundle();
		data.putString("path", srcPath);
		data.putInt("filter", imageFilter);
		data.putLong("sessionid", sessionId);
		msg.setData(data);
		mOperatorHandler.sendMessage(msg);
	}

	public void findPersonHandle(int personId, int imageFilter,long sessionId) {
		Message msg = mOperatorHandler.obtainMessage();
		msg.what = OPTION_FIND_PERSON_ID;
		Bundle data = new Bundle();
		data.putInt("personid", personId);
		data.putInt("filter", imageFilter);
		data.putLong("sessionid", sessionId);
		msg.setData(data);
		mOperatorHandler.sendMessage(msg);
	}
	
	public void abortFindPersonHandle()
	{
		if(mOperatorHandler!=null && mOperatorHandler.hasMessages(OPTION_FIND_PERSON))
		{
			Log.i(TAG, "abortFindPersonHandle");
			mOperatorHandler.removeMessages(OPTION_FIND_PERSON);
		}
	}
	
	@Override
	public void run() {
		Looper.prepare();
		notifyServiceStatusChanged(ImageSearchUtils.OPERATOR_STATUS_IDLE);
		mOperatorLooper = Looper.myLooper();
		mOperatorHandler = new OperatorHandler(mOperatorLooper);
		Log.i(TAG, "background engine handle running TID:"+Process.myTid());
		Looper.loop();
	}

	public class OperatorHandler extends Handler {
		public OperatorHandler() {
			super();
		}

		public OperatorHandler(Looper mLoop) {
			super(mLoop);
		}

		@Override
		public void handleMessage(Message msg) {
			Log.i(TAG, "[handleMessage] action " + msg.what);
			switch (msg.what) {
			case OPTION_REBUID:
				rebuildImageSearchDb();
				rebuildResourceSearchDb();
				break;
			case OPTION_UPDATE:
				int op = msg.arg2;
				updateImageSearchDb(op);
				break;
			case OPTION_FIND_PERSON:
				Bundle data = msg.getData();
				int imageFilter = data.getInt("filter");
				String srcPath = data.getString("path");
				long sessionid = data.getLong("sessionid");
				findPersion(srcPath, imageFilter,
						FacialProcessingConstants.FP_PERSON_NOT_REGISTERED,sessionid);
				break;
			case OPTION_FIND_PERSON_ID: 
				Bundle data2 = msg.getData();
				int imageFilter2 = data2.getInt("filter");
				int personid = data2.getInt("personid");
				long sessionid2 = data2.getLong("sessionid");
				findPersion(null, imageFilter2, personid,sessionid2);
				break;
			default:
				break;
			}
		}

	}


	public void registerContentObserver() {
		
		SdcardImageBuildType.registerContentObserver(this);
		ContactImageBuildType.registerContentObserver(this);
	}
	
	public void registerReSearchReceiver()
	{
		ApplicationsBuildType.regeisterPackageChange();
	}
	
	private void rebuildResourceSearchDb()
	{
		try{
			ApplicationsBuildType.OnRebuild();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void rebuildImageSearchDb() {
		try {
			rebuildImageSearchDbTaskRunning();
		} catch (Exception e) {
			Log.i(TAG, "[rebuildImageSearchDb] error:" + e.getMessage());
		} finally {
			notifyServiceStatusChanged(ImageSearchUtils.OPERATOR_STATUS_IDLE);
		}
	}

	private void updateImageSearchDb(int op) {
		try {
			updateImageSearchDbTaskRunning(op);
		} catch (Exception e) {
			Log.i(TAG, "[updateImageSearchDb] error:" + e.getMessage());
		} finally {
			notifyServiceStatusChanged(ImageSearchUtils.OPERATOR_STATUS_IDLE);
		}
	}

	private void findPersion(String path, int imageFilter, int personId,long sessionid) {
		try {
			findPersonRunning(path, imageFilter, personId,sessionid);
		} catch (Exception e) {
			Log.i(TAG, "[findPersion] error:" + e.getMessage());
		} finally {
			notifyServiceStatusChanged(ImageSearchUtils.OPERATOR_STATUS_IDLE);
		}
	}

	private void rebuildImageSearchDbAsync() {
		SdcardImageBuildType.onRebuild();
		ContactImageBuildType.onRebuild();
	}

	public void rebuildImageSearchDbEnd() {
		Log.i(TAG, "[rebuildImageSearchDbEnd]***@@@");
		registerContentObserver();
	}

	public void updateImageSearchDbAsync(int option) {
		Log.i(TAG, "[updateImageSearchDbAsync]option " + option);
		switch (option) {
		case OPTION_UPDATE_TYPE_CONTACT:
			ContactImageBuildType.onUpdate();
			break;
		case OPTION_UPDATE_TYPE_IMAGE:
			SdcardImageBuildType.onUpdate();
			break;
		default:
			throw new IllegalArgumentException("[updateImageSearchDbAsync]unknown option " + option);
		}
	}

	

	public void updateImageSearchDbEnd() {
		Log.i(TAG, "[updateImageSearchDbEnd]***@@@");
		ContactImageBuildType.onUpdateEnd();
		SdcardImageBuildType.onUpdateEnd();
	}

	public void rebuildImageSearchDbTaskRunning() {
		Log.i(TAG, "[rebuildImageSearchDbTaskRunning] Start***@@@");
		// check if db created before
		if (checkDbbuildBefore() > 0) {
			// notifyServiceStatusChanged(ImageSearchService.STATUS_IDLE);
			Log.i(TAG, "[rebuildImageSearchDbTaskRunning] has been build before***@@@");
			return;
		}
		notifyServiceStatusChanged(ImageSearchUtils.OPERATOR_STATUS_REBUILDING);
		rebuildImageSearchDbAsync();
		rebuildImageSearchDbEnd();
	}

	private int checkDbbuildBefore() {
		int count = 0;
		String[] projection = new String[1];
		projection[0] = SearchMetaData._ID;
		Cursor cursor = mContentResolver.query(SearchMetaData.CONTENT_URI,
				projection, null, null, null);
		if (cursor != null) {
			count = cursor.getCount();
			cursor.close();
		}

		return count;
	}

	private void notifyServiceStatusChanged(int status) {
		mOperatorStatus = status;
		if (mBuildServiceListener != null) {
			mBuildServiceListener.onEngineStatusUpdate(status);
		}
	}

	public void updateImageSearchDbTaskRunning(int option) {
		Log.i(TAG, "updateImageSearchDbTaskRunning Start*********");
		// check if need update
		if (option == OPTION_UPDATE_TYPE_IMAGE) {
			SdcardImageBuildType.onUpdateStart();
		}

		notifyServiceStatusChanged(ImageSearchUtils.OPERATOR_STATUS_UPDATING);
		updateImageSearchDbAsync(option);
		updateImageSearchDbEnd();
	}

	private void findPersonRunning(String srcPath, int imageFilter,
			int personId,long sessionid) {
		Log.i(TAG, "FindPersonRunning srcPath "+srcPath);

		notifyServiceStatusChanged(ImageSearchUtils.OPERATOR_STATUS_FIND_PERSON);
		ArrayList<PersonImageItem> mResultItems = searchImageSearchDbAsync(srcPath,
				imageFilter, personId);
		if (mBuildServiceListener != null) {
			mBuildServiceListener.onQueryResult(sessionid,srcPath, imageFilter,mResultItems);
		}
	}

	private int findBitmapPersonIdAsync(String path) {
		int persionId = FacialProcessingConstants.FP_PERSON_NOT_REGISTERED;
		try {
			// read from db first
			int count = 0;
			if (mContentResolver != null) {
				String selection = SearchMetaData.BITMAP_PATH + "=?";
				String[] selectionArgs = new String[1];
				selectionArgs[0] = path;
				Cursor cursor = mContentResolver.query(
						SearchMetaData.CONTENT_URI, null, selection,
						selectionArgs, null);
				if (cursor != null) {
					count = cursor.getCount();
					while (cursor.moveToNext()) {
						persionId = cursor.getInt(cursor
								.getColumnIndex(SearchMetaData.PERSIOD_ID));
					}
					cursor.close();
				}

			}
			if (count == 0) {
				// not in imagesearch.db
				Bitmap bmp = BitmapFactory.decodeFile(path);
				persionId = FaceRecognizeEngine.FindPerson(bmp, mContext);
			} else if (count == 1) {
				// great, find the right one
				Log.i(TAG, "findBitmapPersonIdAsync persionId in db:" + persionId);
			} else if (count >= 2) {
				// too many people
				throw new IllegalArgumentException(
						"too much face in current bitmap:" + count);
			}

		} catch (Exception e) {
			persionId = FacialProcessingConstants.FP_PERSON_NOT_REGISTERED;
			Log.i(TAG, "[FindBitmapAsync] exception " + e.getMessage());
		}

		return persionId;
	}

	private ArrayList<PersonImageItem> searchImageSearchDbAsync(String Path,
			int imageFilter, int PersonID) {
		ArrayList<PersonImageItem> mResultItem = null;
		// FaceRecognizeEngine.loadAlbum(mContext);
		try {
			int persionId = -1;
			if (PersonID >= 0 && Path.isEmpty()) {
				persionId = PersonID;
			} else {
				// reading from imagesearch.db first. if failed. recognize then
				persionId = findBitmapPersonIdAsync(Path);

			}
			Log.i(TAG, "[searchImageAsync] persionid " + persionId);
			if (persionId < 0) {
				throw new IllegalArgumentException(
						"we can't find persion in album");
			}
			int imageType = -1;
			if (imageFilter == ImageSearchUtils.FIND_TYPE_IMAGE_STORE) {
				imageType = SearchMetaData.IMAGE_TYPE_PATH;
			} else if (imageFilter == ImageSearchUtils.FIND_TYPE_CONTACT) {
				imageType = SearchMetaData.IMAGE_TYPE_CONTACT;
			}
			// we shall find same persionId in db.
			String selection = SearchMetaData.PERSIOD_ID + "=" + persionId
					+ " AND(" + SearchMetaData.TYPE + "=" + imageType + ')';
			Cursor cursor = mContentResolver.query(SearchMetaData.CONTENT_URI,
					null, selection, null, null);
			if (cursor != null) {
				int count = cursor.getCount();
				Log.i(TAG, "[searchImageAsync]find persion with count " + count);
				if (count > 0) {
					mResultItem = new ArrayList<PersonImageItem>(count);
					int mContactId = -1;
					String path = "";
					int bitmapSize = -1;
					int imageId = -1;
					while (cursor.moveToNext()) {
						imageType = cursor.getInt(cursor
								.getColumnIndex(SearchMetaData.TYPE));
						if ((imageType == SearchMetaData.IMAGE_TYPE_CONTACT)) {
							mContactId = cursor.getInt(cursor
									.getColumnIndex(SearchMetaData.CONTACT_ID));
							PersonImageItem mContactResultItem = new PersonImageItem(
									persionId, mContactId, ImageSearchUtils.FIND_TYPE_CONTACT);
							Log.i(TAG, "[searchImageSearchDbAsync] mContactId:"+mContactId);
							mResultItem.add(mContactResultItem);
						} else if ((imageType == SearchMetaData.IMAGE_TYPE_PATH)) {
							path = cursor
									.getString(cursor
											.getColumnIndex(SearchMetaData.BITMAP_PATH));
							bitmapSize = cursor
									.getInt(cursor
											.getColumnIndex(SearchMetaData.BITMAP_SIZE));
							imageId = cursor.getInt(cursor
									.getColumnIndex(SearchMetaData.IMAGE_ID));
							PersonImageItem mSdResultitem = new PersonImageItem(
									persionId,-1, path,imageId,bitmapSize,ImageSearchUtils.FIND_TYPE_IMAGE_STORE);
							Log.i(TAG, "[searchImageSearchDbAsync] resultPath:"+path);
							mResultItem.add(mSdResultitem);
						} else {
							Log.w(TAG, "the type no support:imageFilter"
									+ imageFilter + " imageType " + imageType);
						}

					}
				}
				cursor.close();
			}

		} catch (Exception e) {
			mResultItem = null;
			Log.w(TAG, "searchImageAsync wrong " + e.getMessage());
		}
		if(mResultItem == null)
		{
			mResultItem = new ArrayList<PersonImageItem>();
			mResultItem.clear();
		}
		
		return mResultItem;
	}
}
