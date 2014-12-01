package com.jayfulmath.designpattern.builder;

import com.jayfulmath.designpattern.main.BasicExample;


/*对于构建过程，可以使用Director来固定
 *对于实现类ConcreateBuilder and  ConcreateBuilderB, 可以使用抽象工厂模式的方式进行封装.
 * 
 * */
public class BuildMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		IBuilder builder = BuildFactory.operator();
		Director mDirector = new Director(builder);
		mDirector.construct();
		Productor mProductor = builder.getResult();
		System.out.println("productor:"+mProductor.toString());
	}

}
