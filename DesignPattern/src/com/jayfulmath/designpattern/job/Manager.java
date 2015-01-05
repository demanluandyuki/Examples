package com.jayfulmath.designpattern.job;

public abstract class Manager implements IManager {

	IManager successer;
	@Override
	public void setSuccesser(IManager next) {
		successer = next;
	}

}
