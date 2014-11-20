package com.multithread.readwrite;

public class ReadThread extends Thread {

	
	ReaderWriterExample mRwExample = null;
	String name = null;
	boolean flag = true;
	public ReadThread(ReaderWriterExample re,String name)
	{
		mRwExample = re;
		this.name = name;
	}
	
	@Override
	public void run() {
		mRwExample.mLatchStart.countDown();
		while(flag)
		{
			//等待写入结束
			mRwExample.waitWriteEnd();
			
			//读取文件，直到本次末尾 //check 文件写入操作是否已经彻底结束，结束read线程。
			int mReadLength = mRwExample.g_productor.size();
			String mReadStr = "";
			if(mReadLength>0)
			{
				for(Integer a:mRwExample.g_productor)
				{
					mReadStr+=String.format("%x", a);
				}
				System.out.println(name+"读取数据："+mReadStr);
			}
			
			if(mRwExample.bStopFlag)
			{
				flag = false;
			}
			
			//通知本次读者结束。
			mRwExample.singalReadEnd();
		}
		
		System.out.println(name+"读取数据结束");
		mRwExample.mLatchDown.countDown();
	}
	
}
