package com.sooncode.jdbc4json.page;

import java.util.List;

public class One<L> implements Result {
	
	
	
	private List<L> ones;

	public One(List<L> ones){
		this.ones = ones;
	}
	
	
	
	public List<L> getOnes() {
		return ones;
	}

	public void setOnes(List<L> ones) {
		this.ones = ones;
	}

}
