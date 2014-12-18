package com.jayfulmath.designpattern.bridge;

public abstract class Road {
	public static final int TYPE_CITY = 1;
	public static final int TYPE_HIGHWAY = 2;
	public static final int TYPE_SUBURBAN = 3;
	
	protected String TAGNAME;
	
	protected Automobile _mAuto;	
	protected double _miles;
	
	public Road(double _miles) {
		this._miles = _miles;
	}


	/**
	 * @param _mAuto the _mAuto to set
	 */
	public void set_mAuto(Automobile _mAuto) {
		this._mAuto = _mAuto;
	}

	public abstract double SpeedRate();
	public double Run()
	{
		
//		System.out.println("distance of "+TAGNAME+" is "+_miles+" km");
		System.out.println(String.format("distance of %s is %.0f km", TAGNAME,_miles));
		double speed = _mAuto.Run()*SpeedRate();
		double h = _miles/speed;
		System.out.println(String.format("%s spend time %.2f hours", TAGNAME,h));
		return h;
	}
}
