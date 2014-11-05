package com.multithread.main.sampleExample;

import com.multithread.main.ExampleInterface;

public class SampleExample extends ExampleInterface {

	final int MAX_THREAD_NUM = 10;
	Integer g_num = 0;

	@Override
	public void startDemo() {
		Persion p = new Persion(0);
		while (p.index < MAX_THREAD_NUM) {
			Thread t = new SampleMultiThread(p);
			t.start();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.index++;
		}
	}

	class SampleMultiThread extends Thread {

		Persion p =null;

		public SampleMultiThread(Persion p) {
			this.p = p;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				synchronized (g_num) {
					Thread.sleep(100);
					g_num++;
					System.out.println("g_num is:" + g_num + "\t" + "index is:"
							+ this.p.index);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	class Persion{
		public int index = -1;
		public Persion(int i)
		{
			index = i;
		}
	}
}
