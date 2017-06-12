package com.sooncode.soonjdbc.sql.condition.sign;

import com.sooncode.soonjdbc.sql.condition.Condition;
import com.sooncode.soonjdbc.sql.condition.LikeCondition;

/**
 * 模糊匹配
 * @author pc
 *
 */
public class LikeSign extends Sign {

	/**
	 * 模糊匹配    "%XXX%"
	 */
	public static final LikeSign LIKE = new LikeSign("LIKE");
	/**
	 * 右模糊匹配    "%XXX"
	 */
	public static final LikeSign R_LIKE = new LikeSign("R_LIKE");
	/**
	 * 左模糊匹配    "XXX%"
	 */
	public static final LikeSign L_LIKE = new LikeSign("L_LIKE");
	
	private LikeSign(String signStr){
		this.signStr = signStr;
	}
	
	@Override
	public Condition getCondition() {
		return new LikeCondition();
	}

}
