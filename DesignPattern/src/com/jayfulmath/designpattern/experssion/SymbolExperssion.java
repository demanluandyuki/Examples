package com.jayfulmath.designpattern.experssion;


public abstract class  SymbolExperssion extends Experssion {

	Experssion left;
	Experssion right;
	public SymbolExperssion(Experssion left, Experssion right) {
		super();
		this.left = left;
		this.right = right;
	}
	
}
