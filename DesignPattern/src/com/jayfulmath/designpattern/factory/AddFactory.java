package com.jayfulmath.designpattern.factory;

import com.jayfulmath.designpattern.samplefactory.operator.AbstractOperator;
import com.jayfulmath.designpattern.samplefactory.operator.OperatorAdd;

public class AddFactory implements IOperatorFactory {

	@Override
	public AbstractOperator CreateOperation() {
		// TODO Auto-generated method stub
		return new OperatorAdd();
	}

}
