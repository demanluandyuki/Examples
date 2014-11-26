package com.jayfulmath.designpattern.main;

import com.jayfulmath.designpattern.factory.FactoryMain;
import com.jayfulmath.designpattern.samplefactory.SampleFactoryMain;

public class MainThread {
	   public static void main(String args[]) {
		   BasicExample sf = new FactoryMain();
		   sf.startDemo();
		   
		   
		   System.exit(0);
	    } 
}

