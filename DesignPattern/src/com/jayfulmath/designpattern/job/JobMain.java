package com.jayfulmath.designpattern.job;

import com.jayfulmath.designpattern.main.BasicExample;

public class JobMain extends BasicExample {

	@Override
	public void startDemo() {
		BasicManager bm = new BasicManager();
		SrManager sr = new SrManager();
		bm.setSuccesser(sr);
		
		bm.HandleRequest(2);
		bm.HandleRequest(5);
	}

}
