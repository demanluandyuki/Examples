package com.multithread.readwrite;

public class WriteThread extends Thread {
	
	ReaderWriterExample mRwExample = null;
	String name = null;
	int iFlag = 0;
	
	public WriteThread(ReaderWriterExample re,String name)
	{
		mRwExample = re;
		this.name = name;
	}
	
	
	@Override
	public void run() {
		mRwExample.mLatchStart.countDown();
		int index = 0;
		while(index<ReaderWriterExample.BUFFER_LENGTH)
		{
			//等待所有读者结束读取操作。
			mRwExample.waitReaderEnd();
			
			int mWriteLength = (int) (Math.random()*9)+1;//1-10;
			if(mWriteLength >(ReaderWriterExample.BUFFER_LENGTH - index))
			{
				mWriteLength = ReaderWriterExample.BUFFER_LENGTH - index;
			}
	
			
			//写入数据到文件最后  //check 写入数据到最后。
			mRwExample.g_productor.clear();
			int mParam = 0;
			String writeline = "";
			for(int i=0;i<mWriteLength;i++)
			{
				mParam = (int) (Math.random()*14)+1;//0-E
				mRwExample.g_productor.add(mParam);
				writeline+= String.format("%1$x", mParam);
			}
			
			index = index +mWriteLength;
			System.out.println(name+"写入数据："+writeline+"\t 当前index："+index);

			if(index>=ReaderWriterExample.BUFFER_LENGTH)
			{
				mRwExample.bStopFlag = true;
			}
			
			//通知写入操作结束，可以读取。
			mRwExample.singalWriteEnd();
						
			iFlag++;
		}
		
		System.out.println(name+"线程操作结束");
		mRwExample.mLatchDown.countDown();
	}
	
}
