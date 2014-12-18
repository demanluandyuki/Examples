package com.jayfulmath.designpattern.bridge;

public class AutomobileFactory {
	
	public static Automobile CreateAutoMobile(int carType)
	{
		Automobile _mAuto = null;
		switch(carType)
		{
		case Automobile.TYPE_CAR:
			_mAuto = Car.getInstance();
			break;
		case Automobile.TYPE_MOTORCYCLE:
			_mAuto = Motorcycle.getInstance();
			break;	
		}
		return _mAuto;
	}
}
