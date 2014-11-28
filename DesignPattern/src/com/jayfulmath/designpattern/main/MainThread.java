package com.jayfulmath.designpattern.main;

//import com.jayfulmath.designpattern.decorate.DecorateMain;
import com.jayfulmath.designpattern.proxy.ProxyMain;

public class MainThread {
	   public static void main(String args[]) {
		   BasicExample sf = new ProxyMain();
		   sf.startDemo();
		   
		   
		   System.exit(0);
	    } 
}

