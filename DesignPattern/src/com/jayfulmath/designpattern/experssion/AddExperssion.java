package com.jayfulmath.designpattern.experssion;

import java.util.HashMap;

public class AddExperssion extends SymbolExperssion {

	public AddExperssion(Experssion left, Experssion right) {
		super(left, right);
		
	}

	@Override
	public int interpreter(HashMap<String, Integer> var) {
		return super.left.interpreter(var)+super.right.interpreter(var);
	}
	
}
