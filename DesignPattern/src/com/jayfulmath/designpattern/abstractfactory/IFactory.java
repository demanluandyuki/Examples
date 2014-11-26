package com.jayfulmath.designpattern.abstractfactory;

public interface IFactory {
	IUser CreateUser();
	IDepartment CreateDepartment();
}
