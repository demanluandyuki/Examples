package com.jayfulmath.designpattern.abstractfactory;

public class AccessDepartment implements IDepartment {

	@Override
	public void insertDepartment(Department department) {
		// TODO Auto-generated method stub
		System.out.println("在Access 中给Department表添加一条记录");
	}

	@Override
	public Department getDepartment(int id) {
		System.out.println("在Access 中根据ID得到Department表一条记录");
		return null;
	}

}
