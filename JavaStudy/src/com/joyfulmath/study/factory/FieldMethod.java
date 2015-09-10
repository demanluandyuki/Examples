package com.joyfulmath.study.factory;

import com.joyfulmath.study.field.Person;
import com.joyfulmath.study.field.Student;
import com.joyfulmath.study.utils.TraceLog;

public class FieldMethod implements IWorkMethod {

	@Override
	public void startWork() {
		Student st = new Student("Mark-Student");
		Person p = st;
		TraceLog.v(p.name+" "+st.name);
		TraceLog.v(p.getName()+" "+st.getName());
	}

}
