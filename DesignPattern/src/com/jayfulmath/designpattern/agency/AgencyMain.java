package com.jayfulmath.designpattern.agency;

import com.jayfulmath.designpattern.main.BasicExample;

/*����ģʽ���ŵ㣺���Լ��ٶ��College֮����໥��ϵ�����Զ����ĸı�College ��mediator
 * ȱ�㣺����ConcreateMediator����̫�࣬����Ե͡�
 * 
 * */
public class AgencyMain extends BasicExample {

	@Override
	public void startDemo() {
		UNSC unsc = new UNSC();
		
		USA usa  = new USA(unsc);
		Iraq iraq = new Iraq(unsc);
		unsc.usa = usa;
		unsc.iraq = iraq;
		
		usa.Declare("Do not using neclear weapons");
		iraq.Declare("we have not neclear weapons");
	}

}
