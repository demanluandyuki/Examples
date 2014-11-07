package com.multithread.prosumer;

public class CustomerThread extends Thread {

	final int END_PRODUCE_NUMBER = 10; // ������Ʒ����
	volatile boolean flag = true;
	ProsumerExample mProsumer = null;
	int mProductor = 0;
	String name = null;
	public CustomerThread(ProsumerExample pe,String name) {
		mProsumer = pe;
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println("---"+name+"�����_ʼ");
		mProsumer.mLatchStart.countDown();
		while (flag) {
			try {
				// �ȴ������������
				System.out.println("---"+name+"�ȴ�����������");
				mProsumer.waitExist();

				// ����ķ��ʻ�����
				synchronized (mProsumer.objlock) {
//					mProductor = mProsumer.g_produtor;
					System.out.println("---"+name+"������" + mProductor + "ȡ��������");
					if (mProductor == END_PRODUCE_NUMBER) {
						flag = false;
					}
				}

				// ֪ͨ�������ѿ�
				mProsumer.singalEmpty();

				// doing other things
				Thread.sleep(100);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}
		System.out.println("---�����߲�������");
		mProsumer.mLatchDown.countDown();
	}

}
