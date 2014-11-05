package com.multithread.main.event;

public class Person {
	private int id = 0;
	
	public void addId()
	{
		id++;
	}
	
	public void reduceId(){
		id--;
	}
	
	public int getId(){
		return id;
	}
}
