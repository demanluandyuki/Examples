package com.jayfulmath.designpattern.decorate;

public class LeatherShoes extends Chothes {

	/* (non-Javadoc)
	 * @see com.jayfulmath.designpattern.decorate.Chothes#show()
	 */
	@Override
	public void show() {
		System.out.print("ƤЬ\t");
		super.show();
	}
	
}
