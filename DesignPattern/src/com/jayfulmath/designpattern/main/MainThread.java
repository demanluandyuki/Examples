package com.jayfulmath.designpattern.main;

//import com.jayfulmath.designpattern.builder.BuildMain;
//import com.jayfulmath.designpattern.facade.FacadeMain;
import com.jayfulmath.designpattern.observer.ObserverMain;


public class MainThread {
	   public static void main(String args[]) {
		   BasicExample sf = new ObserverMain();
		   sf.startDemo();		   
		   System.exit(0);
	    } 
}

