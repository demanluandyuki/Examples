package com.jayfulmath.designpattern.abstractfactory;

public class SqlserverUser implements IUser {

	@Override
	public void insertUser(User user) {
		System.out.println("��SQLserver �и�user�����һ����¼");
	}

	@Override
	public User getUser(int id) {
		System.out.println("��SQLserver �и���ID�õ�user��һ����¼");
		return null;
	}

}
