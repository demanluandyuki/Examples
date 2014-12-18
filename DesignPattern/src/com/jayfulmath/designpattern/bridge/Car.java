package com.jayfulmath.designpattern.bridge;

public class Car extends Automobile {
	
	private static Car _mInstance = null;
	
	private Car() {
		super();
	}
	
	public static Car getInstance()
	{
		if(_mInstance == null)
		{
			_mInstance = new Car();
		}
		return _mInstance;
	}
	
	@Override
	public int Run() {
		// TODO Auto-generated method stub
		System.out.print("Car Run 50km/h at:");
		return 50;
	}

}
