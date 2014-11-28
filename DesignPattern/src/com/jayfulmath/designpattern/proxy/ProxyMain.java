package com.jayfulmath.designpattern.proxy;

import com.jayfulmath.designpattern.main.BasicExample;

public class ProxyMain extends BasicExample {

	@Override
	public void startDemo() {
		ProxyCamera mCamera = ProxyCamera.getInstance();
		mCamera.open();
		mCamera.close();
	}

}
