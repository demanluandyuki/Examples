package com.jayfulmath.designpattern.samplefactory;

import com.jayfulmath.designpattern.main.BasicExample;

/*简单工厂模式.
 *把所有操作都抽象成一个类，然后通过需要通过多态来确定具体的操作
 *然后以工厂模式的产生这些方法 
 * 
 * 
 * */


public class SampleFactoryMain extends BasicExample {

	@Override
	public void startDemo() {
		//add
		AbstractOperator addop = OperatorFactory.CreateOperator("+");
		if(addop!=null)
		{
			addop.NumberA = 1;
			addop.NumberB = 2;
			System.out.println(addop.NumberA+" add "+addop.NumberB+" = "+addop.GetResult());	
		}
		
		//squar
		AbstractOperator squarop = OperatorFactory.CreateOperator("squar");
		if(squarop!=null)
		{
			squarop.NumberA = 1.2;
			System.out.println(addop.NumberA+" squar " +" = "+squarop.GetResult());
		}
	}

}
