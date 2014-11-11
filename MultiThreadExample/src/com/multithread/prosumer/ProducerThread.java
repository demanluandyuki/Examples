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
		System.out.println(name+"�����_ʼ");
		mProsumer.mLatchStart.countDown();
//		for(int i=0;i<=END_PRODUCE_NUMBER;i++)
//		{
//			try {
//				//�ȴ�������Ϊ��
//				mProsumer.waitEmpty(name);
//				//����ķ��ʻ����� 
//				synchronized (mProsumer.objlock) {
//					int index = mProsumer.g_produtor.size();
//					mProsumer.g_produtor.offer(i);
//					System.out.println(name+"������"+i+"���뻺����λ�ã�"+(index+1));
//				}
//				
//				//֪ͨ����������������
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
			//�ȴ�������Ϊ��
			mProsumer.waitEmpty(name);
			
			//����ķ��ʻ����� 
			synchronized (mProsumer.objlock){
				if(mProsumer.mProductor<ProsumerExample.TOTAL_PRODUCTORS)
				{
					int index = mProsumer.g_produtor.size();
					mProsumer.g_produtor.offer(mProsumer.mProductor);
					System.out.println(name+"������"+mProsumer.mProductor+"���뻺����λ�ã�"+(index+1));
					++mProsumer.mProductor;
					if(mProsumer.mProductor>=ProsumerExample.TOTAL_PRODUCTORS)
					{
						flag = false;
						mProsumer.releaseEmptySingal();
					}					
				}
				else
				{
					flag = false;//��������
					break;//����֪ͨ��ӦΪû�в���������
				}
			}
			
			
			//֪ͨ����������������
			mProsumer.singalExist(name);
		}
		System.out.println(name+"��������");
		mProsumer.mLatchDown.countDown();
		
	}
	
	
	
}
