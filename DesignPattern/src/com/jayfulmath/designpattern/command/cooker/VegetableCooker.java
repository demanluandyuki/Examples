package com.jayfulmath.designpattern.command.cooker;

import com.jayfulmath.designpattern.command.GlobalValue.MENUTYPE;

public class VegetableCooker extends StandardCooker {

	private static VegetableCooker _mIntance = null;
	public VegetableCooker(String name)
	{
		super(name);
		type = MENUTYPE.VEGETABLE_TYPE;
	}
	
	public static VegetableCooker getInstance(String name)
	{
		if(_mIntance == null)
		{
			_mIntance = new VegetableCooker(name);
		}
		return _mIntance;
	}
	
	@Override
	public void cookfood() {
		try {
			Thread.sleep(10*1000);
			System.out.println(name+" Cooker Vegetable OK");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
