package com.sooncode.soonjdbc.sql;

import java.util.LinkedList;
import java.util.List;

public class SqlJointer {

	public static String join(List<String> keys,String joinKey){
		if(keys.size()==0){
			return null;
		}
		
		String str = new String();
		for (String key : keys) {
			str = str + key + joinKey;
		}
		int endIndex = str.lastIndexOf(joinKey);
		str = str.substring(0, endIndex);
		return str;
	}
	
	public static void main(String[] args) {
		List<String> list = new LinkedList<>();
		//list.add("AAA");
		//list.add("BBB");
		//list.add("CCC");
		
		String key = " , ";
		
		System.out.println(join(list,key));
	}
}
