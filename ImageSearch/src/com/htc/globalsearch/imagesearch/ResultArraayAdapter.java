package com.htc.globalsearch.imagesearch;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import com.htc.globalsearch.imagesearch.LoadImageAsync.ImageCallBack;
import com.htc.globalsearch.imagesearch.R;
import com.htc.globalsearch.imagesearch.client.GenericResultItem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultArraayAdapter extends ArrayAdapter<Object> implements OnItemClickListener,OnScrollListener,ImageCallBack {

	private static final String TAG = "ImageSearch.ResultArraayAdapter";
	private ArrayList<GenericResultItem> mItems =null;
//	private int mLayoutId = 0;
	private Context mContext = null;
	private LoadImageAsync mLoadTask = null;
	private int mViewStart = 0;
	private int mViewEnd = 0;
//	private ArrayList<GenericResultItem> mLastVisableItem = null;
	private int mScrollStatue = SCROLL_STATE_IDLE;
	public ResultArraayAdapter(Context context, int resource) {
		super(context, resource);
//		mLayoutId = resource;
		mContext= context;
		mLoadTask = new LoadImageAsync();
//		Intent intent = new Intent();
//		List<String> a = new ArrayList<String>();
	}

	public void setGenericResultItem(ArrayList<GenericResultItem> itmes)
	{
		if(mItems!=null && mItems.size()>0)
		{
			for(GenericResultItem oldItem:mItems)
			{
				oldItem.Release();
			}
		}
		
		this.mItems = itmes;
		mLoadTask.Reset();
	}

	@Override
	public int getCount() {
		if(mItems!=null)
		{
			return mItems.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(mItems!=null)
		{
			return mItems.get(position);
		}
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "[getView] position:" + position);
		if (mItems != null && position <= mItems.size()) {
			ViewHolder viewHolder = null;
			GenericResultItem resultItem = mItems.get(position);
			resultItem.postion = position;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_grid_result, null);
				viewHolder.result_image = (ImageView) convertView
						.findViewById(R.id.grid_result_image);
				viewHolder.result_image.setTag(position);
				viewHolder.result_Title = (TextView) convertView
						.findViewById(R.id.grid_result_title);
				viewHolder.result_Msg = (TextView) convertView
						.findViewById(R.id.grid_result_msg);
				convertView.setTag(viewHolder);

				setViewHolder(resultItem,viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
				setViewHolder(resultItem,viewHolder);				
			}
			return convertView;
		} else {
			return super.getView(position, convertView, parent);
		}
	}

	private void setViewHolder(GenericResultItem resultItem,
			ViewHolder viewHolder) {
		// TODO Auto-generated method stub
		if (resultItem != null) {
			if (resultItem.mImageView == null) {
				resultItem.mImageView = new SoftReference<ImageView>(
						viewHolder.result_image);
			} else {
				ImageView image = resultItem.mImageView.get();
				if (image != null && image == viewHolder.result_image) {
					// doing nothing
				} else {
					resultItem.mImageView = new SoftReference<ImageView>(
							viewHolder.result_image);
				}
			}
			
			if(mScrollStatue == SCROLL_STATE_IDLE)
			{
				//in idle load image async
				mLoadTask.loadBitmapAsync(resultItem, resultItem.mImageView.get(), this);
			}
			else
			{
				// loading image when idle
				viewHolder.result_image
				.setImageResource(R.drawable.icon_result_default);
			}
			

			viewHolder.result_Title.setText(resultItem.getTitle());
			String msgStr = resultItem.getMsg();
			if (msgStr != null && !msgStr.isEmpty()) {
				viewHolder.result_Msg.setText(msgStr);
			} else {
				viewHolder.result_Msg.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterview, View convertView, int pos, long arg3) {
		Log.i(TAG, "onItemClick pos:"+pos);
		if(mItems != null&& pos <= mItems.size())
		{
			GenericResultItem resultItem = mItems.get(pos);
			resultItem.onLaunch(mContext);
		}
	}
	
	private static class ViewHolder{
		ImageView result_image;
		TextView result_Title;
		TextView result_Msg;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
//		Log.i(TAG, "onScroll firstVisibleItem:"+firstVisibleItem+"\t visibleItemCount:"+visibleItemCount+" \t totalItemCount:"+totalItemCount);
		mViewStart = firstVisibleItem;
		mViewEnd = firstVisibleItem+visibleItemCount-1;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onScrollStateChanged "+scrollState);
		mScrollStatue = scrollState;
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE)
		{
			//scroll down
			//start to loading bitmap async
			LoadBitmapAsyncAdapter(mViewStart,mViewEnd);
		}
	}

	private void LoadBitmapAsyncAdapter(int start, int end) {
		// TODO Auto-generated method stub
		int length = mItems.size();
		GenericResultItem item = null;
		for(int i=start;i<=end && i<length;i++)
		{
			item = mItems.get(i);
			mLoadTask.loadBitmapAsync(item, item.mImageView.get(), this);
		}
		
	}

	@Override
	public void imageLoaded(Bitmap bitmap, ImageView view,
			GenericResultItem item) {
		// TODO Auto-generated method stub
		int pos = item.postion;
		Log.i(TAG, "[imageLoaded]:pos "+pos+" start "+mViewStart+"\t end:"+mViewEnd);
		if(pos<=mViewEnd && pos>=mViewStart)
		{
			//need update
			view.setImageBitmap(bitmap);
		}
	}
	
	public void releaseBitmaps(GenericResultItem item)
	{
		//bitmap is stall in async load cache
		Log.i(TAG, "[releaseBitmaps] item:"+item.getTitle());
		mLoadTask.freeCacheBitmap(item);
	}
	
	public void recycleResultBitmaps(int fromPos, int toPos)
	{
		for(int i=fromPos;i<toPos;i++)
		{
			GenericResultItem item = mItems.get(i);
			releaseBitmaps(item);
		}
		
	}
}
