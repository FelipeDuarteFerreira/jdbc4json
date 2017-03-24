package com.sooncode.soonjdbc.page;

import java.util.List;

public class Many2Many<L, M, R> {
	private L one;
	private List<One2One> many;

	public L getOne() {
		return one;
	}

	public void setOne(L one) {
		this.one = one;
	}

	public List<One2One> getMany() {
		return many;
	}

	public void setMany(List<One2One> many) {
		this.many = many;
	}

}