package com.htc.globalsearch.imagesearch;

import java.util.ArrayList;

import com.htc.globalsearch.imagesearch.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlertListAdapter extends BaseAdapter {

	private ArrayList<ListAdapterInfo>  mItem = null;
	
	private Context mContext = null;
	
	public AlertListAdapter(Context context,ArrayList<ListAdapterInfo> items)
	{
		mContext = context;
		mItem = items;
	}
	
	@Override
	public int getCount() {
		return mItem.size();
	}

	@Override
	public Object getItem(int paramInt) {
		return mItem.get(paramInt);
	}

	@Override
	public long getItemId(int paramInt) {
		return paramInt;
	}

	@Override
	public View getView(int pos, View contentView, ViewGroup ViewGroup) {
		if(pos<=mItem.size())
		{
			ImageView image = null;
			TextView text  = null;
			if(contentView == null)
			{
				contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_image_src_select, null);
			}
			image = (ImageView) contentView.findViewById(R.id.image_src_select_item);
			text = (TextView) contentView.findViewById(R.id.text_src_select_item);
			
			if(image!=null)
			{
				image.setImageDrawable(mItem.get(pos).icon);
			}
			if(text!=null)
			{
				text.setText(mItem.get(pos).lable);
			}
			
			return contentView;
		}
		
		return null;
	}
	
	public static class ListAdapterInfo{
		public String lable=null;
		public Drawable icon = null;
		public int type = -1;
		
		public ListAdapterInfo(String text,Drawable image,int type)
		{
			this.lable = text;
			this.icon = image;
			this.type = type;
		}
	}
	
}
