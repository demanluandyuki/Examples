package com.jayfulmath.designpattern.main;

import com.jayfulmath.designpattern.bridge.BridgeMain;
//import com.jayfulmath.designpattern.composite.CompositeMain;
//import com.jayfulmath.designpattern.adapter.AdapterMain;
//import com.jayfulmath.designpattern.builder.BuildMain;
//import com.jayfulmath.designpattern.facade.FacadeMain;
//import com.jayfulmath.designpattern.observer.ObserverMain;
//import com.jayfulmath.designpattern.statusmode.StatusModeMain;
//import com.jayfulmath.designpattern.memento.MementoMain;


public class MainThread {
	   public static void main(String args[]) {
		   BasicExample sf = new BridgeMain();
		   sf.startDemo();		   
		   System.exit(0);
	    } 
}

