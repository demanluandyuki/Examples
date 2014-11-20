package com.multithread.pvoperator;

import com.multithread.main.ExampleInterface;

public class PVExample extends ExampleInterface {
	/*PV 操作的关键在于分析，同步与互斥的问题。
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
