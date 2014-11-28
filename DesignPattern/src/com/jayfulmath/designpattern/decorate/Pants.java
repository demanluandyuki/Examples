package com.jayfulmath.designpattern.decorate;

public class Pants extends Chothes {

	/* (non-Javadoc)
	 * @see com.jayfulmath.designpattern.decorate.Chothes#show()
	 */
	@Override
	public void show() {
		System.out.print("Î÷¿ã\t");
		super.show();
	}
	
}
