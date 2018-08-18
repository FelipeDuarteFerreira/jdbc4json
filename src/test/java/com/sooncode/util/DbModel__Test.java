package com.sooncode.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sooncode.soonjdbc.entity.SystemUser;
import com.sooncode.soonjdbc.entity.SystemUserDbModel;
import com.sooncode.soonjdbc.util.DbField;

public class DbModel__Test {

	
	public static void main(String[] args) {
		
		SystemUser su = new SystemUser();
		su.setAddress("Address");
		su.setUserId(123);
		
		SystemUserDbModel sudm = new SystemUserDbModel();
		sudm.injectPropertyValue(su);
		List<DbField<?>> fields = sudm.primaryKeys();
		
		Map<String , Object> map = new HashMap<>();
		map.put("userId", 100);
		map.put("sex", "0");
		map.put("name", "hechen");
		
		sudm.injectPropertyValue(map);
		
		List<DbField<?>> fields2 = sudm.primaryKeys();
		
		
	}
	
	
	
	
}
