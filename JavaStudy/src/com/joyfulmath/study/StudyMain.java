package com.joyfulmath.study;

import com.joyfulmath.study.factory.IWorkMethod;
import com.joyfulmath.study.factory.WorkMethodFactory;

public class StudyMain {

	public static void main(String[] args) {
		IWorkMethod method = WorkMethodFactory.createMethod("FieldMethod");
		method.startWork();
	}

}
