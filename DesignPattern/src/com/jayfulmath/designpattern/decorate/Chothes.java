package com.jayfulmath.designpattern.decorate;

public abstract class Chothes implements IComponent {
	protected IComponent _mComponent = null;
	
	public void show()
	{
		if(_mComponent!=null)
		{
			_mComponent.show();
		}
	}
	
	public void decorate(IComponent comp)
	{
		_mComponent = comp;
	}
}
