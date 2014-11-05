package com.multithread.semaphore;

import java.util.concurrent.Semaphore;

import com.multithread.main.ExampleInterface;

/**
 * semaphore ���Կ���ĳ����Դ��ͬʱʹ�õĸ�����
 * ͨ�� acquire()�����ɣ�û�о͵ȴ���
 * release() �ͷ�һ����ɡ�
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
