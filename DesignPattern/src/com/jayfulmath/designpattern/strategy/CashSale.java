package com.jayfulmath.designpattern.strategy;

public class CashSale implements ICashSuper {

	private double monryrate = 1d;
	
	public CashSale(String monryrateStr) {
		this.monryrate = Double.parseDouble(monryrateStr);
	}

	@Override
	public double acceptCash(double money) {		
		return monryrate*money;
	}

}
