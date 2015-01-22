package com.joyfulmath.android4example.http;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class JSonParser {
	private static final String JSON =
			"{" +
			" \"phone\" : [\"12345678\", \"87654321\"]," +
			" \"name\" : \"yuanzhifei89\"," +
			" \"age\" : 100," +
			" \"address\" : { \"country\" : \"china\", \"province\" : \"jiangsu\" }," +
			" \"married\" : false" +
			"}";
	private static final String TAG = "httpdemo.JSonParser"; 
	
	public void parser()
	{
		Log.i(TAG, "[parser]");
		try{
			JSONTokener jsonparser = new JSONTokener(JSON);
			JSONObject person = (JSONObject) jsonparser.nextValue();
			JSONArray pbones = person.getJSONArray("phone");
			Log.i(TAG, "[parser]pbones:"+pbones.getString(0)+" "+pbones.getString(1));
			
			String name = person.getString("name");
			Log.i(TAG, "[parser]name:"+name);

		}catch(Exception ex)
		{
			Log.i(TAG, "[parser]ex:"+ex.getMessage());
		}
		
	}
}
