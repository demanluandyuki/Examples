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
		System.out.println("---" + name + "操作開始");
		mProsumer.mLatchStart.countDown();
		while (flag) {
			try {
				// 等待缓冲池有数据
				System.out.println("---" + name + "等待缓冲区数据");
				mProsumer.waitExist(name);

				// 互斥的访问缓冲区
				synchronized (mProsumer.objlock) {
					if (mProsumer.g_produtor.size() > 0) {
						mProductor = mProsumer.g_produtor.poll();
						System.out.println("---" + name + "将数据" + mProductor
								+ "取出缓冲区");
						if (mProductor == (ProsumerExample.TOTAL_PRODUCTORS-1)) {
							flag = false;
							mProsumer.bStopCustomFlag = true;
							// 释放其他消费线程
							mProsumer.releaseExistSingal();
						}
					} else {
						System.out.println("---" + name + "缓冲区已空");
						// 其他消费者线程已停止,缓冲区已为空,此线程也要停止。
						if (mProsumer.bStopCustomFlag) {
							flag = false;
							break;//没有产生新的空间
						}
					}
				}

				// 通知缓存区有空间
				mProsumer.singalEmpty(name);

				// doing other things
				Thread.sleep((long) (Math.random() * 100));

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("---" + name + "操作结束");
		mProsumer.mLatchDown.countDown();
	}

}
