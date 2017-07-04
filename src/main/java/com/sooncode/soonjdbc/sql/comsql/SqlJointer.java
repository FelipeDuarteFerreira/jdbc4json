package com.sooncode.soonjdbc.sql.comsql;

import java.util.LinkedList;
import java.util.List;

import com.sooncode.soonjdbc.constant.STRING;

public class SqlJointer {

	public static String join(List<String> keys,String joinKey){
		String str = new String();
		if( keys == null || keys.size() == 0 || joinKey == null || joinKey.equals(STRING.NULL_STR)){
			return str;
		}
		for (String key : keys) {
			str += key; 
			str += joinKey;
			
		}
		str = subString(str, joinKey);
		return str;
	}
	
	public static String join(int size, String key, String joinKey){
		String str = new String();
		if( size < 1 || key == null ||  key.equals(STRING.NULL_STR) || joinKey == null || joinKey.equals(STRING.NULL_STR)){
			return str;
		}
		for (int i = 0; i < size ; i++) {
			str += key; 
			str += joinKey;
		}
		 
		str = subString(str, joinKey);
		return str;
	}
	
	
	
	
	private static String subString(String str , String joinKey) {
		int endIndex = str.lastIndexOf(joinKey);
		str = str.substring(0, endIndex);
		return str;
	}
	 
	
	
	public static void main(String[] args) {
		List<String> list = new LinkedList<>();
		list.add("AAA");
		list.add("BBB");
		list.add("CCC");
		
		String key = ",";
		
		System.out.println(join(list,key));
		System.out.println(join(5,"?",key));
	}
}
