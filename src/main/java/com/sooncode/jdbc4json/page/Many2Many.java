package com.sooncode.jdbc4json.page;

 

public class Many2Many<L,M,R> implements Result{
	private L one;
	private  One2One<M,R> many;
	 
	public One2One<M, R> getMany() {
		return many;
	}
	public void setMany(One2One<M, R> many) {
		this.many = many;
	}
	public L getOne() {
		return one;
	}
	public void setOne(L one) {
		this.one = one;
	}
	 
	
}