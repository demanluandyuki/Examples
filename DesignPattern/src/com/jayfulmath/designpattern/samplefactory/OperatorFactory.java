package com.jayfulmath.designpattern.samplefactory;

public class OperatorFactory {

	public static AbstractOperator CreateOperator(String operatorstr)
	{
		AbstractOperator oper = null;
		switch(operatorstr)
		{
		case "+":
			oper = new OperatorAdd();
			break;
		case "-":
			oper = new OperatorDelete();
			break;
		case "*":
			oper = new OperatorPlus();
			break;
		case "/":
			oper = new OperatorDevide();
			break;
		case "squar":
			oper = new OperatorSquare();
			break;	
		}
		
		return oper;
	}
}
