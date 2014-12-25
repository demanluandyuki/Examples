package com.jayfulmath.designpattern.command;

import java.util.List;

import com.jayfulmath.designpattern.command.GlobalValue.MENUTYPE;
import com.jayfulmath.designpattern.command.cooker.ISubject;
import com.jayfulmath.designpattern.command.cooker.Observer;

public class Customer extends Observer {
	private int id;
	private Menu _mMenu = null;
	private String name;
	private Object objlock = new Object();

	public Customer(String name, int id, List<ISubject> sub) {
		super(name, sub);
		this.name = name;
		this.id = id;
	}

	public void generateMenu() {
		int meat = (int) (Math.random() * 10);
		meat = meat % 2;

		int vegetable = (int) (Math.random() * 10);
		vegetable = vegetable % 2;

		int fruit = 1;

		_mMenu = new Menu(meat, vegetable, fruit, id);
		System.out.println(name + " order menu " + "\tmeat:" + meat
				+ "\tvegetable:" + vegetable + "\tfruit:" + fruit
				+ "\tcustomerId:" + id);
	}

	/**
	 * @return the _mMenu
	 */
	public Menu get_mMenu() {
		return _mMenu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Customer) {
			Customer c = (Customer) obj;
			if (c.id == this.id) {
				result = true;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public void Update(MENUTYPE type, int customerid) {
		synchronized (objlock) {
			if (customerid == this.id) {
				// notify for current is OK.
				switch (type) {
				case FRUIT_TYPE:
					if (_mMenu.get_mFruit() > 0) {
						System.out.println(name + " Start eat Fruit ");
						_mMenu.delFruit();
					}
					break;
				case MEAT_TYPE:
					if (_mMenu.get_mMeat() > 0) {
						System.out.println(name + "Start eat Meat");
						_mMenu.delMeat();
					}
					break;
				case VEGETABLE_TYPE:
					if (_mMenu.get_mVegetable() > 0) {
						System.out.println(name + " Start eat Vegetable");
						_mMenu.delVegetable();
					}
					break;
				}

				if (_mMenu.isFinished()) {
					System.out.println(name + " has finished launch!");
					GlobalValue._mCountDownCustom.countDown();
				}
			}
		}

	}

}
