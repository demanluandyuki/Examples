package com.multithread.prosumer;

public class ProducerThread extends Thread {

	final int END_PRODUCE_NUMBER = 10;   //生产产品个数  
	
	ProsumerExample mProsumer = null;
	String name = null;
	public ProducerThread(ProsumerExample pe,String name)
	{
		mProsumer = pe;
		this.name = name;
	}
	
	@Override
	public void run() {
		System.out.println(name+"操作開始");
		mProsumer.mLatchStart.countDown();
		for(int i=0;i<=END_PRODUCE_NUMBER;i++)
		{
			try {
				//等待缓冲区为空
				mProsumer.waitEmpty();
				//互斥的访问缓冲区 
				synchronized (mProsumer.objlock) {
					int index = mProsumer.g_produtor.size();
					mProsumer.g_produtor.add(i);
					System.out.println(name+"将数据"+i+"放入缓冲区位置："+index+1);
				}
				
				//通知缓冲区有新数据了
				mProsumer.singalExist();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			finally{
			}			
		}
		System.out.println(name+"操作结束");
		mProsumer.mLatchDown.countDown();
		
	}
	
	
	
}
