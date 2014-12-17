package com.jayfulmath.designpattern.memento;

public class Player {
	
	private String status;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}	
	
	public StatusMemonto CreateMemontoStatus()
	{
		return new StatusMemonto(status);
	}
	
	public void setMemonto(StatusMemonto memoto){
		this.status = memoto.getStatus();
	}
}
