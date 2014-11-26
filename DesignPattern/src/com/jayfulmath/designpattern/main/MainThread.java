package com.jayfulmath.designpattern.main;

import com.jayfulmath.designpattern.abstractfactory.AbstractFactoryMain;
import com.jayfulmath.designpattern.factory.FactoryMain;

public class MainThread {
	   public static void main(String args[]) {
		   BasicExample sf = new AbstractFactoryMain();
		   sf.startDemo();
		   
		   
		   System.exit(0);
	    } 
}

