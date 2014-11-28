package com.jayfulmath.designpattern.decorate;

public class Tie extends Chothes {

	/* (non-Javadoc)
	 * @see com.jayfulmath.designpattern.decorate.Chothes#show()
	 */
	@Override
	public void show() {
		System.out.print("Áì´ø\t");
		super.show();
	}
	
}
