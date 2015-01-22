package com.htc.globalsearch.imagesearch.client.searchitem;

import java.util.ArrayList;

import android.os.RemoteException;

import com.htc.globalsearch.imagesearch.client.GeneralSearchItem;
import com.htc.globalsearch.imagesearch.client.GenericResultItem;
import com.htc.globalsearch.imagesearch.client.SearchEngine;
import com.htc.globalsearch.imagesearch.client.SearchWorkQueue;
import com.htc.globalsearch.imagesearch.utils.ImageSearchUtils;

public class ContactImageSearchType extends GeneralSearchItem implements GeneralSearchItem.QueryListener {

	public ContactImageSearchType(SearchEngine engine,SearchWorkQueue workqueue)
	{
		super(engine,workqueue);
		this.mimeType = ImageSearchUtils.FIND_TYPE_CONTACT;
	}
	
	@Override
	public void startQuery(String key) {
		try {
			this.mEngine.findPersonInService(key, mimeType);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onQueryResult(String key, int type,
			ArrayList<GenericResultItem> resultItems) {
		//notify workthread

	}

	
}
