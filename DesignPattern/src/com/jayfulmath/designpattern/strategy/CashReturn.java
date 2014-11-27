package com.jayfulmath.designpattern.strategy;

public class CashReturn implements ICashSuper {

	private double moneyCondition = 0.0d;
	private double moneyReturn = 0.0d;

	public CashReturn(String moneyConditionStr, String moneyReturnStr) {
		try {
			moneyCondition = Double.parseDouble(moneyConditionStr);
			moneyReturn = Double.parseDouble(moneyReturnStr);
		} catch (Exception e) {
			System.out.println("parse condition wrong:" + moneyCondition
					+ " moneyReturn:" + moneyReturn);
		}
	}

	@Override
	public double acceptCash(double money) {
		double result = 0.0d;
		if(money>=moneyCondition)
		{
			result = money-Math.floor(money/moneyCondition)*moneyReturn;
		}
		return result;
	}

}
