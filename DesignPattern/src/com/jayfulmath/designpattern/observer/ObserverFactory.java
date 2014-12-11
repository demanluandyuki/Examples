package com.jayfulmath.designpattern.observer;

public class ObserverFactory {
	
	public static Observer Factory(String classname,String name,ISubject sub)
	{
		Observer observer = null;
		switch(classname)
		{
		case "NBALooker":
			observer = new NBALooker(name, sub);
			break;
		case "StockLooker":
			observer = new StockLooker(name, sub);
			break;	
		}
		
		return observer;		
	}
}
