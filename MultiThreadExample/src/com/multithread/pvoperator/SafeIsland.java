package com.multithread.pvoperator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SafeIsland {
	
	public PVObject NT = new PVObject("NLoad");
	public PVObject TN = new PVObject("TLoad");
	public PVObject K = new PVObject("K");
	public PVObject L = new PVObject("L");
	public static final int MAX_NANKAI_CAR_COUNT = 2;
	public static final int MAX_TIANJING_CAR_COUNT = 3;
	public CountDownLatch mLatchDown = new CountDownLatch(MAX_NANKAI_CAR_COUNT+MAX_TIANJING_CAR_COUNT);
	
	public class NanKaiCar extends Thread{
		String name = null;
		public NanKaiCar(String name)
		{
			this.name = name;
		}
		@Override
		public void run() {
			System.out.println("[NanKaiCar]"+name+" Thread start");
			try {
				Thread.sleep((long) (Math.random()*100));
				NT.P();
				System.out.println("[NanKaiCar]"+name+" enter crossing N");
				K.P();
				System.out.println("[NanKaiCar]"+name+" walk to M:N->M");
				Thread.sleep((long) (Math.random()*1000));
				System.out.println("[NanKaiCar]"+name+" start walk to T");
				K.V();
				L.P();
				System.out.println("[NanKaiCar]"+name+" walk to T:M->T");
				L.V();
				NT.V();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mLatchDown.countDown();
			System.out.println("[NanKaiCar]"+name+" walk down");
		}
		
		
	}
	
	public class TianJingCar extends Thread{
		String name = null;
		public TianJingCar(String name)
		{
			this.name = name;
		}
		@Override
		public void run() {
			
			try {
				System.out.println("[TianJingCar]"+name+" Thread start");
				Thread.sleep((long) (Math.random()*100));
				TN.P();
				System.out.println("[TianJingCar]"+name+" enter crossing T");
				L.P();
				System.out.println("[TianJingCar]"+name+" walk to M:T->M");
				Thread.sleep((long) (Math.random()*1000));
				System.out.println("[TianJingCar]"+name+" start walk to N");
				L.V();
				K.P();
				System.out.println("[TianJingCar]"+name+" walk to T:M->N");
				K.V();
				TN.V();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mLatchDown.countDown();
			System.out.println("[TianJingCar]"+name+" walk down");
		}		
		
	}

	public void start()
	{
		NT.Init(1);
		TN.Init(1);
		K.Init(1);
		L.Init(1);
		Executor mEcecutor = Executors.newFixedThreadPool(MAX_TIANJING_CAR_COUNT+MAX_NANKAI_CAR_COUNT+1);
		for(int i =1;i<=MAX_NANKAI_CAR_COUNT;i++)
		{
			mEcecutor.execute(new NanKaiCar("carN"+i));
		}
		for(int j=1;j<=MAX_TIANJING_CAR_COUNT;j++)
		{
			mEcecutor.execute(new TianJingCar("carT"+j));
		}
		try {
			mLatchDown.await();
			System.out.println("all car has pass road");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

