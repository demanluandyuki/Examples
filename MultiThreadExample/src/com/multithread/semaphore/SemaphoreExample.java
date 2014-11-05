package com.multithread.semaphore;

import java.util.concurrent.Semaphore;

import com.multithread.main.ExampleInterface;

/**
 * semaphore 可以控制某个资源被同时使用的个数。
 * 通过 acquire()获得许可，没有就等待。
 * release() 释放一个许可。
 * 
 * */
public class SemaphoreExample extends ExampleInterface{
	
	final Semaphore sema = new Semaphore(1);
	int g_num = 0;
	@Override
	public void startDemo() {

		for(int i=0;i<10;i++)
		{
			Thread se = new SemaMultiThread(i,sema);
			se.start();
		}
		
	}
	
	
	
	
	@Override
	public void startDemo2() {
		final Semaphore _lsema = new Semaphore(10);
		for(int i=0;i<50;i++)
		{
			Thread se = new SemaMultiThread(i,_lsema);
			se.start();
		}
	}




	class SemaMultiThread extends Thread {
		
		int index = -1;
		Semaphore mSema = null;
		public SemaMultiThread(int i,Semaphore se) {
			this.index = i;
			mSema = se;
		}

		@Override
		public void run() {
			try {
				mSema.acquire();
				System.out.println("index:"+this.index);
				Thread.sleep((long) (Math.random()*100));
				mSema.release();
				System.out.println("----------------:"+mSema.availablePermits());
				Thread.sleep((long) (Math.random()*100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
