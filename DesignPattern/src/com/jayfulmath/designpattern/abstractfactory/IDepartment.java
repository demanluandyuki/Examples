package com.jayfulmath.designpattern.abstractfactory;

//封装对Department表的操作
public interface IDepartment {
	void insertDepartment(Department department);
	Department getDepartment(int id);
}
