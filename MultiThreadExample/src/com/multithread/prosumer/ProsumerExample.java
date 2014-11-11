package com.multithread.prosumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.multithread.main.ExampleInterface;

public class ProsumerExample extends ExampleInterface {

	static final int BUFFER_LENGTH = 10;
	static final int CUSTOMER_SIZE = 4;
	static final int PRODUCTOR_SIZE = 3;
	public static final int TOTAL_PRODUCTORS = 200;
	public Queue<Integer> g_produtor = new LinkedList<Integer>();
	public volatile int mProductor = 0;
	public Object objlock = new Object();
	Semaphore StoreEmpty  = new Semaphore(BUFFER_LENGTH);//等待缓冲区数据
	Semaphore StoreHas  = new Semaphore(BUFFER_LENGTH);  
	
	public CountDownLatch mLatchDown = new CountDownLatch(PRODUCTOR_SIZE+CUSTOMER_SIZE);
	public CountDownLatch mLatchStart = new CountDownLatch(PRODUCTOR_SIZE+CUSTOMER_SIZE);
	
	public boolean bStopCustomFlag = false;
	public boolean bStopProductorFlag = false;
	
	@Override
 	public void startDemo() {
		try {
			g_produtor.clear();		
			bStopCustomFlag = false;
			initEmptySingal();
			initExistSingal();
			
			Executor mEcecutor = Executors.newFixedThreadPool(PRODUCTOR_SIZE+CUSTOMER_SIZE);
			
			for(int i=1;i<=PRODUCTOR_SIZE;i++)
			{
				mEcecutor.execute(new ProducerThread(this,"生产者"+i));
			}
			
			for(int j=1;j<=CUSTOMER_SIZE;j++)
			{
				char c =(char)(j+'A'-1);
				mEcecutor.execute(new CustomerThread(this,"消费者"+c));
			}
			
			mLatchStart.await();
			System.out.println("所有操作线程已启动...");
			mLatchDown.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		System.out.println("所有线程操作结束");
	}
	
	/*
	 * if true ,go back, if false, wait here
	 * */
	public void waitEmpty(String name)
	{
		try {
//			System.out.println("[waitEmpty]"+name+"等待缓冲区，有空余地方："+StoreEmpty.availablePermits());
			StoreEmpty.acquire();
//			System.out.println("[waitEmpty]"+name+"等待缓冲区，有空余地方结束 剩余空间："+StoreEmpty.availablePermits());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void singalEmpty(String name)
	{
		StoreEmpty.release();
//		System.out.println("[singalEmpty]"+name+"缓冲区释放空余地方，剩余空间："+StoreEmpty.availablePermits());
	}
	
	public void waitExist(String name){
		try {
//			System.out.println("[waitExist]"+name+"等待缓冲区，数据存放空间："+StoreHas.availablePermits());
			StoreHas.acquire();
//			System.out.println("[waitExist]"+name+"缓冲区有数据放入,缓冲区数据个数："+StoreHas.availablePermits());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void singalExist(String name){
		StoreHas.release();
//		System.out.println("[singalExist]"+name+"将数据放入缓冲区："+StoreHas.availablePermits());
	}
	
	public void initEmptySingal()
	{
		//init,all is empty;
//		try {
//			StoreEmpty.acquire(BUFFER_LENGTH-1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public void initExistSingal()
	{
		//init,nothing is exist
		try {
//			System.out.println("释放所有缓冲区数据，消费线程全部等待："+StoreHas.availablePermits());
			StoreHas.acquire(StoreHas.availablePermits());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void releaseExistSingal(){
//		System.out.println("[releaseExistSingle]等待缓冲区有数据放入：释放所有"+StoreHas.availablePermits());
		StoreHas.release(BUFFER_LENGTH);
	}
	
	public void releaseEmptySingal(){
		StoreEmpty.release(BUFFER_LENGTH);
	}
}
