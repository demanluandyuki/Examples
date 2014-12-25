package com.jayfulmath.designpattern.command;

import java.util.concurrent.CountDownLatch;

public class GlobalValue {
	public static CountDownLatch _mCountDown = new CountDownLatch(4);
	public static CountDownLatch _mCountDownEnd = new CountDownLatch(4);
	public static CountDownLatch _mCountDownCustom = null;
	
	public enum MENUTYPE{
		MEAT_TYPE ,
		VEGETABLE_TYPE,
		FRUIT_TYPE,
	}
}
