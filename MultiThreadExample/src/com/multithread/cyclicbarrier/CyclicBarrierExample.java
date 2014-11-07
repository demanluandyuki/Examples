package com.multithread.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.multithread.main.ExampleInterface;

public class CyclicBarrierExample extends ExampleInterface {

	@Override
	public void startDemo() {
		 System.out.println("开始线程："+Thread.currentThread().getName());
		// TODO Auto-generated method stub
		final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {

			/*
			 * 运行在最后一个await到达的线程，不是主线程！
			 * */
		    @Override
		    public void run() {
		        System.out.println("所有选手ready"+"线程："+Thread.currentThread().getName());
		    }
		});
		
		ExecutorService executor = Executors.newFixedThreadPool(3); 
        executor.submit(new Thread(new Runner(barrier, "1号选手")));  
        executor.submit(new Thread(new Runner(barrier, "2号选手")));  
        executor.submit(new Thread(new Runner(barrier, "3号选手")));  
  
        executor.shutdown();
	}

	@Override
	public void startDemo2() {
		startDemo();
	}

	
}
