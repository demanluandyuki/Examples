package com.jayfulmath.designpattern.flyweight;

import java.util.Hashtable;

public class WebSiteFactory {
	private Hashtable<String, WebSite> mTable = new Hashtable<>();
	
	public WebSite getWebSiteCategory(String key)
	{
		if(!mTable.contains(key))
		{
			mTable.put(key, new ConcreateWebSite(key));
		}
		
		return mTable.get(key);
	}
	
	public int getWebsiteCount()
	{
		return mTable.size();
	}
}
