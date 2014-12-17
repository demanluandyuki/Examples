package com.jayfulmath.designpattern.adapter;

public class Client {
	
	ITarget target;
	
	public Client(ITarget target) {
		this.target = target;
	}

	public void doAction()
	{
		System.out.println("Client request");
		target.Request();
	}
}
