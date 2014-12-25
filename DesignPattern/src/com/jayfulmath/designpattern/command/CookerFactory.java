package com.jayfulmath.designpattern.command;

import com.jayfulmath.designpattern.command.cooker.FruitCooker;
import com.jayfulmath.designpattern.command.cooker.MeatCooker;
import com.jayfulmath.designpattern.command.cooker.StandardCooker;
import com.jayfulmath.designpattern.command.cooker.VegetableCooker;

public class CookerFactory {
	
	public static StandardCooker CreateCooker(String type)
	{
		StandardCooker _mCooker = null;
		switch(type)
		{
		case "meat":
			_mCooker = MeatCooker.getInstance("MeatCooker");
			break;
		case "vegetable":
			_mCooker = VegetableCooker.getInstance("VegetableCooker");
			break;
		case "fruit":
			_mCooker = FruitCooker.getInstance("FruitCooker");
			break;	
		}
		
		
		return _mCooker;
	}
}
