package com.jayfulmath.designpattern.abstractfactory;

//封装对User表的操作
public interface IUser {
	void insertUser(User user);
	User getUser(int id);
}
