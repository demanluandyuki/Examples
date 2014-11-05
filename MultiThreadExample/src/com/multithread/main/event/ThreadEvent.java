package com.multithread.main.event;

import java.util.EventObject;

public class ThreadEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8151338897105765563L;
	private String operatorStr  = "";

	public ThreadEvent(Object arg0, String action) {
		super(arg0);
		this.operatorStr = action;
	}

	public void setOperator(String action){
		this.operatorStr = action;	
	}
	
	public String getOperator(){
		return this.operatorStr;
	}
}
