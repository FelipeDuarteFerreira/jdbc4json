package com.sooncode.jdbc4json.page;

import java.util.LinkedList;
import java.util.List;

 
public class One2One<L,R> implements Result {

	private List<LeftAndRight<L, R>> leftAndRights = new LinkedList<>();

	public void add(L left, R right) {
		LeftAndRight<L, R> lar = new LeftAndRight<>(left, right);
		leftAndRights.add(lar);
	}

	public List<L> getLefts() {
		List<L> list = new LinkedList<>();
		for (int i = 0; i < this.size(); i++) {
			list.add(this.getLeft(i));
		}
		return list;
	}

	public List<R> getRights() {
		List<R> list = new LinkedList<>();
		for (int i = 0; i < this.size(); i++) {
			list.add(this.getRight(i));
		}
		return list;
	}

	public L getLeft(int index) {
		return leftAndRights.get(index).getLeft();
	}

	public R getRight(int index) {
		return leftAndRights.get(index).getRight();
	}

	public int size() {
		return leftAndRights.size();
	}

}

