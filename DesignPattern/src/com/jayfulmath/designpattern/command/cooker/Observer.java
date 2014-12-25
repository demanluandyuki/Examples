package com.jayfulmath.designpattern.command.cooker;

import java.util.List;

import com.jayfulmath.designpattern.command.GlobalValue.MENUTYPE;

public abstract class Observer {
	
	protected String name;
	protected List<ISubject> sub;	
	
	public Observer(String name, List<ISubject> sub) {
		this.name = name;
		this.sub = sub;
	}
	public abstract void Update(MENUTYPE type,int customerid);
}
