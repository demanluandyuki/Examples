package com.jayfulmath.designpattern.single;

public class singleClass {
	private static singleClass _minstance = null;
	
	private static Object objlock =new Object();
	
	protected singleClass(){}
	
	public static singleClass getInstance()
	{
		if(_minstance == null)
		{
			synchronized (objlock) {
				if(_minstance == null)
				{
					_minstance = new singleClass();
				}
			}			
		}
		return _minstance;
	}
	
	public int add(int a,int b)
	{
		return a+b;
	}
	
}
