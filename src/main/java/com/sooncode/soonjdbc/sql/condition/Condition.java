package com.sooncode.soonjdbc.sql.condition;

import java.util.LinkedList;
import java.util.List;

/**
 * 查询条件 最小单位
 * 
 * @author hechenwe@gmail.com
 *
 */
public abstract class Condition {
	/**
	 * 字段(属性)
	 */
	protected String key;

	protected List<Object> values = new LinkedList<Object>();

	/**
	 * 条件使用的符号
	 */
	protected String conditionSign;

	protected Object other;

	public abstract SqlAndParameter getSqlSlice();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getConditionSign() {
		return conditionSign;
	}

	public void setConditionSign(String conditionSign) {
		this.conditionSign = conditionSign;
	}

	public Condition() {
	}

	public Condition(String key, Object value, String conditionSign) {
		this.key = key;
		this.values.add(value);
		this.conditionSign = conditionSign;
	}

	public Condition(String key, List<Object> values, String conditionSign) {
		this.key = key;
		this.values.addAll(values);
		this.conditionSign = conditionSign;
	}

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}

	public void addValue(Object value) {
		if (value != null) {
			this.values.add(value);
		}
	}

	public void addValues(List<Object> values) {
		if (values != null) {
			for (Object value : values) {
				this.addValue(value);
			}
		}
	}

	public List<Object> getValues() {
		return values;
	}

}
