package com.jayfulmath.designpattern.decorate;

public class Person implements IComponent {
	
	private String name;
	
	public Person(String name)
	{
		this.name = name;
	}
	@Override
	public void show() {
		System.out.println("´©ÒÂ·þµÄ"+name);
	}

}
