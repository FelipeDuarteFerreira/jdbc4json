package com.sooncode.springJdbc_test;

public class Sql implements SqlInterface {

	private String sql;
	
	public Sql (String sql){
		this.sql = sql;
	}
	
	
	@Override
	public String getSql() {
		 
		return this.sql;
	}

}
