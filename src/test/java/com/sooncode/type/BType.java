package com.sooncode.type;

import org.springframework.stereotype.Component;

@Component
public class BType  extends AType {

	public String getTypeCode() {
		return "B";
	}

	@Override
	public int getNumber() {
		 
		return 2;
	}
	
	

}
