package com.jayfulmath.designpattern.experssion;

import java.util.HashMap;

public class SubExperssion extends SymbolExperssion {

	public SubExperssion(Experssion left, Experssion right) {
		super(left, right);
		
	}

	@Override
	public int interpreter(HashMap<String, Integer> var) {
		return super.left.interpreter(var) - super.right.interpreter(var);
	}

}
