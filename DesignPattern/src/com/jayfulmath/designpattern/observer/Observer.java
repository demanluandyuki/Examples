package com.jayfulmath.designpattern.observer;

public abstract class Observer {
	
	protected String name;
	protected ISubject sub;	
	
	public Observer(String name, ISubject sub) {
		this.name = name;
		this.sub = sub;
	}
	public abstract void Update();
}
