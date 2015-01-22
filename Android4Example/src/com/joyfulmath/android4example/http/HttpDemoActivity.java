package com.joyfulmath.android4example.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.joyfulmath.android4example.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HttpDemoActivity extends Activity implements IResult {

	protected static final String TAG = "httpdemo.HttpDemoActivity";
	public Handler mMainHandler;
	public static final String RequestUrl = "http://www.baidu.com";
	Button mBtnRequest;
	TextView mResponseView;
	Button mbtn_json;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_httpdemo_main);
		mBtnRequest = (Button) this.findViewById(R.id.btn_request);
		mResponseView = (TextView) this.findViewById(R.id.httpdemo_text);
		mbtn_json = (Button) this.findViewById(R.id.btn_json);
		mBtnRequest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WorkThread work = new WorkThread(HttpDemoActivity.this,
						RequestUrl);
				work.start();
			}
		});
		
		mbtn_json.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//				JSonParser parser = new JSonParser();
//				parser.parser();
				XmlParser parser = new XmlParser();
				parser.pullXml(HttpDemoActivity.this.getResources());
			}
		});
		
		
		mMainHandler = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				String str = (String) msg.obj;
				Log.i(TAG, "get " + str);
				mResponseView.setText(str);
			}

		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private class WorkThread extends Thread {

		IResult mListener;
		String address;
		String message = "";
		public WorkThread(IResult result, String address) {
			mListener = result;
			this.address = address;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			Log.i(TAG, "WorkThread start");
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet(address);
				HttpResponse response = client.execute(request);
				InputStreamReader inreader = new InputStreamReader(response
						.getEntity().getContent());
				BufferedReader reader = new BufferedReader(inreader);
				String str = reader.readLine();
				Log.i(TAG, "WorkThread start str:"+str);
				while(str!=null)
				{
					message+= str;
					str = reader.readLine();
				}
				
				if(mListener!=null)
				{
					mListener.onResult(message);
				}
				
			} catch (Exception ex) {
				Log.i(TAG, "WorkThread ex:"+ex.getMessage());
			}
		}

	}

	@Override
	public void onResult(String msg) {
		Message message = new Message();
		message.what = 1;
		message.obj = msg;
		mMainHandler.sendMessage(message);
	}
}
