package com.jayfulmath.designpattern.proxy;

public abstract class BasicCamera {
	
	private String name = null;
	
	public BasicCamera(String name)
	{
		this.name = name;
	}
	
	public boolean open()
	{
		System.out.println(name+" camera"+" open");
		return true;		
	}
	
	public void close()
	{
		System.out.println(name+" camera"+" close");
	}
	
	
}
