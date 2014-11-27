package com.jayfulmath.designpattern.strategy;

public class Commodity {
	
	public String _name;
	private double _price;
	private int _count;

	public double get_price() {
		return _price;
	}
	public void set_price(double _price) {
		this._price = _price;
	}
	
	public String get_name() {
		return _name;
	}
	
	public void set_name(String _name) {
		this._name = _name;
	}
	
	/**
	 * @return the _count
	 */
	public int get_count() {
		return _count;
	}
	/**
	 * @param _count the _count to set
	 */
	public void set_count(int _count) {
		this._count = _count;
	}
	
	public Commodity(String _name, double _price, int _count) {
		this._name = _name;
		this._price = _price;
		this._count = _count;
		System.out.println("买入商品"+_name+" "+_count+"个"+"单价"+_price);
	}

	public double getTotalPrice()
	{
		return _count*_price;		
	}
}
