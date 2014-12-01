package com.jayfulmath.designpattern.main;

import com.jayfulmath.designpattern.facade.FacadeMain;


public class MainThread {
	   public static void main(String args[]) {
		   BasicExample sf = new FacadeMain();
		   sf.startDemo();		   
		   
		   System.exit(0);
	    } 
}

