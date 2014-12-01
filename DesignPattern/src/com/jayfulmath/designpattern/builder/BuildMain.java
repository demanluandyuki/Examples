package com.jayfulmath.designpattern.builder;

import com.jayfulmath.designpattern.main.BasicExample;


/*���ڹ������̣�����ʹ��Director���̶�
 *����ʵ����ConcreateBuilder and  ConcreateBuilderB, ����ʹ�ó��󹤳�ģʽ�ķ�ʽ���з�װ.
 * 
 * */
public class BuildMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		IBuilder builder = BuildFactory.operator();
		Director mDirector = new Director(builder);
		mDirector.construct();
		Productor mProductor = builder.getResult();
		System.out.println("productor:"+mProductor.toString());
	}

}
