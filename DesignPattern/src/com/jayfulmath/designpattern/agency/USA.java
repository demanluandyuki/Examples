package com.jayfulmath.designpattern.agency;

public class USA extends Country {

	public USA(UnitedNation mediator) {
		super(mediator);
		
	}
	
	public void Declare(String message)
	{
		this.unitedNation.Declare(message, this);
	}
	
	public void GetMessage(String message)
	{
		System.out.println("USA get message:"+message);
	}
}
