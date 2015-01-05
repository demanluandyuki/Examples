package com.jayfulmath.designpattern.agency;

import com.jayfulmath.designpattern.main.BasicExample;

/*代理模式的优点：可以减少多个College之间的相互关系，可以独立的改变College 和mediator
 * 缺点：就是ConcreateMediator责任太多，耦合性低。
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
