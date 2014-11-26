package com.jayfulmath.designpattern.factory;

import com.jayfulmath.designpattern.samplefactory.AbstractOperator;
import com.jayfulmath.designpattern.samplefactory.OperatorPlus;

public class PlusFactory implements IOperatorFactory {

	@Override
	public AbstractOperator CreateOperation() {
		// TODO Auto-generated method stub
		return new OperatorPlus();
	}

}
