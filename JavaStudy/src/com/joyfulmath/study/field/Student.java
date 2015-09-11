package com.joyfulmath.study.field;

import com.joyfulmath.study.utils.TraceLog;

public class Student extends Person {
	
	public String name;
	public int level;
	public Student(String name)
	{
		this.name = name;
		super.name = "Man-Person";
		TraceLog.v("Student construct");
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
