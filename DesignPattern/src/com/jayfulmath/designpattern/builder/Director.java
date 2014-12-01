package com.jayfulmath.designpattern.builder;

public class Director {
	private IBuilder builder;

	public Director(IBuilder mBuilder) {
		builder = mBuilder;
	}

	public void construct() {
		builder.buildA();
		builder.buildB();
		builder.buildC();
	}
}
