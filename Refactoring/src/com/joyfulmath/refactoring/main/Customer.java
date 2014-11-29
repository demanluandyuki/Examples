package com.joyfulmath.refactoring.main;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
	private String _name;
	private Vector _rentals = new Vector();

	public Customer(String _name) {
		this._name = _name;
	}

	public String get_name() {
		return _name;
	}

	public void addRental(Rental arg) {
		_rentals.addElement(arg);
	}

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for " + get_name() + "\n";

		while (rentals.hasMoreElements()) {
			Rental each = (Rental) rentals.nextElement();
			
			
			frequentRenterPoints = each.getFrequentRenterPoints();

			result += "\t" + each.get_movie().get_title() + "\t"
					+ String.valueOf(each.getCharge()) + "\n";
			totalAmount += each.getCharge();
		}

		result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
		result += "You earned" + frequentRenterPoints
				+ " frequent renter points";
		return result;
	}
}
