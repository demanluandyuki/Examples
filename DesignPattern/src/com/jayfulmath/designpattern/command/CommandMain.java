package com.jayfulmath.designpattern.command;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.jayfulmath.designpattern.main.BasicExample;

/*����ģʽ��	1.�˿�֪ͨ����Ա��ˣ������˵����Է������ȵȣ�����ʦ�ڿͻ��Ƿ�յģ���ʦֻ���˵�����
 *			2.��ʦ���ò��Ժ�ֻ��֪ͨ����ԱBȥ���ͻ��ϲˡ�
 *��demoģ��ͻ���ˣ�����ʦ���ˣ��Լ��ͻ��Ժ��뿪Ϊ���̡� 			
 * */
public class CommandMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		OrderWaiter waiter = new OrderWaiter("waiter");		
		Executor mExecutor = Executors.newFixedThreadPool(10);
		mExecutor.execute(waiter);
		waiter.executeCookerThread(mExecutor);
		
		Customer deman = new Customer("deman",1,waiter.getmListSub());
		Customer yuki = new Customer("yuki",2,waiter.getmListSub());
		GlobalValue._mCountDownCustom = new CountDownLatch(2);
		
		try {
			GlobalValue._mCountDown.await();
			System.out.println("All thread is running,count:"+GlobalValue._mCountDown.getCount());
			System.out.println("main thread id:"+Thread.currentThread().getId());
			deman.generateMenu();
			yuki.generateMenu();
			waiter.customerOrderMenu(deman);
			waiter.customerOrderMenu(yuki);
			
			
			GlobalValue._mCountDownCustom.await();//�ȴ��ͻ��뿪
			waiter.stopOrderWaiting();
			
			GlobalValue._mCountDownEnd.await();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Demo is end");
	}

}
