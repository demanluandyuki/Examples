package com.jayfulmath.designpattern.abstractfactory;

public class AccessDepartment implements IDepartment {

	@Override
	public void insertDepartment(Department department) {
		// TODO Auto-generated method stub
		System.out.println("��Access �и�Department�����һ����¼");
	}

	@Override
	public Department getDepartment(int id) {
		System.out.println("��Access �и���ID�õ�Department��һ����¼");
		return null;
	}

}
