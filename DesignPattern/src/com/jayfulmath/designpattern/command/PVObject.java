package com.jayfulmath.designpattern.command;

import java.util.concurrent.Semaphore;

/*
	P(S)��
	
	�ٽ��ź���S��ֵ��1����S=S-1��
	
	�����S>=0����ý��̼���ִ�У�����ý�����Ϊ�ȴ�״̬��
	
	V(S)��
	
	�ٽ��ź���S��ֵ��1����S=S+1��
	
	�ڸý��̼���ִ�У�������źŵĵȴ��������еȴ����̾ͻ���һ�ȴ����̡�
 * 
 * */
public class PVObject {
	
	private Semaphore mSemaphore =null;
	private int Max_size = 0xff;
	private String name = null;
	public PVObject(int size,String name)
	{
		if(size>0)
		{
			Max_size = size;
			mSemaphore = new Semaphore(size);
		}
		this.name = name;
	}
	
	public PVObject(String name)
	{
		Max_size = 1;
		mSemaphore = new Semaphore(1);
		this.name = name;
	}
	
	public void Init(int status)
	{
		if(status<0 || status>Max_size)
		{
			System.out.println("[PVObject][Init]"+name+" wrong,status:"+status);
			return;
		}
		
		if(status == Max_size)
		{
			return;
		}
		
		try {
			mSemaphore.release(Max_size);
			mSemaphore.acquire(Max_size-status);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void P()
	{
		try {
			//
			mSemaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void V()
	{
		mSemaphore.release();		
	}
}
