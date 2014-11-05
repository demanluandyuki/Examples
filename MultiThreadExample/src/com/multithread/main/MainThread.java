package com.multithread.main;

import com.multithread.main.event.ThreadEventExample;
import com.multithread.main.sampleExample.SampleExample;
import com.multithread.semaphore.CountDownLunchExample;
import com.multithread.semaphore.SemaphoreExample;

public class MainThread {
	   public static void main(String args[]) {
//		   ExampleInterface se = new  SampleExample();
//		   se.startDemo();
//		   ExampleInterface te = new ThreadEventExample();
//		   te.startDemo();
//		   ExampleInterface smea = new SemaphoreExample();
//		   smea.startDemo();
//		   smea.startDemo2();
		   
		   ExampleInterface cd = new CountDownLunchExample();
		   cd.startDemo();
	    } 
}

