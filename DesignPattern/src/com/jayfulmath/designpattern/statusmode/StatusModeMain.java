package com.jayfulmath.designpattern.statusmode;

import com.jayfulmath.designpattern.main.BasicExample;

public class StatusModeMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		Context mContext = new Context(STATESENUM.STATES_SLEEP);
		mContext.setmHour(8);
		mContext.Request();
		
		mContext.setmHour(10);
		mContext.Request();
		
		mContext.setmHour(12);
		mContext.Request();
		
		mContext.setmHour(15);
		mContext.Request();
		
		
		mContext.setmHour(19);
		mContext.Request();
		
		mContext.setmHour(22);
		mContext.Request();
	}

}
