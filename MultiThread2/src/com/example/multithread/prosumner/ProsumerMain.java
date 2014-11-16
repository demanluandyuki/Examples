package com.example.multithread.prosumner;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import com.example.multithread.main.ExampleInterface;

public class ProsumerMain extends ExampleInterface {
	public final static int MAX_BUFFER_SIZE = 4;
	public final static int CUSTOMER_SIZE = 2;
	public final static int PRODUCTOR_SIZE = 10;
	public Queue<Integer> mProductors = new LinkedList<Integer>();
	public Object objlock = new Object();
	public CountDownLatch mLatchStart = new CountDownLatch(1+CUSTOMER_SIZE);
	public CountDownLatch mLatchEnd = new CountDownLatch(1+CUSTOMER_SIZE);
	
	
	@Override
	public void StartDemo() {
		// TODO Auto-generated method stub
		Thread pr = new ProducerThread(this);
		pr.start();
		
		try {
			mLatchStart.await();
			System.out.println("所有线程开始启动");
			
			
			
			
			
			mLatchEnd.await();
			System.out.println("所有线程结束");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void WaitEmpty(){
		
	}
	
	public void WaitNotEmpty(){
		
	}
	
	
}
