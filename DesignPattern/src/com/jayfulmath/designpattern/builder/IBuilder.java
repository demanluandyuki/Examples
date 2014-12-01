package com.jayfulmath.designpattern.builder;

public interface IBuilder {
	void buildA();
	void buildB();
	void buildC();
	
	Productor getResult();
}
