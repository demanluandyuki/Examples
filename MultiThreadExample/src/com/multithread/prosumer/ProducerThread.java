package com.multithread.prosumer;

public class ProducerThread extends Thread {

	final int END_PRODUCE_NUMBER = 10;   //������Ʒ����  
	
	ProsumerExample mProsumer = null;
	String name = null;
	public ProducerThread(ProsumerExample pe,String name)
	{
		mProsumer = pe;
		this.name = name;
	}
	
	@Override
	public void run() {
		System.out.println(name+"�����_ʼ");
		mProsumer.mLatchStart.countDown();
		for(int i=0;i<=END_PRODUCE_NUMBER;i++)
		{
			try {
				//�ȴ�������Ϊ��
				mProsumer.waitEmpty();
				//����ķ��ʻ����� 
				synchronized (mProsumer.objlock) {
					int index = mProsumer.g_produtor.size();
					mProsumer.g_produtor.add(i);
					System.out.println(name+"������"+i+"���뻺����λ�ã�"+index+1);
				}
				
				//֪ͨ����������������
				mProsumer.singalExist();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			finally{
			}			
		}
		System.out.println(name+"��������");
		mProsumer.mLatchDown.countDown();
		
	}
	
	
	
}
