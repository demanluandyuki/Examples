package com.jayfulmath.designpattern.experssion;

import java.util.HashMap;

public class VarExperssion extends Experssion {

	private String key;
	@Override
	public int interpreter(HashMap<String, Integer> var) {
		System.out.println("VarExperssion interpreter key:"+key);
		return var.get(key);
	}
	public VarExperssion(String key) {
		super();
		this.key = key;
	}
	
	
}
