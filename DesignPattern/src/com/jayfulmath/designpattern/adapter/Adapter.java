package com.jayfulmath.designpattern.adapter;

public class Adapter implements ITarget {
	Adapee adaptee = new Adapee();
	@Override
	public void Request() {
		adaptee.SpecialRequest();
	}

}
