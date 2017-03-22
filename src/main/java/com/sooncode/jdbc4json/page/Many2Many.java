package com.sooncode.jdbc4json.page;

 

public class Many2Many<L,M,R> implements Result{
	private L one;
	private  One2One<M,R> many;
	public L getLeft() {
		return one;
	}
	public void setLeft(L one) {
		this.one = one;
	}
	public One2One<M, R> getMany() {
		return many;
	}
	public void setMany(One2One<M, R> many) {
		this.many = many;
	}
	 
}