package com.jayfulmath.designpattern.bridge;

public class Suburban extends Road {

	public Suburban(double _miles) {
		super(_miles);
		this.TAGNAME = "Suburban";
	}

	@Override
	public double SpeedRate() {
		// TODO Auto-generated method stub
		System.out.println("In "+TAGNAME);
		return 1.0;
	}
}
