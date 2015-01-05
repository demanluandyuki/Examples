package com.jayfulmath.designpattern.job;

public interface IManager {
	void setSuccesser(IManager next);
	void HandleRequest(int day);
}
