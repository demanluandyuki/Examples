package com.htc.globalsearch.imagesearch.client;

import java.util.ArrayList;

import android.util.Log;

import com.htc.globalsearch.imagesearch.client.searchitem.ContactImageSearchType;
import com.htc.globalsearch.imagesearch.client.searchitem.SdImageSearchType;
import com.htc.globalsearch.imagesearch.utils.ImageSearchUtils;

/*for it may call imagesearchoperator.find, it will running in imagesearchoperator's handle thread
 *so it need to listen the queryresult and do next query.
 * */
public class SearchWorkQueue {
	private static final String TAG = "ImageSearch.SearchWorkQueue";
	public String name = null;
	public String key = "";
	public ArrayList<GeneralSearchItem> mSearchTypes = null;
	public ArrayList<GenericResultItem> mResultItems = null;
	public ArrayList<Integer> mSearchFilters = null;
	public SearchEngine mEngine = null;
	public SearchWorkQueue(String tag,SearchEngine searchEngine)
	{
		name = tag;
		mEngine = searchEngine;
		mResultItems = new ArrayList<GenericResultItem>(100);
		mSearchTypes = new ArrayList<GeneralSearchItem>(2);
		mSearchFilters = new ArrayList<Integer>(2);
	}
	
	private ArrayList<GeneralSearchItem> GenerateSearchItems(int filter)
	{
		Log.i(TAG, "GenerateSearchItems filter:"+filter);
		mSearchTypes.clear();
		mSearchFilters.clear();
		if((filter & ImageSearchUtils.FIND_TYPE_IMAGE_STORE)>0)
		{
			SdImageSearchType searchType = new SdImageSearchType(mEngine,this);
			mSearchTypes.add(searchType);
			mSearchFilters.add(ImageSearchUtils.FIND_TYPE_IMAGE_STORE);
		}
		
		if((filter & ImageSearchUtils.FIND_TYPE_CONTACT)>0)
		{
			ContactImageSearchType searchType = new ContactImageSearchType(mEngine,this);
			mSearchTypes.add(searchType);
			mSearchFilters.add(ImageSearchUtils.FIND_TYPE_CONTACT);
		}
		
		return mSearchTypes;		
	}
	
	public void query()
	{
		Log.i(TAG, "query mSearchTypes:"+mSearchTypes.size());
		if(mSearchTypes.size()>0)
		{
//			for(GeneralSearchItem searchItem:mSearchTypes)
//			{
//				searchItem.startQuery(key);
//			}
			GeneralSearchItem searchitem = mSearchTypes.get(0);
			searchitem.startQuery(key);
		}
	}
	
	public void prepareWorkQueue(int filter,String key)
	{
		this.key = key;
		GenerateSearchItems(filter);
		mResultItems.clear();
	}
	
	public void notifyWorkQueue(ArrayList<GenericResultItem> items,int filter)
	{
		Log.i(TAG, "notifyWorkQueue filter:"+filter);
		synchronized (mResultItems) {
			
			if(items!=null)
			{
				mResultItems.addAll(items);
			}
			if(!mSearchFilters.contains(filter))
			{
				Log.w(TAG, "[notifyWorkQueue] the type is not the one we need!:filter "+filter);
				for(int imagefilter:mSearchFilters)
				{
					Log.i(TAG, "imagefilter:"+imagefilter);
				}
			}
			Object object = filter;
			mSearchFilters.remove(object);
			mSearchTypes.remove(0);//remove first one
			if(mSearchFilters.size() == 0)
			{
				// add search has down! notify ui
				mEngine.onSearchResult(key,mResultItems);
			}
			else
			{
				//doing another query by handle
				mEngine.searhImageQueueHandle();
			}
		}
	}
	
}
