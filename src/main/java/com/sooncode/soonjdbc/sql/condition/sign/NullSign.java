package com.sooncode.soonjdbc.sql.condition.sign;

import com.sooncode.soonjdbc.sql.condition.Condition;
import com.sooncode.soonjdbc.sql.condition.NullCondition;

/**
 * 模糊匹配
 * @author pc
 *
 */
public class NullSign extends Sign  {

	/**
	 * IS NULL 
	 */
	public static final NullSign IS_NULL = new NullSign(" IS NULL ");
	/**
	 * IS NOT NULL
	 */
	public static final NullSign IS_NOT_NULL = new NullSign(" IS NOT NULL ");
	 
	
	private NullSign(String signStr){
		this.signStr = signStr;
	}


	@Override
	public Condition getCondition() {
		 
		return new NullCondition();
	}
}
