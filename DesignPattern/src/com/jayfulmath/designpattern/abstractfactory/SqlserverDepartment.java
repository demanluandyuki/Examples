package com.jayfulmath.designpattern.abstractfactory;

public class SqlserverDepartment implements IDepartment {

	@Override
	public void insertDepartment(Department department) {
		System.out.println("��SQLserver �и�Department�����һ����¼");
	}

	@Override
	public Department getDepartment(int id) {
		System.out.println("��SQLserver �и���ID�õ�Department��һ����¼");
		return null;
	}

}
