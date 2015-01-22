package com.htc.globalsearch.imagesearch.service.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class ApplicationsMetaData implements BaseColumns {

	public final static String TABLE_NAME = "applications";
	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ ImageSearchProvider.AUTHORITY + "/applications");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.imagesearch.applications";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.imagesearch.applications";
	public static final String DEFAULT_SORT_ORDER = "modified DESC";

	public static final String NAME = "name";
	public static final String PACKAGE = "package";
	public static final String CLASS_NAME = "class";
	public static final String ICON = "icon";
	public static final String NAME_PINGYING = "name_pinying";
	public static final String TIME_INSTALL = "time_install";
	public static final String TIME_UPDATE = "time_update";
	public static final String VER_CODE = "vercode";
	public static final String VER_NAME = "vername";

	public static int delete(SQLiteDatabase db, int op, String id,
			String selection, String[] selectionArgs) {
		int count = 0;
		switch (op) {
		case ImageSearchProvider.APPLICATIONS_COLLECTION_URI_INDICATER:
			count = db.delete(TABLE_NAME, selection, selectionArgs);
			break;
		case ImageSearchProvider.APPLICATIONS_SINGAL_URI_INDICATER:
			count = db.delete(TABLE_NAME, ApplicationsMetaData._ID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? "AND(" + selection + ')'
							: ""), selectionArgs);
			break;
		}
		return count;
	}

	public static Uri insert(SQLiteDatabase db, Uri uri, ContentValues values) {
		ContentValues inValue;

		if (values != null) {
			inValue = new ContentValues(values);
		} else {
			inValue = new ContentValues();
		}

		if (inValue.containsKey(PACKAGE) == false) {

			throw new IllegalArgumentException(
					"fail to insert row because package name need " + uri);
		}

		if (inValue.containsKey(CLASS_NAME) == false) {

			throw new IllegalArgumentException(
					"fail to insert row because class name need " + uri);
		}

		long rowId = db.insert(TABLE_NAME, PACKAGE, inValue);

		if (rowId > 0) {
			Uri inSertAppUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			return inSertAppUri;
		}

		throw new IllegalArgumentException("insert row failed " + uri);
	}

	public static Cursor query(SQLiteDatabase db, int op, String id,
			String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		Cursor dbInnerCursor = null;
		switch (op) {
		case ImageSearchProvider.APPLICATIONS_COLLECTION_URI_INDICATER:
			dbInnerCursor = db.query(TABLE_NAME, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case ImageSearchProvider.APPLICATIONS_SINGAL_URI_INDICATER:
			dbInnerCursor = db.query(TABLE_NAME, projection, _ID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? "AND(" + selection + ')'
							: ""), selectionArgs, null, null, sortOrder);
			break;
		}
		return dbInnerCursor;
	}

	public static int update(SQLiteDatabase db, int match, String id,
			ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
		switch (match) {
		case ImageSearchProvider.APPLICATIONS_COLLECTION_URI_INDICATER:
			count = db.update(TABLE_NAME, values,selection,
					selectionArgs);
			break;
		case ImageSearchProvider.APPLICATIONS_SINGAL_URI_INDICATER:
			count = db.update(TABLE_NAME, values,_ID
							+ "="
							+ id
							+ (!TextUtils.isEmpty(selection) ? "AND("
									+ selection + ')' : ""), selectionArgs);
			break;
		}

		return count;
	}
}
