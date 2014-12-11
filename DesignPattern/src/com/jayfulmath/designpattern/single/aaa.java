package com.jayfulmath.designpattern.single;

public class aaa {
	private static aaa _minstance = null;
	
	protected aaa(){}
	
	public static aaa getAaa()
	{
		if(_minstance == null)
		{
			_minstance = new aaa();
		}
		return _minstance;
	}
	
}
