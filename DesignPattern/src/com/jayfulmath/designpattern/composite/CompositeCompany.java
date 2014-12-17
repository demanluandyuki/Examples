package com.jayfulmath.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeCompany extends Company {

	private List<Company> children = new ArrayList<Company>();
	
	public CompositeCompany(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		children.clear();
	}

	@Override
	public void Add(Company c) {
		// TODO Auto-generated method stub
		children.add(c);
	}

	@Override
	public void Remove(Company c) {
		// TODO Auto-generated method stub
		children.remove(c);
	}

	@Override
	public void Display(int depth) {
		// TODO Auto-generated method stub
		printfObjectName(depth);		
		for(Company c :children)
		{
			c.Display(depth+2);
		}
	}

	@Override
	public void LineofDuty() {
		// TODO Auto-generated method stub
		for(Company compont :children)
		{
			compont.LineofDuty();
		}
	}

}
