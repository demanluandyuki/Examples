package com.jayfulmath.designpattern.flyweight;

import com.jayfulmath.designpattern.main.BasicExample;

public class FlyweightMain extends BasicExample {

	@Override
	public void startDemo() {
		WebSiteFactory factory = new WebSiteFactory();
		
		WebSite fx = factory.getWebSiteCategory("产品展示");
		fx.Use(new User("X"));
		
		WebSite fy = factory.getWebSiteCategory("产品展示");
		fy.Use(new User("Y"));
		
		WebSite fz = factory.getWebSiteCategory("博客");
		fz.Use(new User("Z"));
	}

}
