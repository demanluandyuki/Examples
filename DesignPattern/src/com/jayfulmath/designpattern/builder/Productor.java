package com.jayfulmath.designpattern.builder;

public class Productor {
	
	private String partA;
	private String partB;
	private String partC;
	/**
	 * @param partA the partA to set
	 */
	public void setPartA(String partA) {
		this.partA = partA;
	}
	/**
	 * @param partB the partB to set
	 */
	public void setPartB(String partB) {
		this.partB = partB;
	}
	/**
	 * @param partC the partC to set
	 */
	public void setPartC(String partC) {
		this.partC = partC;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (partA+"\t"+partB+"\t"+partC);
	}
	
	
}
