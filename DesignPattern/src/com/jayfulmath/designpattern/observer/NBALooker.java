package com.jayfulmath.designpattern.observer;

public class NBALooker extends Observer {

	public NBALooker(String name, ISubject sub) {
		super(name, sub);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Update() {
		System.out.println(sub.getSubjectStatus()+"\t"+name+"\tstop NBA Look");
	}

}
