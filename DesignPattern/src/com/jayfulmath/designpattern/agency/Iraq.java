package com.jayfulmath.designpattern.agency;

public class Iraq extends Country {

	public Iraq(UnitedNation mediator) {
		super(mediator);
		
	}

	public void Declare(String message)
	{
		this.unitedNation.Declare(message, this);
	}
	
	public void GetMessage(String message)
	{
		System.out.println("Iraq get message:"+message);
	}
}
