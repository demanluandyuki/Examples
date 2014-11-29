package com.example.multithread.pv.fruit;

public class PVAutom {
	
	private int S = 0;
	
	public PVAutom(int initStatus)
	{
		S=initStatus;
	}
	
	public void P()
	{
		S = S -1;
		if(S>=0)
		{
			//continue
		}
		else
		{
			//wait
		}
	}
	
	public void V()
	{
		
		S = S+1;
		// this action contiue
		
		//and if some one wait at P,continue it.
		System.
	}
}
