package com.jayfulmath.designpattern.agency;

public class UNSC extends UnitedNation {

	public USA usa;
	public Iraq iraq;
	
	
	@Override
	public void Declare(String message, Country college) {
		if(college == usa)
		{
			iraq.GetMessage(message);
		}
		else if(college == iraq)
		{
			usa.GetMessage(message);			
		}
	}

}
