package com.jayfulmath.designpattern.agency;

public abstract class Country {
	UnitedNation unitedNation;
	
	public Country(UnitedNation mediator)
	{
		this.unitedNation = mediator;
		
	}
}
