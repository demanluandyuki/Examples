package com.multithread.pvoperator;

import com.multithread.main.ExampleInterface;

public class PVExample extends ExampleInterface {
	/*PV �����Ĺؼ����ڷ�����ͬ���뻥������⡣
	 * 
	 * */
	@Override
	public void startDemo() {
//		Fruit mFruit = new Fruit();
//		mFruit.Start();
		
		SafeIsland mSafeIsland = new SafeIsland();
		mSafeIsland.start();
	}

}
