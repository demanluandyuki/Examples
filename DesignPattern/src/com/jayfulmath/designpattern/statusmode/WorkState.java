package com.jayfulmath.designpattern.statusmode;

public class WorkState implements IState {

	@Override
	public void Work(Context context) {
		if(context.getmHour()<18)
		{
			System.out.println("��ǰʱ��:\t"+context.getmHour()+"\t�����С�����");
		}
		else
		{
			context.setState(StateFactory.CrteateState(STATESENUM.STATES_REST_AT_HOME));
			context.Request();
		}
	}

}
