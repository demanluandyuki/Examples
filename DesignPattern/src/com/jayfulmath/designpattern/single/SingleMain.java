package com.jayfulmath.designpattern.single;

import com.jayfulmath.designpattern.main.BasicExample;

public class SingleMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		singleClass msingle = singleClass.getInstance();
		int c = msingle.add(1, 2);
	}

}
