package com.jayfulmath.designpattern.observer;

import com.jayfulmath.designpattern.main.BasicExample;

/*�۲���ģʽ��һ�������࣬����״̬�ı��֪ͨ�����۲�����������������⡣
 *observer�����ɹ���ģʽ���߳��󹤳�ģʽ�Ľ� 
 *Subject��һ���ӿڡ�
 *Observer��subject�Ĺ۲���Ϊ����ͬ�ġ�
 * */
public class ObserverMain extends BasicExample {

	@Override
	public void startDemo() {
		ISubject huhanshang = new Boss();
		Observer maweidong = ObserverFactory.Factory("NBALooker", "maweidong", huhanshang);
		Observer zhangdashang = ObserverFactory.Factory("StockLooker", "zhangdashang", huhanshang);
		
		huhanshang.Attach(maweidong);
		huhanshang.Attach(zhangdashang);
		
		huhanshang.setSubjectStatus("I am back! Huhan Shang!");
		huhanshang.Broadcast();
	}

}
