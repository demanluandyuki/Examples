package com.jayfulmath.designpattern.abstractfactory;

public class AccessFactory implements IFactory {

	@Override
	public IUser CreateUser() {
		// TODO Auto-generated method stub
		return new AccessUser();
	}

	@Override
	public IDepartment CreateDepartment() {
		// TODO Auto-generated method stub
		return new AccessDepartment();
	}

}
