package com.sooncode.soonjdbc.page;

import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;

public class One2Many  {
	private DbModel one;
	private List<DbModel> many;

	public One2Many(){
		
	}
	
	
	public One2Many(DbModel one,List<DbModel> many){
		this.one = one;
		this.many = many;
				
	}


	public DbModel getOne() {
		return one;
	}


	public void setOne(DbModel one) {
		this.one = one;
	}


	public List<DbModel> getMany() {
		return many;
	}


	public void setMany(List<DbModel> many) {
		this.many = many;
	}
	
	
	
	 
}