package com.htc.globalsearch.imagesearch.service.buildtype;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.htc.globalsearch.imagesearch.service.GeneralBuildType;
import com.htc.globalsearch.imagesearch.service.provider.ApplicationsMetaData;

public class ApplicationsBuildType extends GeneralBuildType {

	private static final String TAG = "ImageSearch.ApplicationsBuildType";
	public static final String PACKAGE_ADD = "android.intent.action.PACKAGE_ADDED";
	public static final String PACKAGE_REMOVE = "android.intent.action.PACKAGE_REMOVED";

	private static PackageChanegedReceiver mReceiver = null;

	public static void OnRebuild() throws NameNotFoundException {
		rebuild();
	}

	private static void rebuild() throws NameNotFoundException {
		// TODO Auto-generated method stub
		PackageManager pm = mContext.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> reinfolist = pm.queryIntentActivities(mainIntent, 0);
		// Collections.sort(reinfolist,new
		// ResolveInfo.DisplayNameComparator(pm));
		Log.i(TAG, "[rebuild] infosize:" + reinfolist.size());
		for (ResolveInfo info : reinfolist) {
			ApplicationsDbType dbtype = createAppInfos(info, pm);
			ApplicationsDbType.insertDb(dbtype);
		}

	}

	private static ApplicationsDbType createAppInfos(ResolveInfo info,
			PackageManager pm) throws NameNotFoundException {
		ApplicationsDbType dbtype = new ApplicationsDbType();
		dbtype.activityName = info.activityInfo.name; // 鑾峰緱璇ュ簲鐢ㄧ▼搴忕殑鍚姩Activity鐨刵ame
		dbtype.pkgName = info.activityInfo.packageName; // 鑾峰緱搴旂敤绋嬪簭鐨勫寘鍚�		dbtype.appLabel = (String) info.loadLabel(pm); // 鑾峰緱搴旂敤绋嬪簭鐨凩abel
		// Drawable icon = info.loadIcon(pm); // 鑾峰緱搴旂敤绋嬪簭鍥炬爣
		PackageInfo pInfo = pm.getPackageInfo(dbtype.pkgName, 0);
		dbtype.verName = pInfo.versionName;
		dbtype.verCode = pInfo.versionCode;
		dbtype.installtime = pInfo.firstInstallTime;
		dbtype.updatetime = pInfo.lastUpdateTime;
		return dbtype;
	}

	public static void removePackage(String pkgName) {
		Log.i(TAG, "[removePackage] pkgName:"+pkgName);
		String selection = ApplicationsMetaData.PACKAGE+"=?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = pkgName;
		int count = mContentResolver.delete(ApplicationsMetaData.CONTENT_URI, selection, selectionArgs);
	}

	public static void addPackage(String pkgName) {
		PackageManager pm = mContext.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mainIntent.setPackage(pkgName);
		List<ResolveInfo> reinfolist = pm.queryIntentActivities(mainIntent, 0);
		Log.i(TAG, "[rebuild] infosize:" + reinfolist.size());
		try {
			for (ResolveInfo info : reinfolist) {
				ApplicationsDbType dbtype = createAppInfos(info, pm);
				ApplicationsDbType.insertDb(dbtype);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void regeisterPackageChange() {
		if (mReceiver == null) {
			mReceiver = new PackageChanegedReceiver();
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(PACKAGE_ADD);
		filter.addAction(PACKAGE_REMOVE);
		mContext.registerReceiver(mReceiver, filter);
	}

	public static void unRegisterPackageChange() {
		mContext.unregisterReceiver(mReceiver);
		mReceiver = null;
	}

	static class PackageChanegedReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// 鎺ユ敹瀹夎骞挎挱
			if (intent.getAction().equals(PACKAGE_ADD)) {
				String packageName = intent.getDataString();
				addPackage(packageName);
			}
			// 鎺ユ敹鍗歌浇骞挎挱
			if (intent.getAction().equals(PACKAGE_REMOVE)) {
				String packageName = intent.getDataString();
				removePackage(packageName);
			}
		}

	}

	static class ApplicationsDbType {
		String activityName; // 鑾峰緱璇ュ簲鐢ㄧ▼搴忕殑鍚姩Activity鐨刵ame
		String pkgName; // 鑾峰緱搴旂敤绋嬪簭鐨勫寘鍚�		
		String appLabel; // 鑾峰緱搴旂敤绋嬪簭鐨凩abel
		// Drawable icon = info.loadIcon(pm); // 鑾峰緱搴旂敤绋嬪簭鍥炬爣
		String verName;
		int verCode;
		long installtime;
		long updatetime;

		public static Uri insertDb(ApplicationsDbType dbParam) {
			ContentValues values = new ContentValues();
			values.put(ApplicationsMetaData.NAME, dbParam.appLabel);
			values.put(ApplicationsMetaData.PACKAGE, dbParam.pkgName);
			values.put(ApplicationsMetaData.CLASS_NAME, dbParam.activityName);
			values.put(ApplicationsMetaData.VER_CODE, dbParam.verCode);
			values.put(ApplicationsMetaData.VER_NAME, dbParam.verName);
			values.put(ApplicationsMetaData.TIME_INSTALL, dbParam.installtime);
			values.put(ApplicationsMetaData.TIME_UPDATE, dbParam.updatetime);

			Uri insertUri = mContentResolver.insert(
					ApplicationsMetaData.CONTENT_URI, values);
			return insertUri;
		}
	}

}
