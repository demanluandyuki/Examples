package com.jayfulmath.designpattern.bridge;

public class CityRoad extends Road {

	public CityRoad(double _miles) {
		super(_miles);
		this.TAGNAME = "CityRoad";
	}

	@Override
	public double SpeedRate() {
		// TODO Auto-generated method stub
		System.out.println("In "+TAGNAME);
		return 0.75;
	}
}
