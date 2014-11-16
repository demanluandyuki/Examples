package com.example.multithread.prosumner;

public class ProducerThread extends Thread {

	ProsumerMain mPrdu = null;
	public ProducerThread(ProsumerMain pr)
	{
		mPrdu = pr;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		mPrdu.mLatchStart.countDown();
		
		for(int i=0;i<ProsumerMain.PRODUCTOR_SIZE;i++)
		{
			//等待有空的缓冲区出现 
			
			
			//互斥的访问缓冲区  
			synchronized (mPrdu.objlock) {
				mPrdu.mProductors.add(i);
				System.out.println("生产者放入数据："+i);
			}
			
			//通知消费者有新数据了
		}
		
		
		
		mPrdu.mLatchEnd.countDown();
	}

}
