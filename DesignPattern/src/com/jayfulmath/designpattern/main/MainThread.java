package com.jayfulmath.designpattern.main;

import com.jayfulmath.designpattern.decorate.DecorateMain;

public class MainThread {
	   public static void main(String args[]) {
		   BasicExample sf = new DecorateMain();
		   sf.startDemo();
		   
		   
		   System.exit(0);
	    } 
}

