package com.jayfulmath.designpattern.factory;

import com.jayfulmath.designpattern.main.BasicExample;
import com.jayfulmath.designpattern.samplefactory.AbstractOperator;

public class FactoryMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		IOperatorFactory factoryA = new AddFactory();
		AbstractOperator OperationA = factoryA.CreateOperation();
		OperationA.NumberA = 1;
		OperationA.NumberB = 2;
		System.out.println("[FactoryMain]"+OperationA.NumberA+" add "+OperationA.NumberB+" = "+OperationA.GetResult());	
	}

}
