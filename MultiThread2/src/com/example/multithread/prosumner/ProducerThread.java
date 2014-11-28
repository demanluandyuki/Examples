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
			//�ȴ��пյĻ��������� 
			
			
			//����ķ��ʻ�����  
			synchronized (mPrdu.objlock) {
				mPrdu.mProductors.add(i);
				System.out.println("�����߷������ݣ�"+i);
			}
			
			//֪ͨ����������������
		}
		
		
		
		mPrdu.mLatchEnd.countDown();
	}

}
