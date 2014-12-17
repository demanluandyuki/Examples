package com.jayfulmath.designpattern.composite;

public abstract class Company {
	
	protected String name;

	public Company(String name) {
		this.name = name;
	}
	
	public abstract void Add(Company c);
	public abstract void Remove(Company c);
	public abstract void Display(int depth);
	public abstract void LineofDuty();
	
	protected void printfObjectName(int depth) {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<depth;i++)
		{
			builder.append("-");
		}
		
		System.out.println(builder.toString()+name);
	}
}
