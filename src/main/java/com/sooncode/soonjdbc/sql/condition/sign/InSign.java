package com.sooncode.soonjdbc.sql.condition.sign;

import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.sql.condition.Condition;
import com.sooncode.soonjdbc.sql.condition.InCondition;

public class InSign extends Sign{
	/**
	 * IS NULL 
	 */
	public static final InSign IN = new InSign(SQL_KEY.IN);
	/**
	 * IS NOT NULL
	 */
	public static final InSign NOT_IN = new InSign(SQL_KEY.NOT_IN);
	 
	
	private InSign(String signStr){
		this.signStr = signStr;
	}
 
	@Override
	public Condition getCondition() {
		return new InCondition();
	}

}
