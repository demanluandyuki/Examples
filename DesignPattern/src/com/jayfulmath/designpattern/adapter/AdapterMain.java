package com.jayfulmath.designpattern.adapter;

import com.jayfulmath.designpattern.main.BasicExample;

/*Client��Ҫ����Target�ӿڻ�����࣬����providerֻ���ṩadaptee���͵Ľӿڡ�
 *���ʱ����Ҫһ��adapter��ת���ӿڣ�����ӦTarget��ʹ�á�
 * 
 * */
public class AdapterMain extends BasicExample {

	@Override
	public void startDemo() {
		Adapter ada = new Adapter();
		Client c = new Client(ada);
		c.doAction();
	}

}
