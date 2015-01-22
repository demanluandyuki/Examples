package com.htc.globalsearch.imagesearch.utils;

import android.content.Intent;

public class ImageSearchUtils {
	
	public final static String ACTION_CAMERA_PICKER = "android.media.action.IMAGE_CAPTURE";
	
	//{act=com.htc.HTCAlbum.action.ITEM_PICKER_FROM_COLLECTIONS typ=image/*
	public final static String ACTION_GALLERY_PICKER = "com.htc.HTCAlbum.action.ITEM_PICKER_FROM_COLLECTIONS";
	
	public final static int FIND_TYPE_IMAGE_STORE = 1;
	public final static int FIND_TYPE_CONTACT = 2;
	public final static int FIND_TYPE_ALL = 0xFFFF;
	
	//search activity base
	public final static int REQUEST_CAMERA_PICK = 0;
	public final static int REQUEST_GALLERY_PICK = 1;

	public final static int  MSG_ID_UPDATE_LIST = 10;
	public final static int  MSG_ID_START_FIND_PERSON = 11;
	public final static int  MSG_ID_UPDATE_SEARCH_BUTTON = 12;
	public final static int  MSG_ID_PREPARE_ITMES = 13;
	public final static int  MSG_ID_SHOW_ALERT = 14;
	public final static int  MSG_ID_DELETE_ALERT = 15;
	public final static int  MSG_ID_PROGRESS_DIALOG = 16;
	public final static int  MSG_ID_DELETE_PROGRESS_DIALOG = 17;
	public final static int  MSG_ID_UPDATE_ENGINE_STATUS = 18;

	//engine
	public static final int ENGINE_OP_DEOCDE_URI = 50;
	public static final int ENGINE_OP_PREPARE_SRC_APP = 51;
	public static final int ENGINE_START_SEARCH = 52;
	public static final int ENGINE_SEARCH_QUEUE = 53;
	
	//background engine
	public final static int OPERATOR_BASE =100;
	public final static int OPERATOR_STATUS_NONE =101;
	public final static int OPERATOR_STATUS_IDLE =102;
	public final static int OPERATOR_STATUS_REBUILDING =103;
	public final static int OPERATOR_STATUS_UPDATING =104;
	public final static int OPERATOR_STATUS_FIND_PERSON =105;
	
	
	private ImageSearchUtils(){};
	
	
	public static Intent getCameraIntent()
	{
		Intent intent = new Intent(ACTION_CAMERA_PICKER);
		intent.putExtra("request_from", "contacts");
		return intent;
	}
	
	public static Intent getGalleryIntent()
	{
		Intent intent = new Intent(ACTION_GALLERY_PICKER);
		intent.setType("image/*");
		return intent;
	}
}
