package com.jayfulmath.designpattern.composite;

public class FianceDepartment extends Company {

	public FianceDepartment(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Add(Company c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Remove(Company c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Display(int depth) {
		// TODO Auto-generated method stub
		printfObjectName(depth);
	}

	@Override
	public void LineofDuty() {
		// TODO Auto-generated method stub
		System.out.println(name+"\t staff payoff");
	}

}
