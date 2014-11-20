package com.multithread.readwrite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.multithread.main.ExampleInterface;
import com.multithread.prosumer.ProducerThread;

/**
 * 1.写者要等待所有读者读完才能继续写入。
 * 2.所有读者要等待写者写完才能继续读取。
 * 3.读者这件可以共享读取文件。
 * 
 *   
 * 
 * */
public class ReaderWriterExample extends ExampleInterface {
	public static final int READ_THREAD_SIZE = 4;
	public static final int BUFFER_LENGTH = 100;
	
	public List<Integer> g_productor = new ArrayList<Integer>();
	
	public CountDownLatch mReaderLatch = new CountDownLatch(READ_THREAD_SIZE);
	public Semaphore mWriteSema = new Semaphore(READ_THREAD_SIZE);
	
	public boolean bStopFlag = false;
	public CountDownLatch mLatchDown = new CountDownLatch(1+READ_THREAD_SIZE);
	public CountDownLatch mLatchStart = new CountDownLatch(1+READ_THREAD_SIZE);
	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		
		try {
			
			initReadNone();
			bStopFlag = false;
			Executor mEcecutor = Executors.newFixedThreadPool(1+READ_THREAD_SIZE);
			mEcecutor.execute(new WriteThread(this,"Writer"));
			
			for(int i=1;i<=READ_THREAD_SIZE;i++)
			{
				mEcecutor.execute(new ReadThread(this,"Reader"+i));
			}
			
			mLatchStart.await();
			System.out.println("All Thread is runnning");
			
			mLatchDown.await();
			
			System.out.println("All Thread is Down");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void waitReaderEnd()
	{
		//多个read End,using countdownlatch
		try {
			mReaderLatch.await();
			mReaderLatch = new CountDownLatch(READ_THREAD_SIZE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void singalWriteEnd(){
		mWriteSema.release(READ_THREAD_SIZE);
	}
	
	
	
	public void waitWriteEnd(){
		try {
			mWriteSema.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void singalReadEnd(){
		
		mReaderLatch.countDown();
	}
	

	public void initReadNone(){
		try {
			mWriteSema.acquire(READ_THREAD_SIZE);
			for(int i=0;i<READ_THREAD_SIZE;i++)
			{
				mReaderLatch.countDown();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
