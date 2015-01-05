package com.jayfulmath.designpattern.job;

public class BasicManager extends Manager {

	@Override
	public void HandleRequest(int day) {
		if(day<3)
		{
			System.out.println("BasicManager handler request:"+day);
		}
		else
		{
			successer.HandleRequest(day);
		}
	}

}
