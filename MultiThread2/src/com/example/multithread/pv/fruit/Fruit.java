package com.example.multithread.pv.fruit;

/* 经典放水果问题
 * 爸爸往盘子中放水果，一次只能放一只水果
 * 儿子等待苹果
 * 女儿等待桔子
 * 
 * PV分析：
 * 爸爸：等待盘子为空
 * 女儿：等待盘子中的水果为桔子
 * 儿子：等待盘子中的水果是苹果
 * */

public class Fruit {

	/*	EmptyDash 	1
	 * 	Orange		0
	 *  apple		0
	 * */
	
	
	public void P(int singal)
	{
		
	}
	
	public void V(int singal)
	{
		
	}
	
	
}



