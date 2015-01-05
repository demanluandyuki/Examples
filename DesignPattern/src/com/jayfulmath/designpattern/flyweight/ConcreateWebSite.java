package com.jayfulmath.designpattern.flyweight;

public class ConcreateWebSite extends WebSite {

	String name;
	
	
	public ConcreateWebSite(String name) {
		super();
		this.name = name;
	}


	@Override
	public void Use(User user) {
		System.out.println("WebSite :"+name+"\t User:"+user.name);
	}

}
