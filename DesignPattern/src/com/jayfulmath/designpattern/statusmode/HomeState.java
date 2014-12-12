package com.jayfulmath.designpattern.statusmode;

public class HomeState implements IState {

	@Override
	public void Work(Context context) {
		if(context.getmHour()<22)
		{
			System.out.println("当前时间:\t"+context.getmHour()+"\t在家休息中。。。");
		}
		else
		{
			context.setState(StateFactory.CrteateState(STATESENUM.STATES_SLEEP));
			context.Request();
		}
	}

}
