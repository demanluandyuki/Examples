package com.jayfulmath.designpattern.statusmode;

public class SleepState implements IState {

	@Override
	public void Work(Context context) {

		if(context.getmHour()<9 || context.getmHour()>=22)
		{
			System.out.println("当前时间:\t"+context.getmHour()+"\t睡觉中。。。");
		}
		else
		{
			context.setState(StateFactory.CrteateState(STATESENUM.STATES_WORK));
			context.Request();
		}
	}

}
