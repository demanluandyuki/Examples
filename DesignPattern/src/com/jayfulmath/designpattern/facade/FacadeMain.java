package com.jayfulmath.designpattern.facade;

import com.jayfulmath.designpattern.main.BasicExample;

/* 外观模式经常用到，比如我们经常会把对硬件的操作（camera，flashlight）等等
 * 的操作都会封装在engine类里面，已确保UI层，只需要和engine类通信，而不需要知道
 * 具体硬件的操作。
 * 
 * */
public class FacadeMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		Engine mEngine = new Engine();
		mEngine.openFlashlight();
		mEngine.closeFlashlight();
		mEngine.openVideo();
		mEngine.closeVideo();
	}

}
