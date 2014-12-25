package com.jayfulmath.designpattern.command.cooker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jayfulmath.designpattern.command.GlobalValue;
import com.jayfulmath.designpattern.command.GlobalValue.MENUTYPE;
import com.jayfulmath.designpattern.command.PVObject;

public abstract class StandardCooker extends Thread implements ICook,ISubject{
	
	public PVObject mPvoObject = null;
	private Queue<Integer> mQueue = new LinkedList<Integer>();
	private Object objlock = new Object();
	List<Observer> _mObservers = new ArrayList<Observer>();	
	String name;
	boolean flag;
	MENUTYPE type;
	
	public StandardCooker(String name) {
		this.name = name;
		mQueue.clear();
		flag = true;
		mPvoObject = new PVObject(name);
	}

	public abstract void cookfood();	
	
	@Override
	public void doCook() {
		synchronized (objlock) {
			Integer id = mQueue.poll();
			if(id!=null)
			{
				cookfood();
				Broadcast(id);
			}
		}
	}

	@Override
	public void addCustomer(int customId) {
		synchronized (objlock) {
			mQueue.add(customId);
		}
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		System.out.println(name+" Start Cook,thread id "+Thread.currentThread().getId());
		GlobalValue._mCountDown.countDown();
		
		while(flag)
		{
			mPvoObject.P();
			doCook();
		}
		System.out.println(name+" thred is end");
		GlobalValue._mCountDownEnd.countDown();
	}

	/* (non-Javadoc)
	 * @see com.jayfulmath.designpattern.command.cooker.ISubject#Attach(com.jayfulmath.designpattern.command.cooker.Observer)
	 */
	@Override
	public void Attach(Observer obs) {
		_mObservers.add(obs);		
	}

	/* (non-Javadoc)
	 * @see com.jayfulmath.designpattern.command.cooker.ISubject#Detach(com.jayfulmath.designpattern.command.cooker.Observer)
	 */
	@Override
	public void Detach(Observer obs) {
		_mObservers.remove(obs);
	}

	/* (non-Javadoc)
	 * @see com.jayfulmath.designpattern.command.cooker.ISubject#Broadcast()
	 */
	@Override
	public void Broadcast(int customId) {
		System.out.println(name+" notify customer:"+customId+type+"is ok");
		for(Observer observer:_mObservers)
		{
			observer.Update(type,customId);
		}
	}
	
	public void stopRunning()
	{
		flag = false;
		mPvoObject.V();
	}
}
