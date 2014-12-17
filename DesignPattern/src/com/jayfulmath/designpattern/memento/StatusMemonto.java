package com.jayfulmath.designpattern.memento;

/*存取的内容，包含很多载体。
 * 
 * */
public class StatusMemonto {
	
	private String _mStatus;

	public StatusMemonto(String status) {
		this._mStatus = status;
	}

	/**
	 * @return the _mStatus
	 */
	public String getStatus() {
		return _mStatus;
	}	
}
