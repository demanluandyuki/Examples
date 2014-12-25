package com.jayfulmath.designpattern.command;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.jayfulmath.designpattern.main.BasicExample;

/*命令模式：	1.顾客通知服务员点菜，撤销菜单，吃饭结束等等，而厨师于客户是封闭的，厨师只看菜单做菜
 *			2.厨师做好菜以后，只需通知服务员B去给客户上菜。
 *本demo模拟客户点菜，到厨师做菜，以及客户吃好离开为过程。 			
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
			
			
			GlobalValue._mCountDownCustom.await();//等待客户离开
			waiter.stopOrderWaiting();
			
			GlobalValue._mCountDownEnd.await();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Demo is end");
	}

}
