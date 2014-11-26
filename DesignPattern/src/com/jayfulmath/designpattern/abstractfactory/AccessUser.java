package com.jayfulmath.designpattern.abstractfactory;

public class AccessUser implements IUser {

	@Override
	public void insertUser(User user) {
		System.out.println("��Access �и�user�����һ����¼");
	}

	@Override
	public User getUser(int id) {
		System.out.println("��Access �и���ID�õ�user��һ����¼");
		return null;
	}

}
