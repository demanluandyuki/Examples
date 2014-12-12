package com.jayfulmath.designpattern.statusmode;

public class WorkState implements IState {

	@Override
	public void Work(Context context) {
		if(context.getmHour()<18)
		{
			System.out.println("当前时间:\t"+context.getmHour()+"\t工作中。。。");
		}
		else
		{
			context.setState(StateFactory.CrteateState(STATESENUM.STATES_REST_AT_HOME));
			context.Request();
		}
	}

}
