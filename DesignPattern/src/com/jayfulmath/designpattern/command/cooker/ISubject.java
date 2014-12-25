package com.jayfulmath.designpattern.command.cooker;

public interface ISubject {
	public void Attach(Observer obs);
	public void Detach(Observer obs);
	public void Broadcast(int customId);
}
