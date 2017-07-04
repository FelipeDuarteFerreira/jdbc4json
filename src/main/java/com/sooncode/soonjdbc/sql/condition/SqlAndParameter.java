package com.sooncode.soonjdbc.sql.condition;

import java.util.LinkedList;
import java.util.List;

public class SqlAndParameter {
	private String sqlSlice;

	private List<Object> values = new LinkedList<Object>();

	public String getSqlSlice() {
		return this.sqlSlice;
	}

	public void setSqlSlice(String sqlSlice) {
		this.sqlSlice = sqlSlice;
	}

	public List<Object> getValues() {
		return values;
	}

	public void addValue(Object value) {
		this.values.add(value);
	}

	public void addValues(List<Object> values) {
		this.values.addAll(values);
	}

}
