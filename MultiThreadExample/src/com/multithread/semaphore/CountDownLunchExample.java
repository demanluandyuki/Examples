package com.multithread.semaphore;

import java.util.concurrent.CountDownLatch;

import com.multithread.main.ExampleInterface;

public class CountDownLunchExample extends ExampleInterface {
	
	final CountDownLatch countDown = new CountDownLatch(50);
	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		for(int i=0;i<50;i++)
		{
			Thread cd = new SemaMultiThread(i,countDown);
			cd.start();
		}
		try {
			countDown.await();
			System.out.println("[CountDownLunchExample]all thread is down");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
class SemaMultiThread extends Thread {
		
		int index = -1;
		CountDownLatch mSema = null;
		public SemaMultiThread(int i,CountDownLatch se) {
			this.index = i;
			mSema = se;
		}
		@Override
		public void run() {
			try {
				Thread.sleep((long) (Math.random()*100));
				mSema.countDown();
				System.out.println("[CountDownLunchExample]index:"+this.index);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
