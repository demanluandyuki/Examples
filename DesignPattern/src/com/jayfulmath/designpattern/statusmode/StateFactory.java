package com.jayfulmath.designpattern.statusmode;

public class StateFactory {
	
	public static IState CrteateState(STATESENUM state)
	{
		IState workstate = null;
		switch(state)
		{
		case STATES_SLEEP:
			workstate = new SleepState();
			break;
		case STATES_WORK:
			workstate = new WorkState();
			break;
		case STATES_REST_AT_HOME:
			workstate = new HomeState();
			break;		
		default:
			break;
		}
		
		return workstate;
	}
}
