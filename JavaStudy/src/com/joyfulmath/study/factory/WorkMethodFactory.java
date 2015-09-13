package com.joyfulmath.study.factory;


public class WorkMethodFactory {
	
	public static IWorkMethod createMethod(String methodStr)
	{
		IWorkMethod method = null;
		switch (methodStr) {
		case "FieldMethod":
			method = new FieldMethod();
			break;
		case "InterviewMethod":
			method = new InterviewMethod();
			break;
		default:
			break;
		}
		
		return method;
	}
}
