package com.jayfulmath.designpattern.factory;

import com.jayfulmath.designpattern.samplefactory.operator.AbstractOperator;

public interface IOperatorFactory {
	AbstractOperator CreateOperation();
}
