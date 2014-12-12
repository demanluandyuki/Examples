package com.jayfulmath.designpattern.statusmode;

public class Context {
	private IState state;
	private int mHour;
	
	public Context(STATESENUM state)
	{
		this.state =StateFactory.CrteateState(state);
	}

//	/**
//	 * @return the state
//	 */
//	public IState getState() {
//		return state;
//	}

	/**
	 * @param state the state to set
	 */
	public void setState(IState state) {
		this.state = state;
	}
	
	/**
	 * @return the mHour
	 */
	public int getmHour() {
		return mHour;
	}

	/**
	 * @param mHour the mHour to set
	 */
	public void setmHour(int mHour) {
		this.mHour = mHour;
	}
	
	public void Request()
	{
		state.Work(this);
	}
}
