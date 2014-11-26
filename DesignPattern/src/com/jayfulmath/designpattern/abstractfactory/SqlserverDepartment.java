package com.jayfulmath.designpattern.abstractfactory;

public class SqlserverDepartment implements IDepartment {

	@Override
	public void insertDepartment(Department department) {
		System.out.println("在SQLserver 中给Department表添加一条记录");
	}

	@Override
	public Department getDepartment(int id) {
		System.out.println("在SQLserver 中根据ID得到Department表一条记录");
		return null;
	}

}
