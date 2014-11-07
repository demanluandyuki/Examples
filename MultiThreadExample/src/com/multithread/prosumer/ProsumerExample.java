package com.multithread.prosumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import com.multithread.main.ExampleInterface;

public class ProsumerExample extends ExampleInterface {

	final int BUFFER_LENGTH = 4;
	final int CUSTOMER_SIZE = 2;
	public final int TOTAL_PRODUCTORS = 10;
	public Queue<Integer> g_produtor = new LinkedList<Integer>();
	public Object objlock = new Object();
	CountDownLatch StoreEmpty  = new CountDownLatch(1);
	CountDownLatch StoreHas  = new CountDownLatch(1);  
	
	public CountDownLatch mLatchDown = new CountDownLatch(1+CUSTOMER_SIZE);
	public CountDownLatch mLatchStart = new CountDownLatch(1+CUSTOMER_SIZE);
	
	@Override
 	public void startDemo() {
		g_produtor.clear();
		Thread producerThread = new ProducerThread(this,"生产者");
		Thread customThreadA = new CustomerThread(this,"消费者A");	
		Thread customThreadB = new CustomerThread(this,"消费者B");	

		try {
			producerThread.start();	
			customThreadA.start();
			customThreadB.start();
			mLatchStart.await();
			System.out.println("所有操作线程已启动...");
			singalEmpty();
			synchronized (StoreEmpty) {
				StoreEmpty.notify();
			}

			mLatchDown.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		System.out.println("ProsumerExample is done");
	}
	
	/*
	 * if true ,go back, if false, wait here
	 * */
	public void waitEmpty()
	{
		try {
			StoreEmpty.await();
			StoreEmpty = new CountDownLatch(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void singalEmpty()
	{
		StoreEmpty.countDown();
	}
	
	public void waitExist(){
		try {
			StoreHas.await();
			StoreHas = new CountDownLatch(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void singalExist(){
		StoreHas.countDown();
	}
}
