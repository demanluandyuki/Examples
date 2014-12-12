package com.jayfulmath.designpattern.statusmode;

public class SleepState implements IState {

	@Override
	public void Work(Context context) {

		if(context.getmHour()<9 || context.getmHour()>=22)
		{
			System.out.println("��ǰʱ��:\t"+context.getmHour()+"\t˯���С�����");
		}
		else
		{
			context.setState(StateFactory.CrteateState(STATESENUM.STATES_WORK));
			context.Request();
		}
	}

}
