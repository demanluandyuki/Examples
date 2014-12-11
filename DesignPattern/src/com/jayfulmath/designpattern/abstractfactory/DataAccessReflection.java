package com.jayfulmath.designpattern.abstractfactory;

import java.lang.reflect.*;
public class DataAccessReflection {

	private static final String AssemblyName = "com.jayfulmath.designpattern.abstractfactory";
	private static final String db = "Access";
	
	public static IUser CreateUser()
	{
		String className =AssemblyName+"."+db+"User";
		IUser  result = null;
		try {
			Class<?> c = Class.forName(className);
			Constructor<?> ct = c.getConstructor();
//			ct.getinstance();
			result = (IUser) (ct.newInstance());
		} catch (NoSuchMethodException |ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static IDepartment CreateDepartment()
	{
		String className = AssemblyName+"."+db+"Department";
		IDepartment  result = null;
		try {
			Class<?> c = Class.forName(className);
			Constructor<?> ct = c.getConstructor();
			result = (IDepartment) (ct.newInstance());
		} catch (NoSuchMethodException |ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
