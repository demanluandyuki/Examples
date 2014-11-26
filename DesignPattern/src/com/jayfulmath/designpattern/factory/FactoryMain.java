package com.jayfulmath.designpattern.factory;

import com.jayfulmath.designpattern.main.BasicExample;
import com.jayfulmath.designpattern.samplefactory.operator.AbstractOperator;

public class FactoryMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		IOperatorFactory addFactory = new AddFactory();
		AbstractOperator addOperation = addFactory.CreateOperation();
		addOperation.NumberA = 1;
		addOperation.NumberB = 2;
		System.out.println("[FactoryMain]"+addOperation.NumberA+" add "+addOperation.NumberB+" = "+addOperation.GetResult());	
	}

}
