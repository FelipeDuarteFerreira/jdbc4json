package com.sooncode.soonjdbc.page;

import java.util.HashMap;
import java.util.Map;

import com.sooncode.soonjdbc.util.DbModel;


 
public class One2One  {

	private Map<Class<?>  ,DbModel> map = new HashMap<Class<?>, DbModel>();
	
	 
	public DbModel getOne (Class<? extends DbModel> clas ){
		 
		return   map.get(clas);
	}
	
	public void add(DbModel dbModel ){
		
		map.put(dbModel.getClass(), dbModel);
		
	}
	
	public int size(){
		return map.size();
	}
	
	
}
