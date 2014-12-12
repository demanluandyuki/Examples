package com.jayfulmath.designpattern.statusmode;

public class HomeState implements IState {

	@Override
	public void Work(Context context) {
		if(context.getmHour()<22)
		{
			System.out.println("��ǰʱ��:\t"+context.getmHour()+"\t�ڼ���Ϣ�С�����");
		}
		else
		{
			context.setState(StateFactory.CrteateState(STATESENUM.STATES_SLEEP));
			context.Request();
		}
	}

}
