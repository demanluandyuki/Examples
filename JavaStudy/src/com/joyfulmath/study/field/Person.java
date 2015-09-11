package com.joyfulmath.study.field;

import com.joyfulmath.study.utils.TraceLog;

public class Person {
	public String name;

	public Person()
	{
		TraceLog.v("Person construct");
	}
	
	public String getName() {
		return name;
	}
	
}
