package com.jayfulmath.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Boss implements ISubject {
	
	List<Observer> _mObservers = new ArrayList<Observer>();	
	public String subjectStatus;
	
	public void Attach(Observer obs)
	{
		_mObservers.add(obs);
	}
	
	public void Detach(Observer obs)
	{
		_mObservers.remove(obs);
	}
	
	public void Broadcast()
	{
		//here may be asynchronous to notify observer
		for(Observer observer :_mObservers)
		{
			observer.Update();
		}
	}
	
	/**
	 * @return the subjectStatus
	 */
	public String getSubjectStatus() {
		return subjectStatus;
	}

	/**
	 * @param subjectStatus the subjectStatus to set
	 */
	public void setSubjectStatus(String subjectStatus) {
		this.subjectStatus = subjectStatus;
	}
}
