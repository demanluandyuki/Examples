package com.jayfulmath.designpattern.strategy;

import java.text.DecimalFormat;

public class CashContext {
	private ICashSuper cs = null;

	public CashContext(String type) {
		System.out.println("商品促销活动"+type);
		switch(type)
		{
		case "Normal":
			cs = new CashNormal();
			break;
		case "Return 100 Reach 300":
			cs = new CashReturn("300.0", "100.0");
			break;
		case "80%Sale":
			cs = new CashSale("0.8");
			break;
		default:
			System.out.println("没有符合的商品促销活动");
			break;
		}
	}

	public double getResult(double money) {
		double result = money;
		if(cs != null)
		{
			result =  cs.acceptCash(money);
		}
		String resultstr = new DecimalFormat("0.00").format(result);
		result = Double.parseDouble(resultstr);
		
		return result;
		
	}

}
