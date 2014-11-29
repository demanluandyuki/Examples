package com.jayfulmath.designpattern.prototype;

public class Ball implements IProductor {
	
	private int id = -1;
	private String name;
	private double price;
	private String function;
		
	public Ball(String name, double price, String function) {
		this.name = name;
		this.price = price;
		this.function = function;
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return (name+function+price+" "+id);
	}

	@Override
	public IProductor clone(){
		IProductor _mproductor = new Ball(this.name,price,function);
		return _mproductor;
	}
	
	
	
}
