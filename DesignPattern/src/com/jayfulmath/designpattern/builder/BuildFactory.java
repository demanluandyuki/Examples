package com.jayfulmath.designpattern.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class BuildFactory {
	
	private static final String AssemblyName = "com.jayfulmath.designpattern.builder";
	private static final String realClass = "ConcreateBuilder";
	
	public static IBuilder operator()
	{
		IBuilder result = null;
		String className =AssemblyName+"."+realClass;
		try {
			Class<?> c = Class.forName(className);
			Constructor<?> ct = c.getConstructor();
			result = (IBuilder) (ct.newInstance());
		} catch (NoSuchMethodException |ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
