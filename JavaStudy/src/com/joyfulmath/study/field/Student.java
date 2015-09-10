package com.joyfulmath.study.field;

public class Student extends Person {
	
	public String name;
	
	public Student(String name)
	{
		this.name = name;
		super.name = "Man-Person";
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
