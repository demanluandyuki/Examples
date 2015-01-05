package com.jayfulmath.designpattern.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer  implements Iterator<String> {

	private List<String> _mList = new ArrayList<String>();	
	private int _mIndex = -1;
	
	public void Add(String name)
	{
		_mList.add(name);		
	}
	
	public void currentItem()
	{
		if(_mIndex>=0 && _mIndex<_mList.size())
		{
			
		}
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
