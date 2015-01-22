package com.htc.globalsearch.imagesearch.client;

import java.util.ArrayList;


public abstract class GeneralSearchItem {
	
	public int mimeType = 0;
	public SearchEngine mEngine = null;
	public SearchWorkQueue mWorkQueue = null;
	public abstract void startQuery(String key);
	public GeneralSearchItem(SearchEngine engine,SearchWorkQueue workqueue)
	{
		mEngine = engine;
		mWorkQueue = workqueue;
	}
	
	public interface QueryListener{
		public void onQueryResult(String key, int type,ArrayList<GenericResultItem> resultItems);
	}
}
