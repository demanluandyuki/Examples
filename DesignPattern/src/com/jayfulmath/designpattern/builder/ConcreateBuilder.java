package com.jayfulmath.designpattern.builder;

public class ConcreateBuilder implements IBuilder {

	private Productor _mProductor = new Productor();
	@Override
	public void buildA() {
		// TODO Auto-generated method stub
		System.out.println("small A");
		_mProductor.setPartA("small A");
	}

	@Override
	public void buildB() {
		// TODO Auto-generated method stub
		System.out.println("middle B");
		_mProductor.setPartB("middle B");
	}

	@Override
	public void buildC() {
		// TODO Auto-generated method stub
		System.out.println("large C");
		_mProductor.setPartC("large C");
	}

	@Override
	public Productor getResult() {
		// TODO Auto-generated method stub
		return _mProductor;
	}

}
