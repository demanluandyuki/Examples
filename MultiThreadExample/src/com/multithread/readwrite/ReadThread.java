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
			//�ȴ�д�����
			mRwExample.waitWriteEnd();
			
			//��ȡ�ļ���ֱ������ĩβ //check �ļ�д������Ƿ��Ѿ����׽���������read�̡߳�
			int mReadLength = mRwExample.g_productor.size();
			String mReadStr = "";
			if(mReadLength>0)
			{
				for(Integer a:mRwExample.g_productor)
				{
					mReadStr+=String.format("%x", a);
				}
				System.out.println(name+"��ȡ���ݣ�"+mReadStr);
			}
			
			if(mRwExample.bStopFlag)
			{
				flag = false;
			}
			
			//֪ͨ���ζ��߽�����
			mRwExample.singalReadEnd();
		}
		
		System.out.println(name+"��ȡ���ݽ���");
		mRwExample.mLatchDown.countDown();
	}
	
}
