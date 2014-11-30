package com.jayfulmath.designpattern.prototype;

import com.jayfulmath.designpattern.main.BasicExample;

public class PrototypeMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		Ball _mBall = new Ball("football",109.8,"play football");
		_mBall.setId(1);
		
		Ball _mBallA = (Ball) _mBall.clone();
		_mBallA.setId(2);
		
		_mBall.display();
		_mBallA.display();
	}

}
