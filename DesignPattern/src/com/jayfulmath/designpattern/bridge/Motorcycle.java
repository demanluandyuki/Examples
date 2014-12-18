package com.jayfulmath.designpattern.bridge;

public class Motorcycle extends Automobile {

	
	private static Motorcycle _mInstance = null;
	
	private Motorcycle() {
		super();
	}
	
	public static Motorcycle getInstance()
	{
		if(_mInstance == null)
		{
			_mInstance = new Motorcycle();
		}
		return _mInstance;
	}
	
	@Override
	public int Run() {
		// TODO Auto-generated method stub
		System.out.print("Motorcycle Run 30km/h at:");
		return 30;
	}

}
