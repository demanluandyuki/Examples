package com.jayfulmath.designpattern.samplefactory;

public class OperatorDevide extends AbstractOperator {

	@Override
	public double GetResult() {

		double result = 0;
		if(NumberB!=0)
		{
			result = NumberA/NumberB;
		}
		else
		{
			System.out.println("NumberB is 0");
			result = Double.NaN;
		}
		return result;
	}

}
