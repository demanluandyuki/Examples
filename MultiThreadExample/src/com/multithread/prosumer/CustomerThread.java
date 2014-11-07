package com.multithread.prosumer;

public class CustomerThread extends Thread {

	final int END_PRODUCE_NUMBER = 10; // 生产产品个数
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
		System.out.println("---"+name+"操作開始");
		mProsumer.mLatchStart.countDown();
		while (flag) {
			try {
				// 等待缓冲池有数据
				System.out.println("---"+name+"等待缓冲区数据");
				mProsumer.waitExist();

				// 互斥的访问缓冲区
				synchronized (mProsumer.objlock) {
//					mProductor = mProsumer.g_produtor;
					System.out.println("---"+name+"将数据" + mProductor + "取出缓冲区");
					if (mProductor == END_PRODUCE_NUMBER) {
						flag = false;
					}
				}

				// 通知缓存区已空
				mProsumer.singalEmpty();

				// doing other things
				Thread.sleep(100);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}
		System.out.println("---消费者操作结束");
		mProsumer.mLatchDown.countDown();
	}

}
