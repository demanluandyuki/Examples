package com.multithread.pvoperator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Fruit {
	/*
	 * �����ȿ���ͬ����������С��ȴ��������
		��һ���ְ�Ҫ�ȴ�����Ϊ�ա�
		�ڶ�������Ҫ�ȴ�����ˮ���ǽ��ӡ�
		������Ů��Ҫ�ȴ�����ˮ����ƻ����
		������������Ҫ���⴦�����Դ�����������Ӻ�����Ҫ�����⴦��ģ�
		��������Ŀ�еİְ֡����ӡ�Ů����ֻ��һ�����������Ƿ������ӵ���������һ����
		�������Ǹ�������ͬʱȥ�������ӣ��������Ҳ�Ͳ��������⴦����
	 * 
	 * */
	public PVObject mEmptyDash = new PVObject("emptyDash");//1
	public PVObject mApple = new PVObject("apple");	//0
	public PVObject mOranger = new PVObject("oranger");  //0
	public boolean mDadEnd = false;
	public CountDownLatch mLatchDown = new CountDownLatch(3);
	public CountDownLatch mLatchStart = new CountDownLatch(3);
	public Queue<Integer> mQueue = new LinkedList<Integer>();
	public void Start()
	{
		mEmptyDash.Init(1);
		mApple.Init(0);
		mOranger.Init(0);
		mQueue.clear();
		Executor mEcecutor = Executors.newFixedThreadPool(5);
		mEcecutor.execute(new Dad(this));
		mEcecutor.execute(new Daughter(this));
		mEcecutor.execute(new Son(this));

		try {
			mLatchStart.await();
			System.out.println("all thread start");
			
			mLatchDown.await();
			System.out.println("all thread down");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public class Dad extends Thread{
		
		public Fruit mFruit = null;
		boolean flag = true;
		public int MAX_FRUIT_COUNT = 20;
		public int index = 0;
		public Dad(Fruit f)
		{
			mFruit = f;
		}
		@Override
		public void run() {
			mLatchStart.countDown();
			while(flag)
			{
				mFruit.mEmptyDash.P();
				
				index++;
				if(index >=MAX_FRUIT_COUNT)
				{
					flag = false;
				}
				
				mQueue.offer(index);
				
				if((int)(Math.random()*2) == 1)
				{
					System.out.println("dad put apple"+index+" to dash");
					//apply
					mFruit.mApple.V();
				}
				else
				{
					//oranger
					System.out.println("dad put oranger"+index+" to dash");
					mFruit.mOranger.V();
				}
			}
			mFruit.mDadEnd = true;
			System.out.println("dad thread is end");
			mLatchDown.countDown();
		}
	}
	
	public class Daughter extends Thread{
		
		public Fruit mFruit = null;
		boolean flag = true;
		public Daughter(Fruit f)
		{
			mFruit = f;
		}
		@Override
		public void run() {
			mLatchStart.countDown();
			while(flag)
			{
				mFruit.mOranger.P();
				if(mQueue.size()>0)
				{
					System.out.println("Daughter get oranger"+mQueue.poll()+" from dash");				
					mFruit.mEmptyDash.V();
				}
				else
				{
					System.out.println("Daughter get oranger from dash,but dash is empty");				
				}
				
				if(mFruit.mDadEnd == true)
				{
					flag = false;
				}
			}
			System.out.println("Daughter thread is end");
			//notify son down,for this dad is down.
			mApple.V();
			mLatchDown.countDown();
		}
	}
	
	public class Son extends Thread{
		
		public Fruit mFruit = null;
		boolean flag = true;
		public Son(Fruit f)
		{
			mFruit = f;
		}
		@Override
		public void run() {
			mLatchStart.countDown();
			while(flag)
			{
				mFruit.mApple.P();
				if(mQueue.size()>0)
				{
					System.out.println("Son get apple"+mQueue.poll()+" from dash");				
					mFruit.mEmptyDash.V();
				}
				else
				{
					System.out.println("Son get apple from dash,but dash is empty");				
				}
				
				if(mFruit.mDadEnd == true)
				{
					flag = false;
				}
			}
			System.out.println("Son thread is end");
			mOranger.V();
			mLatchDown.countDown();
		}
	}
}
