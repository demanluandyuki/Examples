package com.jayfulmath.designpattern.command.cooker;

import com.jayfulmath.designpattern.command.GlobalValue.MENUTYPE;


public class FruitCooker extends StandardCooker{
	
	private static FruitCooker _mIntance = null;	
	private FruitCooker(String name)
	{
		super(name);
		type = MENUTYPE.FRUIT_TYPE;
	}
	
	public static FruitCooker getInstance(String name)
	{
		if(_mIntance == null)
		{
			_mIntance = new FruitCooker(name);
		}
		return _mIntance;
	}

	@Override
	public void cookfood() {
		try {
			Thread.sleep(5*1000);
			System.out.println(name+" Cooker Fruit OK");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
