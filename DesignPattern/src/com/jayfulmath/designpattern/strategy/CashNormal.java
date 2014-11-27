package com.jayfulmath.designpattern.strategy;

public class CashNormal implements ICashSuper {

	@Override
	public double acceptCash(double money) {
		return money;
	}

}
