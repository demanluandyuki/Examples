package com.jayfulmath.designpattern.command.cooker;

import com.jayfulmath.designpattern.command.GlobalValue.MENUTYPE;


public class MeatCooker extends StandardCooker {

	private static MeatCooker _mIntance = null;

	private MeatCooker(String name)
	{
		super(name);
		type = MENUTYPE.MEAT_TYPE;
	}
	
	
	public static MeatCooker getInstance(String name)
	{
		if(_mIntance == null)
		{
			_mIntance = new MeatCooker(name);
		}
		return _mIntance;
	}


	@Override
	public void cookfood() {
		try {
			Thread.sleep(25*1000);
			System.out.println(name+" Cooker Meat OK");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
