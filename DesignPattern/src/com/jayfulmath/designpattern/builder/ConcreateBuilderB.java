package com.jayfulmath.designpattern.builder;

public class ConcreateBuilderB implements IBuilder {

	private Productor _mProductor = new Productor();
	@Override
	public void buildA() {
		// TODO Auto-generated method stub
		System.out.println("middle A");
		_mProductor.setPartA("middle A");
	}

	@Override
	public void buildB() {
		// TODO Auto-generated method stub
		System.out.println("small B");
		_mProductor.setPartB("small B");
	}

	@Override
	public void buildC() {
		// TODO Auto-generated method stub
		System.out.println("small C");
		_mProductor.setPartC("small C");
	}

	@Override
	public Productor getResult() {
		return _mProductor;
	}

}
