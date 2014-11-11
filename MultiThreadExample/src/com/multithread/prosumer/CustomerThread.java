package com.multithread.prosumer;

public class CustomerThread extends Thread {
	volatile boolean flag = true;
	ProsumerExample mProsumer = null;
	int mProductor = 0;
	String name = null;

	public CustomerThread(ProsumerExample pe, String name) {
		mProsumer = pe;
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println("---" + name + "�����_ʼ");
		mProsumer.mLatchStart.countDown();
		while (flag) {
			try {
				// �ȴ������������
				System.out.println("---" + name + "�ȴ�����������");
				mProsumer.waitExist(name);

				// ����ķ��ʻ�����
				synchronized (mProsumer.objlock) {
					if (mProsumer.g_produtor.size() > 0) {
						mProductor = mProsumer.g_produtor.poll();
						System.out.println("---" + name + "������" + mProductor
								+ "ȡ��������");
						if (mProductor == (ProsumerExample.TOTAL_PRODUCTORS-1)) {
							flag = false;
							mProsumer.bStopCustomFlag = true;
							// �ͷ����������߳�
							mProsumer.releaseExistSingal();
						}
					} else {
						System.out.println("---" + name + "�������ѿ�");
						// �����������߳���ֹͣ,��������Ϊ��,���߳�ҲҪֹͣ��
						if (mProsumer.bStopCustomFlag) {
							flag = false;
							break;//û�в����µĿռ�
						}
					}
				}

				// ֪ͨ�������пռ�
				mProsumer.singalEmpty(name);

				// doing other things
				Thread.sleep((long) (Math.random() * 100));

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("---" + name + "��������");
		mProsumer.mLatchDown.countDown();
	}

}
