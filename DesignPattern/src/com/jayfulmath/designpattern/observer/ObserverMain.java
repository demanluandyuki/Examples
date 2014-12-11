package com.jayfulmath.designpattern.observer;

import com.jayfulmath.designpattern.main.BasicExample;

/*观察者模式：一个主题类，发生状态改变后，通知其他观察者类来处理该类问题。
 *observer可以由工厂模式或者抽象工厂模式改进 
 *Subject是一个接口。
 *Observer对subject的观察行为是相同的。
 * */
public class ObserverMain extends BasicExample {

	@Override
	public void startDemo() {
		ISubject huhanshang = new Boss();
		Observer maweidong = ObserverFactory.Factory("NBALooker", "maweidong", huhanshang);
		Observer zhangdashang = ObserverFactory.Factory("StockLooker", "zhangdashang", huhanshang);
		
		huhanshang.Attach(maweidong);
		huhanshang.Attach(zhangdashang);
		
		huhanshang.setSubjectStatus("I am back! Huhan Shang!");
		huhanshang.Broadcast();
	}

}
