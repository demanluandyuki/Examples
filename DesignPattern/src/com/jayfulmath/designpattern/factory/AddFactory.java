package com.jayfulmath.designpattern.factory;

import com.jayfulmath.designpattern.samplefactory.AbstractOperator;
import com.jayfulmath.designpattern.samplefactory.OperatorAdd;

public class AddFactory implements IOperatorFactory {

	@Override
	public AbstractOperator CreateOperation() {
		// TODO Auto-generated method stub
		return new OperatorAdd();
	}

}
