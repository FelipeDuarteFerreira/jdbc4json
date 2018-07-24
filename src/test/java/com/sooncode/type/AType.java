package com.sooncode.type;

import org.springframework.stereotype.Component;

@Component
public class AType implements Type {

	public String getTypeCode() {
		 
		return "A";
	}

	public int getNumber() {
		return 1;
	}

}
