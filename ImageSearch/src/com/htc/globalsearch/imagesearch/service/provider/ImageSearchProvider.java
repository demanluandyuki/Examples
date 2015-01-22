package com.htc.globalsearch.imagesearch.service.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class ImageSearchProvider extends ContentProvider {

	public final static String TAG = "ImageSearch.Provider";
	public final static String DBASE_NAME = "imagesearch.db";
	public final static int DBASE_VERSION = 1;
	public final static String AUTHORITY = "com.htc.globalsearch.imagesearch";
	public final static UriMatcher URI_MATCHER;
	public static ImageSearchDatabaseHelper mHelper = null;
	public static final int SEARCH_COLLECTION_URI_INDICATER = 1;
	public static final int SEARCH_SINGAL_URI_INDICATER = 2;
	public static final int APPLICATIONS_COLLECTION_URI_INDICATER = 3;
	public static final int APPLICATIONS_SINGAL_URI_INDICATER = 4;
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTHORITY, SearchMetaData.TABLE_NAME,
				SEARCH_COLLECTION_URI_INDICATER);
		URI_MATCHER.addURI(AUTHORITY, SearchMetaData.TABLE_NAME + "/#",
				SEARCH_SINGAL_URI_INDICATER);
		URI_MATCHER.addURI(AUTHORITY, ApplicationsMetaData.TABLE_NAME,
				APPLICATIONS_COLLECTION_URI_INDICATER);
		URI_MATCHER.addURI(AUTHORITY, ApplicationsMetaData.TABLE_NAME + "/#",
				APPLICATIONS_SINGAL_URI_INDICATER);
	}

	public static class SearchMetaData implements BaseColumns {
		private SearchMetaData() {
		};

		public final static String TABLE_NAME = "image_search";
		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ ImageSearchProvider.AUTHORITY + "/image_search");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.imagesearch.image_search";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.imagesearch.image_search";
		public static final String DEFAULT_SORT_ORDER = "modified DESC";

		public static final int IMAGE_TYPE_PATH = 1;
		public static final int IMAGE_TYPE_CONTACT = 2;

		public static final String PERSIOD_ID = "persion_id";
		public static final String BITMAP_PATH = "path";
		public static final String TYPE = "type";
		public static final String CONTACT_ID = "contact_id";
		public static final String BITMAP_SIZE = "bitmap_size";
		public static final String IMAGE_ID = "image_id";
		public static final String LAST_UPDATE_TIME_STAMP = "last_update_time_stamp";
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		SQLiteDatabase db = mHelper.getWritableDatabase();
		int op = URI_MATCHER.match(uri);
		String idapp = "";
		switch (op) {
		case SEARCH_COLLECTION_URI_INDICATER:
			count = db.delete(SearchMetaData.TABLE_NAME, selection,
					selectionArgs);
			break;
		case SEARCH_SINGAL_URI_INDICATER:
			String id = uri.getPathSegments().get(1);
			count = db.delete(SearchMetaData.TABLE_NAME, SearchMetaData._ID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? "AND(" + selection + ')'
							: ""), selectionArgs);
			break;
		case APPLICATIONS_SINGAL_URI_INDICATER:
			idapp = uri.getPathSegments().get(1);
		case APPLICATIONS_COLLECTION_URI_INDICATER:
			ApplicationsMetaData
					.delete(db, op, idapp, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown Uri " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case SEARCH_COLLECTION_URI_INDICATER:
			return SearchMetaData.CONTENT_TYPE;
		case SEARCH_SINGAL_URI_INDICATER:
			return SearchMetaData.CONTENT_ITEM_TYPE;
		case APPLICATIONS_COLLECTION_URI_INDICATER:
			return ApplicationsMetaData.CONTENT_TYPE;
		case APPLICATIONS_SINGAL_URI_INDICATER:
			return ApplicationsMetaData.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown Uri " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mHelper.getWritableDatabase();

		int op = URI_MATCHER.match(uri);
		if (op == APPLICATIONS_COLLECTION_URI_INDICATER) {
			Uri inSertAppUri = ApplicationsMetaData.insert(db, uri, values);
			if (inSertAppUri != null) {
				getContext().getContentResolver().notifyChange(uri, null);
				return inSertAppUri;
			}
		} else if (op != SEARCH_COLLECTION_URI_INDICATER) {
			throw new IllegalArgumentException("Unknown Uri " + uri);
		}

		ContentValues inValue;

		if (values != null) {
			inValue = new ContentValues(values);
		} else {
			inValue = new ContentValues();
		}

		if (inValue.containsKey(SearchMetaData.PERSIOD_ID) == false) {

			throw new IllegalArgumentException(
					"fail to insert row because persion_id is need " + uri);
		}

		boolean containsBitmapPath = inValue
				.containsKey(SearchMetaData.BITMAP_PATH);
		boolean containsContactId = inValue
				.containsKey(SearchMetaData.CONTACT_ID);
		if ((containsBitmapPath == false) && (containsContactId == false)) {
			throw new IllegalArgumentException(
					"fail to insert row because Path or ContactId must need one "
							+ uri);
		} else if (containsBitmapPath && containsContactId) {
			throw new IllegalArgumentException(
					"fail to insert row because Path and ContactId need only one "
							+ uri);
		} else if (containsBitmapPath) {
			if (inValue.containsKey(SearchMetaData.TYPE) == false) {
				inValue.put(SearchMetaData.TYPE, SearchMetaData.IMAGE_TYPE_PATH);
			}

			if (inValue.containsKey(SearchMetaData.IMAGE_ID) == false) {
				throw new IllegalArgumentException(
						"fail to insert row because bitmap need image_id" + uri);
			}
		} else if (containsContactId) {
			if (inValue.containsKey(SearchMetaData.TYPE) == false) {
				inValue.put(SearchMetaData.TYPE,
						SearchMetaData.IMAGE_TYPE_CONTACT);
			}

			if (inValue.containsKey(SearchMetaData.LAST_UPDATE_TIME_STAMP) == false) {
				throw new IllegalArgumentException(
						"fail to insert row because contact need last update time stamp"
								+ uri);
			}
		}

		long rowId = db.insert(SearchMetaData.TABLE_NAME,
				SearchMetaData.PERSIOD_ID, inValue);

		if (rowId > 0) {
			Uri inSertAppUri = ContentUris.withAppendedId(
					SearchMetaData.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
			return inSertAppUri;
		}

		throw new IllegalArgumentException("insert row failed " + uri);
	}

	@Override
	public boolean onCreate() {
		Log.i(TAG, "[onCreate]");
		mHelper = new ImageSearchDatabaseHelper(getContext(), DBASE_NAME, null,
				DBASE_VERSION);
		Intent intent = new Intent(
				ImageSearchReceiver.BUILD_SEARCH_IMAGE_ACTION);
		this.getContext().sendBroadcast(intent);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor dbInnerCursor = null;
		String id = "";
		int op = URI_MATCHER.match(uri);
		switch (op) {
		case SEARCH_COLLECTION_URI_INDICATER:
			dbInnerCursor = db.query(SearchMetaData.TABLE_NAME, projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		case SEARCH_SINGAL_URI_INDICATER:
			// String id = String.valueOf(ContentUris.parseId(uri));
			id = uri.getPathSegments().get(1);
			dbInnerCursor = db.query(
					SearchMetaData.TABLE_NAME,
					projection,
					SearchMetaData._ID
							+ "="
							+ id
							+ (!TextUtils.isEmpty(selection) ? "AND("
									+ selection + ')' : ""), selectionArgs,
					null, null, sortOrder);
			break;
		case APPLICATIONS_SINGAL_URI_INDICATER:
			id = uri.getPathSegments().get(1);
		case APPLICATIONS_COLLECTION_URI_INDICATER:
			dbInnerCursor = ApplicationsMetaData.query(db,op,id,projection,selection,selectionArgs,sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Unknown Uri " + uri);
		}

		return dbInnerCursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String id = "";
		int match = URI_MATCHER.match(uri);
		Log.i(TAG, "match " + match);
		switch (match) {
		case SEARCH_COLLECTION_URI_INDICATER:
			count = db.update(SearchMetaData.TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case SEARCH_SINGAL_URI_INDICATER:
			id = uri.getPathSegments().get(1);
			count = db.update(
					SearchMetaData.TABLE_NAME,
					values,
					SearchMetaData._ID
							+ "="
							+ id
							+ (!TextUtils.isEmpty(selection) ? "AND("
									+ selection + ')' : ""), selectionArgs);
			break;
		case APPLICATIONS_SINGAL_URI_INDICATER:
			id = uri.getPathSegments().get(1);
		case APPLICATIONS_COLLECTION_URI_INDICATER:
			count = ApplicationsMetaData.update(db,match,id,values,selection,selectionArgs);
			break;			
		default:
			throw new IllegalArgumentException("Unknown Uri " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
