package com.htc.globalsearch.imagesearch.service;

import java.util.ArrayList;

import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;

public interface onServiceEngineListener {
	void onEngineStatusUpdate(int status);

	void onQueryResult(long sessionid,String srcPath, int imageFilter,
			ArrayList<PersonImageItem> mResultItems); 
}
