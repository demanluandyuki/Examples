package com.jayfulmath.designpattern.decorate;

import com.jayfulmath.designpattern.main.BasicExample;

public class DecorateMain extends BasicExample {

	@Override
	public void startDemo() {
		Person mPerson = new Person("ะกร๗");
		
		Westernstyleclothes wl = new Westernstyleclothes();
		Pants pt = new Pants();
		Tie ti = new Tie();
		LeatherShoes ls = new LeatherShoes();
		
		ti.decorate(mPerson);
		ls.decorate(ti);
		pt.decorate(ls);
		wl.decorate(pt);
		
		wl.show();
	}

}
