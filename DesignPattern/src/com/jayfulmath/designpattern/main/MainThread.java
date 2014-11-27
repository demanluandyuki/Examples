package com.jayfulmath.designpattern.main;

//import com.jayfulmath.designpattern.abstractfactory.AbstractFactoryMain;
//import com.jayfulmath.designpattern.factory.FactoryMain;
import com.jayfulmath.designpattern.strategy.StrategyMain;

public class MainThread {
	   public static void main(String args[]) {
		   BasicExample sf = new StrategyMain();
		   sf.startDemo();
		   
		   
		   System.exit(0);
	    } 
}

