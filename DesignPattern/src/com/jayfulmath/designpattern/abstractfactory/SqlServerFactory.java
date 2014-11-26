package com.jayfulmath.designpattern.abstractfactory;

public class SqlServerFactory implements IFactory {

	@Override
	public IUser CreateUser() {
		// TODO Auto-generated method stub
		return new SqlserverUser();
	}

	@Override
	public IDepartment CreateDepartment() {
		// TODO Auto-generated method stub
		return new SqlserverDepartment();
	}

}
