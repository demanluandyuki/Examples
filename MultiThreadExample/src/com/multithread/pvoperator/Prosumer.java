package com.multithread.pvoperator;

public class Prosumer {
	//PV 分析 生产者，消费者问题
	/*同步：	生产者：缓冲区有空间，就放入数据  P(EmptyS) 只有空和不空，信号量为1
	 *    	消费者：缓冲区有数据，就读取数据，并移走数据 P(NotEmptyS)，信号量为缓冲区大小
	 *互斥：	生产者 写入数据，和消费者移走数据互斥 P(OperatorS)，用来互斥，信号量为1
	 *		消费者异步读取移动数据，互斥	  	
	 * */
//	public class Productor extends Thread{
//
//		@Override
//		public void run() {
//			while(true)
//			{
//				P(EmptyS);
//				P(OperatorS);
//				//operator data
//				V(OperatorS);
//				V(NotEmptyS);//通知不为空
//			}
//		}
//		
//		
//	}
//	
//	public class Consumer extends Thread{
//
//		@Override
//		public void run() {
//			P(NotEmptyS);
//			P(OperatorS);
//			//operator data
//			V(OperatorS);
//			V((EmptyS);
//		}
//		
//	}
}
