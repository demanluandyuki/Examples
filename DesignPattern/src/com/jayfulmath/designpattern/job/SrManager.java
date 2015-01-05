package com.jayfulmath.designpattern.job;

public class SrManager extends Manager {

	@Override
	public void HandleRequest(int day) {
		System.out.println("SrManager handler request:"+day);
	}

}
