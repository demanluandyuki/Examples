package com.htc.globalsearch.imagesearch;

import java.util.ArrayList;

import com.htc.globalsearch.imagesearch.AlertListAdapter.ListAdapterInfo;
import com.htc.globalsearch.imagesearch.client.GenericResultItem;
import com.htc.globalsearch.imagesearch.client.SearchEngine;
import com.htc.globalsearch.imagesearch.client.onEngineListener;
import com.htc.globalsearch.imagesearch.utils.ImageSearchUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class ImageSearchActivity extends Activity implements OnClickListener,onEngineListener {

	private final static  String TAG = "ImageSearch.Activity";
	private ImageButton searchButton;
	private SearchEngine mSearchEngine;
	private TextView	searchText;
	private ResultArraayAdapter mResultAdapter =null;
	private GridView mResultList =null;
	private SearchActivityHandle mSearchHandle = null;
	private ArrayList<GenericResultItem> mSearchResultItems =null;
	private Bitmap mDecodeBitmap = null;
	private String mDecodePath = null;
	private ArrayList<ListAdapterInfo> mInfoItems =null;
	private AlertDialog mAlert = null;
	private ProgressDialog mProDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        searchButton = ( ImageButton)findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
        searchText = (TextView) this.findViewById(R.id.search_text);
        mResultList = (GridView) findViewById(R.id.grid_result);
        mResultAdapter = new ResultArraayAdapter(this,R.layout.layout_grid_result);
        mResultList.setAdapter(mResultAdapter);
        mResultList.setOnItemClickListener(mResultAdapter);
        mResultList.setOnScrollListener(mResultAdapter);
        mResultAdapter.setGenericResultItem(null);
        mSearchEngine = new SearchEngine(ImageSearchActivity.this,this);
        mSearchHandle = new SearchActivityHandle();
        mAlert = new AlertDialog.Builder(this).create();
        mProDialog = new ProgressDialog(this);
        mProDialog.setCanceledOnTouchOutside(false);
		Message msg = mSearchHandle.obtainMessage();
		msg.what = ImageSearchUtils.MSG_ID_PREPARE_ITMES;
		mSearchHandle.sendMessageDelayed(msg, 10);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.search_button:
			ShowSelectImageAlert();
			break;
		}
	
	}
	



	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (mSearchEngine != null) {
			mSearchEngine.Release();
		}
		mSearchHandle = null;
		super.onDestroy();
	}


	@Override
	public void onSearchResult(String path, ArrayList<GenericResultItem> itmes) {
		Log.d(TAG, "onSearchResult start "+path);
		dismissAlertDialog(0);
		if(itmes!=null && itmes.size()>0)
		{

		}
		else
		{
			dismissProgressDialog(0);
			Message msg = mSearchHandle.obtainMessage();
			msg.what = ImageSearchUtils.MSG_ID_SHOW_ALERT;
			msg.arg2 = R.string.search_result_null;
			mSearchHandle.sendMessage(msg);
			dismissAlertDialog(3000);	
		}
		mSearchResultItems = itmes;
		mResultAdapter.setGenericResultItem(mSearchResultItems);
		Message msg = mSearchHandle.obtainMessage();
		msg.what = ImageSearchUtils.MSG_ID_UPDATE_LIST;
		mSearchHandle.sendMessage(msg);
	}


	@Override
	public void onEngineStatusChanged(int status) {
		int stautsid = 0;
		switch(status)
		{
		case ImageSearchUtils.OPERATOR_STATUS_IDLE:
			stautsid = R.string.background_engine_idle;
			break;
		case ImageSearchUtils.OPERATOR_STATUS_REBUILDING:
			stautsid = R.string.background_engine_rebuild;
			break;
		case ImageSearchUtils.OPERATOR_STATUS_UPDATING:
			stautsid = R.string.background_engine_update;
			break;
		case ImageSearchUtils.OPERATOR_STATUS_FIND_PERSON:
			stautsid = R.string.background_engine_find;
			break;
		}
		Message msg = mSearchHandle.obtainMessage();
		msg.what = ImageSearchUtils.MSG_ID_UPDATE_ENGINE_STATUS;
		msg.arg1 = stautsid;
		mSearchHandle.sendMessage(msg);
		Log.d(TAG, "[onEngineStatusChanged] status:"+this.getResources().getString(stautsid));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null || resultCode == 0) {
			Log.d(TAG, "[onActivityResult] resultCode " + resultCode);
		} else {
			switch (requestCode) {
			case ImageSearchUtils.REQUEST_CAMERA_PICK:
				Log.d(TAG, "[onActivityResult] Camera:" + data.getDataString());
				Uri uri = data.getData();
				if (uri != null) {
					decodeUriToBitmap(uri);
				}
				break;
			case ImageSearchUtils.REQUEST_GALLERY_PICK:
				Log.d(TAG, "[onActivityResult] Gallery:" + data.getDataString());
				Uri uriGallery = data.getData();
				if (uriGallery != null) {
					decodeUriToBitmap(uriGallery);
				}
				break;
			}
		}

	}
	
	@Override
	public void onEngineDecodeUri(Bitmap bitmap, String path) {
		try {
			Log.d(TAG, "onEngineDecodeUri path "+path);
			int size = this.getResources().getDimensionPixelSize(
					R.dimen.head_item_image_size);
			mDecodeBitmap = ThumbnailUtils.extractThumbnail(bitmap, size, size);
			mDecodePath = path;
			Message msg = mSearchHandle.obtainMessage();
			msg.what = ImageSearchUtils.MSG_ID_UPDATE_SEARCH_BUTTON;
			mSearchHandle.sendMessage(msg);
		} catch (Exception e) {
		}
	}
	
	@Override
	public void onPrepareActivityInfo(ArrayList<ListAdapterInfo> infos) {
		Log.d(TAG, "[onPrepareActivityInfo] names "+infos.size());
		mInfoItems = infos;
	}
	
	private void ShowSelectImageAlert() {

		if (mInfoItems == null || mInfoItems.size() == 0) {
			Log.d(TAG, "ShowSelectImageAlert info not prepared");
		} else if (mInfoItems.size() == 1) {
			ListAdapterInfo info = mInfoItems.get(0);
			startSelectImage(info.type);
		} else if (mInfoItems.size() == 2) {
			Log.d(TAG, "ShowSelectImageAlert show dialog");
			Builder build = new AlertDialog.Builder(this);
			build.setTitle(R.string.alert_select_title);
			build.setIcon(R.drawable.icon_indicator_camera_l);
			build.setAdapter(new AlertListAdapter(this, mInfoItems),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 0) {
								StartCamera();
							} else {
								StartGallery();
							}
						}
					}).show();
		}

	}
	
	private void startSelectImage(int type) {
		mDecodeBitmap = null;
		mDecodePath = null;
		if(type == ImageSearchUtils.REQUEST_GALLERY_PICK)
		{
			StartGallery();
		}
		else
		{
			StartCamera();
		}
	}
	
	private void StartCamera()
	{
		Log.d(TAG, "[StartCamera]");
		Intent intent =ImageSearchUtils.getCameraIntent();
		this.startActivityForResult(intent, ImageSearchUtils.REQUEST_CAMERA_PICK);
	}
	
	private void StartGallery()
	{
		Log.d(TAG, "[StartGallery]");
		Intent intent =ImageSearchUtils.getGalleryIntent();
		this.startActivityForResult(intent, ImageSearchUtils.REQUEST_GALLERY_PICK);
	}
	

	private void  decodeUriToBitmap(Uri uri)
	{
		mSearchEngine.decodeUriToBitmapHandle(uri);
	}
	
	private void BindEngineSearchImage(String path)
	{
		try{
		mSearchResultItems = null;
		mSearchEngine.SearchImageAsyncHandle(path, 
				ImageSearchUtils.FIND_TYPE_ALL);
		}catch (Exception e) {
			Log.d(TAG, "wrong in search image:"+e.getMessage());
		}
	}
	
	private void showingAlertDialog(int infoResId)
	{
		mAlert.setTitle(infoResId);
		mAlert.show();
	}
	
	private void dismissAlertDialog(int millsecons)
	{
		Log.d(TAG, "dismissAlertDialog millsecons "+millsecons);
		if(millsecons == 0)
		{
			mAlert.dismiss();
		}
		else
		{
			Message msg = mSearchHandle.obtainMessage();
			msg.what = ImageSearchUtils.MSG_ID_DELETE_ALERT;
			mSearchHandle.sendMessageDelayed(msg, millsecons);
		}
	}
	
	private void showingPrograssDialog(int infoResId)
	{
		String message = this.getResources().getString(infoResId);
		Log.d(TAG, "showingPrograssDialog "+message);
		
		mProDialog.setMessage(message);
		mProDialog.show();
	}
	
	private void dismissProgressDialog(int millsecons)
	{
		Log.d(TAG, "dismissProgressDialog millsecons"+millsecons);
		if(millsecons == 0)
		{
			mProDialog.dismiss();
		}
		else
		{
			Message msg = mSearchHandle.obtainMessage();
			msg.what = ImageSearchUtils.MSG_ID_DELETE_PROGRESS_DIALOG;
			mSearchHandle.sendMessageDelayed(msg, millsecons);
		}
	}
	
	class SearchActivityHandle extends Handler{
		
		public SearchActivityHandle(){
			super();
		}
		
		public SearchActivityHandle(Looper loop)
		{
			super(loop);
		}
		
		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "SearchActivityHandle id "+msg.what);
			switch(msg.what)
			{
			case ImageSearchUtils.MSG_ID_UPDATE_LIST:
				mResultList.invalidateViews();
				dismissProgressDialog(1000);
				break;
			case ImageSearchUtils.MSG_ID_START_FIND_PERSON:
				if(!mDecodePath.isEmpty()){
					showingPrograssDialog(R.string.search_progress_loading);
					BindEngineSearchImage(mDecodePath);
				}
				break;
			case ImageSearchUtils.MSG_ID_UPDATE_SEARCH_BUTTON:
				if(mDecodeBitmap!=null)
				{
					searchButton.setImageBitmap(mDecodeBitmap);
					searchButton.invalidate();
				}
				
				if(!mDecodePath.isEmpty()){
					showingPrograssDialog(R.string.search_progress_loading);
					BindEngineSearchImage(mDecodePath);
				}				
				break;
			case ImageSearchUtils.MSG_ID_PREPARE_ITMES:
				mSearchEngine.prepareSrcImageItemsHandle();
				break;
			case ImageSearchUtils.MSG_ID_SHOW_ALERT:
				showingAlertDialog(msg.arg2);
				break;
			case ImageSearchUtils.MSG_ID_DELETE_ALERT:
				dismissAlertDialog(0);
				break;
			case ImageSearchUtils.MSG_ID_PROGRESS_DIALOG:
				showingPrograssDialog(msg.arg2);
				break;
			case ImageSearchUtils.MSG_ID_DELETE_PROGRESS_DIALOG:
				dismissProgressDialog(0);
				break;
			case ImageSearchUtils.MSG_ID_UPDATE_ENGINE_STATUS:
				int resId = msg.arg1;
				searchText.setText(resId);
				searchText.invalidate();
				break;
			}
		};
		
		
	}




}
