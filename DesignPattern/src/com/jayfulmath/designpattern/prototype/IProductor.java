package com.jayfulmath.designpattern.prototype;

public interface IProductor {
	public Object clone();
	void setId(int id);
	String display();
}
