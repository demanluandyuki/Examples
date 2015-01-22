package com.joyfulmath.android4example.http;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.joyfulmath.android4example.R;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class XmlParser {

	private static final String TAG = "httpdemo.XmlParser";

	// pull
	/*xmlpullparser 不关心xml的结构属性，它就是开始，结束，属性，value。
	 * 
	 * */
	public void pullXml(Resources res) {
		Log.i(TAG, "[pullXml]");
		XmlResourceParser parser = res.getXml(R.layout.layout_httpdemo_main);// xmlpullparser
		int eventType;
		try {
			eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					Log.i(TAG, "[pullXml] START_DOCUMENT");
				} else if (eventType == XmlPullParser.START_TAG) {
					Log.i(TAG, "[pullXml] START_TAG:" + parser.getName());
					int count = parser.getAttributeCount();
					Log.i(TAG, "[pullXml] \t count" + count );
					for (int i = 0; i < count; i++) {
						String noteName = parser.getAttributeName(i);
						Log.i(TAG, "[pullXml] \t noteName:" + noteName + " at " + i);
						if(noteName.equals("id")||noteName.equals("text"))
						{
							String value = parser.getAttributeValue(i);
							Log.i(TAG, "[pullXml]\t	noteValue:" + value + " at " + i);
						}
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					Log.i(TAG, "[pullXml] END_TAG:" + parser.getName());
				} else if (eventType == XmlPullParser.TEXT) {
					Log.i(TAG, "[pullXml] TEXT:" + parser.getText());
				}

				eventType = parser.next();
			}

			Log.i(TAG, "[pullXml] END_DOCUMENT");
		} catch (XmlPullParserException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
