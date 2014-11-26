package com.jayfulmath.designpattern.abstractfactory;

public class AccessUser implements IUser {

	@Override
	public void insertUser(User user) {
		System.out.println("在Access 中给user表添加一条记录");
	}

	@Override
	public User getUser(int id) {
		System.out.println("在Access 中根据ID得到user表一条记录");
		return null;
	}

}
