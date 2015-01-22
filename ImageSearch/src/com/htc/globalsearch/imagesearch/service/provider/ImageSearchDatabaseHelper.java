package com.htc.globalsearch.imagesearch.service.provider;


import com.htc.globalsearch.imagesearch.service.provider.ImageSearchProvider.SearchMetaData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ImageSearchDatabaseHelper extends SQLiteOpenHelper {

	public ImageSearchDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create table search
		db.execSQL("CREATE TABLE "+SearchMetaData.TABLE_NAME+"("+
				SearchMetaData._ID+" INTEGER PRIMARY KEY,"+
				SearchMetaData.PERSIOD_ID+" INTEGER,"+
				SearchMetaData.BITMAP_PATH+" TEXT,"+
				SearchMetaData.TYPE+" INTEGER,"+
				SearchMetaData.CONTACT_ID+" INTEGER,"+
				SearchMetaData.BITMAP_SIZE+" INTEGER,"+
				SearchMetaData.IMAGE_ID+" INTEGER,"+
				SearchMetaData.LAST_UPDATE_TIME_STAMP+" INTEGER"+
				")"
				);
		
		db.execSQL("CREATE TABLE "+ApplicationsMetaData.TABLE_NAME+"("+
				ApplicationsMetaData._ID+" INTEGER PRIMARY KEY,"+
				ApplicationsMetaData.NAME+" TEXT,"+
				ApplicationsMetaData.PACKAGE+" TEXT,"+
				ApplicationsMetaData.CLASS_NAME+" TEXT,"+
				ApplicationsMetaData.ICON+" TEXT,"+
				ApplicationsMetaData.NAME_PINGYING+" TEXT,"+
				ApplicationsMetaData.TIME_INSTALL+" DATETIME,"+
				ApplicationsMetaData.TIME_UPDATE+" DATETIME,"+
				ApplicationsMetaData.VER_CODE+" INTEGER,"+
				ApplicationsMetaData.VER_NAME+" TEXT"+
				")"
				);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
