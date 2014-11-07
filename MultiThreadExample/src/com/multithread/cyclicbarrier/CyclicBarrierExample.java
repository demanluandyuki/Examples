package com.multithread.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.multithread.main.ExampleInterface;

public class CyclicBarrierExample extends ExampleInterface {

	@Override
	public void startDemo() {
		 System.out.println("��ʼ�̣߳�"+Thread.currentThread().getName());
		// TODO Auto-generated method stub
		final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {

			/*
			 * ���������һ��await������̣߳��������̣߳�
			 * */
		    @Override
		    public void run() {
		        System.out.println("����ѡ��ready"+"�̣߳�"+Thread.currentThread().getName());
		    }
		});
		
		ExecutorService executor = Executors.newFixedThreadPool(3); 
        executor.submit(new Thread(new Runner(barrier, "1��ѡ��")));  
        executor.submit(new Thread(new Runner(barrier, "2��ѡ��")));  
        executor.submit(new Thread(new Runner(barrier, "3��ѡ��")));  
  
        executor.shutdown();
	}

	@Override
	public void startDemo2() {
		startDemo();
	}

	
}
