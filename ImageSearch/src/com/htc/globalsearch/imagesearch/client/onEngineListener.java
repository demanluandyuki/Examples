package com.htc.globalsearch.imagesearch.client;

import java.util.ArrayList;

import com.htc.globalsearch.imagesearch.AlertListAdapter.ListAdapterInfo;

import android.graphics.Bitmap;

public interface onEngineListener {
	public class ErrorResult{
		private ErrorResult(){};
		public final static int ERROR_OK = 0;
		public final static int ERROR_SERVICE_REBUILDING = 1;
		public final static int ERROR_SERVICE_UPDATING = 2;
		public final static int ERROR_SERVICE_NONE = 3;
		
	}
	
	public void onEngineStatusChanged(int status);
	public void onSearchResult(String path,ArrayList<GenericResultItem> itmes);
	public void onEngineDecodeUri(Bitmap bitmap,String path);
	public void onPrepareActivityInfo(ArrayList<ListAdapterInfo> infos);
}
