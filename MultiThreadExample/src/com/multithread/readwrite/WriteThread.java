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
			//�ȴ����ж��߽�����ȡ������
			mRwExample.waitReaderEnd();
			
			int mWriteLength = (int) (Math.random()*9)+1;//1-10;
			if(mWriteLength >(ReaderWriterExample.BUFFER_LENGTH - index))
			{
				mWriteLength = ReaderWriterExample.BUFFER_LENGTH - index;
			}
	
			
			//д�����ݵ��ļ����  //check д�����ݵ����
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
			System.out.println(name+"д�����ݣ�"+writeline+"\t ��ǰindex��"+index);

			if(index>=ReaderWriterExample.BUFFER_LENGTH)
			{
				mRwExample.bStopFlag = true;
			}
			
			//֪ͨд��������������Զ�ȡ��
			mRwExample.singalWriteEnd();
						
			iFlag++;
		}
		
		System.out.println(name+"�̲߳�������");
		mRwExample.mLatchDown.countDown();
	}
	
}
