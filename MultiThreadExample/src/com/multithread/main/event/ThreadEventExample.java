package com.multithread.main.event;

import java.util.concurrent.CountDownLatch;

import com.multithread.main.ExampleInterface;

public class ThreadEventExample extends ExampleInterface {
	private ThreadEventManager mManager = null;
	protected Integer g_num = 0;
	final int MAX_THREAD_NUM = 10;
	CountDownLatch threadCountDown = new CountDownLatch(1);
	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		if (mManager == null) {
			mManager = new ThreadEventManager();
		}
		Person person = new Person();

		mManager.addListener(new ThreadEventImplment(person));
		CountDownLatch threadCountDown = new CountDownLatch(1);
		while(person.getId()<MAX_THREAD_NUM)
		{
			Thread te = new ThreadEventExampleMultiThread(person,threadCountDown);
			te.start();
			person.addId();
		}

		System.out.println(person.getId()+"thread:"+Thread.currentThread().getId());
		
	}

	class ThreadEventExampleMultiThread extends Thread {

		Person p = null;
		CountDownLatch obj;
		public ThreadEventExampleMultiThread(Person p,CountDownLatch o) {
			this.p = p;
			obj =o;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				synchronized (g_num) {
					Thread.sleep(100);
					g_num++;
					System.out.println("g_num is:" + g_num + "\t" + "index is:"
							+ this.p.getId()+"\tthread:"+Thread.currentThread().getId());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
}
