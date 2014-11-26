package com.jayfulmath.designpattern.samplefactory.operator;

public class OperatorSquare extends AbstractOperator {

	@Override
	public double GetResult() {
		
		return (NumberA*NumberA);
	}

}
