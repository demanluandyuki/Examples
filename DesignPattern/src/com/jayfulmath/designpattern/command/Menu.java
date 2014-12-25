package com.jayfulmath.designpattern.command;

public class Menu {
	private int _mCustomId;
	private int _mMeat;
	private int _mVegetable;
	private int _mFruit;
		
	public Menu(int _mMeat, int _mVegetable, int _mFruit, int _mCustomId) {
		this._mMeat = _mMeat;
		this._mVegetable = _mVegetable;
		this._mFruit = _mFruit;
		this._mCustomId = _mCustomId;
	}

	/**
	 * @return the _mMeat
	 */
	public int get_mMeat() {
		return _mMeat;
	}

	/**
	 * @return the _mVegetable
	 */
	public int get_mVegetable() {
		return _mVegetable;
	}

	/**
	 * @return the _mFruit
	 */
	public int get_mFruit() {
		return _mFruit;
	}
	
	/**
	 * @return the _mCustomId
	 */
	public int get_mCustomId() {
		return _mCustomId;
	}
	
	public void delMeat()
	{
		_mMeat = 0;
	}
	
	public void delFruit()
	{
		_mFruit = 0;
	}
	
	public void delVegetable()
	{
		_mVegetable = 0;
	}
	
	public boolean isFinished()
	{
		boolean result = false;
		if((_mMeat ==0) && (_mFruit ==0) && (_mVegetable == 0))
		{
			result = true;
		}
		return result;
	}
}
