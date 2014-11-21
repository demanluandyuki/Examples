package com.multithread.readwritelock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.multithread.main.ExampleInterface;
import com.multithread.readwrite.WriteThread;

public class ReadWriteLockExample extends ExampleInterface {

	public ReadWriteLock mReadwriteLock = new ReentrantReadWriteLock(false);
	public File mFile = null;
	public int mCash = 10000;
	public CountDownLatch mLatchDown = new CountDownLatch(6);
	private boolean mStopedFlag = false;

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		mFile = new File("H:\\Project\\SST\\123.txt");
		try {

			mFile.createNewFile();
			Executor mEcecutor = Executors.newFixedThreadPool(2 + 4);
			mEcecutor.execute(new Reader("Reader1"));
			mEcecutor.execute(new Reader("Reader2"));
			mEcecutor.execute(new Reader("Reader3"));
			mEcecutor.execute(new Reader("Reader4"));
			mEcecutor.execute(new Writer("Writer1", 2000));
			mEcecutor.execute(new Writer("Writer2", -3000));

			mLatchDown.await();
			System.out.println("[startDemo]" + "Demo down");

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class Reader extends Thread {

		public String name = null;
		boolean flag = true;
		private int index = 0;

		public Reader(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			while (flag) {
				try {
					mReadwriteLock.readLock().lock();
					System.out.println("[Reader]" + name + "start");
					Thread.sleep((long) (Math.random() * 100));
					if (!mStopedFlag) {
						System.out.println("[Reader]" + name + "get mcash now:"
								+ mCash);
					} else {
						flag = false;
					}
					Thread.sleep((long) (Math.random() * 100));
					System.out.println("[Reader]" + name + "down");
					mReadwriteLock.readLock().unlock();
					Thread.sleep((long) (Math.random() * 1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
			mLatchDown.countDown();

		}

	}

	public class Writer extends Thread {

		public String name = null;
		boolean flag = true;
		private int index = 0;
		private int cash = 0;

		public Writer(String name, int addcash) {
			this.name = name;
			this.cash = addcash;
		}

		@Override
		public void run() {
			System.out.println("[Writer]" + name + "start");
			while (flag) {

				try {
					mReadwriteLock.writeLock().lock();
					System.out.println("[Writer]" + name + "start");
					Thread.sleep((long) (Math.random() * 100));
					int oldcash = mCash;
					if (mCash <= 0) {
						flag = false;
						mStopedFlag = true;
					} else {
						mCash += cash;
						System.out.println("[Writer]" + name
								+ "operator cash old:" + oldcash + " To:"
								+ mCash);
					}
					Thread.sleep((long) (Math.random() * 100));
					System.out.println("[Writer]" + name + "down");
					mReadwriteLock.writeLock().unlock();
					Thread.sleep((long) (Math.random() * 1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			mLatchDown.countDown();
		}

	}

}
