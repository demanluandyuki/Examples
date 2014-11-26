package com.jayfulmath.designpattern.factory;

import com.jayfulmath.designpattern.samplefactory.AbstractOperator;

public interface IOperatorFactory {
	AbstractOperator CreateOperation();
}
