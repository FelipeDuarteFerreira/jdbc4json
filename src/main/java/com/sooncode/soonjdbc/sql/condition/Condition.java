package com.sooncode.soonjdbc.sql.condition;

import java.util.Arrays;

/**
 * 查询条件 最小单位
 * @author pc
 *
 */
 public abstract class  Condition {
    /**
     * 字段(属性) 
     */
	protected String key ;
	
	/**
	 * 条件对应的值
	 */
	protected Object val;
	/**
	 *  条件对应的值(数组)
	 */
	protected Object [] values;
	
	/**
	 * 条件使用的符号
	 */
	protected String conditionSign;
 
	
	protected Object other;
	
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}

	public String getConditionSign() {
		return conditionSign;
	}

	public void setConditionSign(String conditionSign) {
		this.conditionSign = conditionSign;
	}

	public Condition() {
		 
	}
	public Condition(String key, Object val, String conditionSign) {
		super();
		this.key = key;
		this.val = val;
		this.conditionSign = conditionSign;
	}
	public Condition(String key, Object[] values, String conditionSign) {
		super();
		this.key = key;
		this.values = values;
		this.conditionSign = conditionSign;
	}

	 
	 

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}

	 
	public abstract SqlAndParameter getSqlSlice () ;

	@Override
	public String toString() {
		return "Condition [key=" + key + ", val=" + val + ", values=" + Arrays.toString(values) + ", conditionSign=" + conditionSign + ", other=" + other + "]";
	}
	
	
}
