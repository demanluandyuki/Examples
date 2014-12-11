package com.jayfulmath.designpattern.observer;

public interface ISubject {
	public void Attach(Observer obs);
	public void Detach(Observer obs);
	public void Broadcast();
	public String getSubjectStatus();
	public void setSubjectStatus(String subjectStatus);
}
