package com.multithread.prosumer;

public class ProducerThread extends Thread {

	ProsumerExample mProsumer = null;
	String name = null;
	boolean flag = true;
	public ProducerThread(ProsumerExample pe,String name)
	{
		mProsumer = pe;
		this.name = name;
	}
	
	@Override
	public void run() {
		System.out.println(name+"操作開始");
		mProsumer.mLatchStart.countDown();
//		for(int i=0;i<=END_PRODUCE_NUMBER;i++)
//		{
//			try {
//				//等待缓冲区为空
//				mProsumer.waitEmpty(name);
//				//互斥的访问缓冲区 
//				synchronized (mProsumer.objlock) {
//					int index = mProsumer.g_produtor.size();
//					mProsumer.g_produtor.offer(i);
//					System.out.println(name+"将数据"+i+"放入缓冲区位置："+(index+1));
//				}
//				
//				//通知缓冲区有新数据了
//				mProsumer.singalExist(name);
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				break;
//			}
//			finally{
//			}			
//		}
		
		while(flag)
		{
			//等待缓冲区为空
			mProsumer.waitEmpty(name);
			
			//互斥的访问缓冲区 
			synchronized (mProsumer.objlock){
				if(mProsumer.mProductor<ProsumerExample.TOTAL_PRODUCTORS)
				{
					int index = mProsumer.g_produtor.size();
					mProsumer.g_produtor.offer(mProsumer.mProductor);
					System.out.println(name+"将数据"+mProsumer.mProductor+"放入缓冲区位置："+(index+1));
					++mProsumer.mProductor;
					if(mProsumer.mProductor>=ProsumerExample.TOTAL_PRODUCTORS)
					{
						flag = false;
						mProsumer.releaseEmptySingal();
					}					
				}
				else
				{
					flag = false;//结束操作
					break;//不用通知，应为没有产生新数据
				}
			}
			
			
			//通知缓冲区有新数据了
			mProsumer.singalExist(name);
		}
		System.out.println(name+"操作结束");
		mProsumer.mLatchDown.countDown();
		
	}
	
	
	
}
