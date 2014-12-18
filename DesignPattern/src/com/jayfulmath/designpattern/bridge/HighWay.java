package com.jayfulmath.designpattern.bridge;

public class HighWay extends Road {

	public HighWay(double _miles) {
		super(_miles);
		this.TAGNAME = "HighWay";
	}

	@Override
	public double SpeedRate() {
		// TODO Auto-generated method stub
		System.out.println("In "+TAGNAME);
		return 1.5;
	}
}
