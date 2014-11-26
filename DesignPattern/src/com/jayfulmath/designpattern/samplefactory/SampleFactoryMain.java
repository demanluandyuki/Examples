package com.jayfulmath.designpattern.samplefactory;

import com.jayfulmath.designpattern.main.BasicExample;

/*�򵥹���ģʽ.
 *�����в����������һ���࣬Ȼ��ͨ����Ҫͨ����̬��ȷ������Ĳ���
 *Ȼ���Թ���ģʽ�Ĳ�����Щ���� 
 * 
 * 
 * */


public class SampleFactoryMain extends BasicExample {

	@Override
	public void startDemo() {
		//add
		AbstractOperator addop = OperatorFactory.CreateOperator("+");
		if(addop!=null)
		{
			addop.NumberA = 1;
			addop.NumberB = 2;
			System.out.println(addop.NumberA+" add "+addop.NumberB+" = "+addop.GetResult());	
		}
		
		//squar
		AbstractOperator squarop = OperatorFactory.CreateOperator("squar");
		if(squarop!=null)
		{
			squarop.NumberA = 1.2;
			System.out.println(addop.NumberA+" squar " +" = "+squarop.GetResult());
		}
	}

}
