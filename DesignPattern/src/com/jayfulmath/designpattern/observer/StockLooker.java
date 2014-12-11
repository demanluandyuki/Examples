package com.jayfulmath.designpattern.observer;

public class StockLooker extends Observer {

	public StockLooker(String name, ISubject sub) {
		super(name, sub);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		System.out.println(sub.getSubjectStatus()+"\t"+name+"\tstop Stock Look");
	}

}
