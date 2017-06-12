package com.sooncode.soonjdbc.sql.condition.sign;

import com.sooncode.soonjdbc.sql.condition.Condition;

/**
 * 符号
 * 
 * @author pc
 *
 */
public abstract class Sign {
    
	 
	protected String signStr;
	
	public String toString(){
		return signStr;
	}
	
	public Sign(String signStr){
		this.signStr= signStr;
	}
	protected Sign(){
		
	}
	
	public abstract Condition getCondition();

}
