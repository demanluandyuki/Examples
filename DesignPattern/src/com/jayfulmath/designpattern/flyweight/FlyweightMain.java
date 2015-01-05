package com.jayfulmath.designpattern.flyweight;

import com.jayfulmath.designpattern.main.BasicExample;

public class FlyweightMain extends BasicExample {

	@Override
	public void startDemo() {
		WebSiteFactory factory = new WebSiteFactory();
		
		WebSite fx = factory.getWebSiteCategory("��Ʒչʾ");
		fx.Use(new User("X"));
		
		WebSite fy = factory.getWebSiteCategory("��Ʒչʾ");
		fy.Use(new User("Y"));
		
		WebSite fz = factory.getWebSiteCategory("����");
		fz.Use(new User("Z"));
	}

}
