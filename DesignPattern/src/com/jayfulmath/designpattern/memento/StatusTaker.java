package com.jayfulmath.designpattern.memento;

/*StatusTaker 只能存取Memonto，不能修改memonto的内容。
 * 
 * 
 * */
public class StatusTaker {
	
	private StatusMemonto _mMemonto;

	/**
	 * @return the _mMemonto
	 */
	public StatusMemonto get_mMemonto() {
		return _mMemonto;
	}

	/**
	 * @param _mMemonto the _mMemonto to set
	 */
	public void set_mMemonto(StatusMemonto _mMemonto) {
		this._mMemonto = _mMemonto;
	}
	
	
}
