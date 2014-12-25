package com.jayfulmath.designpattern.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;

import com.jayfulmath.designpattern.command.cooker.ISubject;
import com.jayfulmath.designpattern.command.cooker.StandardCooker;

public class OrderWaiter extends Thread{
	Queue<Menu> _mMenuQueue = new LinkedList<Menu>();
	boolean flag = true;
	private Object objlock = new Object();
	String name;
	PVObject mMenuEmpty = new PVObject("menuobject");
	private StandardCooker _mMeatCook = null;
	private StandardCooker _mFruitCook = null;
	private StandardCooker _mVegetableCook = null;
	private List<ISubject> mListSub = new ArrayList<ISubject>();
	private List<StandardCooker> mListCooker = new ArrayList<StandardCooker>();

	public OrderWaiter(String name)
	{
		this.name = name;
		mMenuEmpty.Init(1);
		_mMeatCook = CookerFactory.CreateCooker("meat");
		_mFruitCook = CookerFactory.CreateCooker("fruit");
		_mVegetableCook = CookerFactory.CreateCooker("vegetable");
		_mMeatCook.mPvoObject.Init(1);
		_mFruitCook.mPvoObject.Init(1);
		_mVegetableCook.mPvoObject.Init(1);
		mListCooker.add(_mMeatCook);
		mListCooker.add(_mFruitCook);
		mListCooker.add(_mVegetableCook);
		mListSub.addAll(mListCooker);
	}
	
	public void customerOrderMenu(Customer customer)
	{
		for(ISubject sub:mListSub)
		{
			sub.Attach(customer);
		}
		
		synchronized(objlock){
			_mMenuQueue.offer(customer.get_mMenu());
			mMenuEmpty.V();
		}
	}

	public void executeCookerThread(Executor mExcutor)
	{
		mExcutor.execute(_mFruitCook);
		mExcutor.execute(_mMeatCook);
		mExcutor.execute(_mVegetableCook);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		System.out.println(name+" Start order,thread id "+Thread.currentThread().getId());
		GlobalValue._mCountDown.countDown();

		while(flag)
		{
			//等待菜单不为空权限
			mMenuEmpty.P();
			
			synchronized(objlock){
				Menu menu = _mMenuQueue.poll();
				if(menu!=null)
				{
					if(menu.get_mMeat()>0)
					{
						_mMeatCook.addCustomer(menu.get_mCustomId());
						_mMeatCook.mPvoObject.V();
					}
					
					if(menu.get_mFruit()>0)
					{
						_mFruitCook.addCustomer(menu.get_mCustomId());
						_mFruitCook.mPvoObject.V();
					}
					
					if(menu.get_mVegetable()>0)
					{
						_mVegetableCook.addCustomer(menu.get_mCustomId());
						_mVegetableCook.mPvoObject.V();
					}
				}
			}
		}
		
		GlobalValue._mCountDownEnd.countDown();
		System.out.println(name+" thred is end");
	}
	
	/**
	 * @return the mListSub
	 */
	public List<ISubject> getmListSub() {
		return mListSub;
	}
	
	public void stopOrderWaiting()
	{
		flag = false;
		mMenuEmpty.V();
		for(StandardCooker sub:mListCooker)
		{
			sub.stopRunning();			
		}
	}
}
