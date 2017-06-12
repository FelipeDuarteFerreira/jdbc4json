package com.sooncode.soonjdbc.sql.condition.sign;

import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.sql.condition.BetweenCondition;
import com.sooncode.soonjdbc.sql.condition.Condition;

public class BetweenSign extends Sign {
	public static final BetweenSign BETWEEN_AND = new BetweenSign(SQL_KEY.BETWEEN);
	public static final BetweenSign NOT_BETWEEN_AND = new BetweenSign(SQL_KEY.NOT_BETWEEN);
	private BetweenSign(String signStr){
		this.signStr = signStr;
	}

	@Override
	public Condition getCondition() {
		return new BetweenCondition();
	}

}
