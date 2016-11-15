package com.sooncode.jdbc4json.sql.condition.sign;

 
 

/**
 * 符号
 * 
 * @author pc
 *
 */
public  class Sign {
    
	 
	private String signStr;
	
	public String toString(){
		return signStr;
	}
	
	public Sign(String signStr){
		this.signStr= signStr;
	}
	protected Sign(){
		
	}

}
