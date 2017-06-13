package com.sooncode.soonjdbc.sql.condition.sign;

import com.sooncode.soonjdbc.sql.condition.Condition;
import com.sooncode.soonjdbc.sql.condition.EqualCondition;

public class EqualSign extends Sign {
	
	public static final EqualSign EQ = new EqualSign("=");
	/**
	 * 大于（>）
	 */
	public static final EqualSign GT = new EqualSign(">");
	/**
	 * 大于等于 （>=）
	 */
	public static final EqualSign GT_EQ = new EqualSign(">=");
	/**
	 * 小于（<）
	 */
	public static final EqualSign LT = new EqualSign("<");

	/**
	 * 小于等于（<=）
	 */
	public static final EqualSign LT_EQ = new EqualSign("<=");

	/**
	 * 不等于 （<>）
	 */
	public static final EqualSign NOT_EQ = new EqualSign("<>");

	
	private EqualSign (String signStr){
		this.signStr = signStr;
	}
	@Override
	public Condition getCondition() {
		return new EqualCondition();
	}

}
