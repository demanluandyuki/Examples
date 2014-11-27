package com.jayfulmath.designpattern.strategy;

import com.jayfulmath.designpattern.main.BasicExample;

public class StrategyMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		CashContext cc = null;

		Commodity shoes = new Commodity("shoe", 123.5, 5);
		Commodity cloths = new Commodity("cloths", 323.2, 6);
		Commodity apple = new Commodity("apple", 15.62, 25);

		cc = new CashContext("70%Sale");
		double result = cc.getResult(shoes.getTotalPrice()
				+ cloths.getTotalPrice() + apple.getTotalPrice());
		System.out.println("Total price is:"+result);
	}

}
