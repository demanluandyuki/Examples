package com.jayfulmath.designpattern.adapter;

import com.jayfulmath.designpattern.main.BasicExample;

/*Client需要的是Target接口或抽象类，但是provider只能提供adaptee类型的接口。
 *这个时候需要一个adapter来转化接口，已适应Target的使用。
 * 
 * */
public class AdapterMain extends BasicExample {

	@Override
	public void startDemo() {
		Adapter ada = new Adapter();
		Client c = new Client(ada);
		c.doAction();
	}

}
