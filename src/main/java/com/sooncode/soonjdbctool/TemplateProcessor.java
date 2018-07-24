package com.sooncode.soonjdbctool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TemplateProcessor {
 
	public static String getCode4List ( String listTemplate , List<String> datas  ) {
		
		listTemplate = listTemplate.trim();
		
		int startIndex = listTemplate.indexOf("<list") + 5;
		int endIndex = listTemplate.indexOf(">") ;
		int textEndInex = listTemplate.indexOf("</list>");
		 
		String listHead = listTemplate.substring(startIndex, endIndex);
		
		String text = listTemplate.substring( endIndex + 1 , textEndInex);
		String [] keys =   listHead.trim().split(" ");
		
		String key = keys[0].trim();
		
		StringBuilder sb = new StringBuilder();
		
		
		for (String data : datas) {
			String textString = text.replace("${"+ key + "}", data) + "\n" ;
			sb.append(textString);
		}
		return sb.toString();
		
	}

	
	public static String getListTemplat (String template, String key) {
		
		String tempTemlate = new String(template);
		int startIndex = tempTemlate.indexOf("<list") + 5;
		int endInex = tempTemlate.indexOf("</list>");
		
		String sub = tempTemlate.substring(startIndex,endInex);
		
		if(sub == null || sub.trim().equals("")) {
			return null;
		}
		
		sub = "<list" +  sub + "</list>";
		if(sub.contains(" "+key + " ")) {
			return sub ;
		}else {
			tempTemlate = tempTemlate.replace(sub, "");
			
			return getListTemplat(tempTemlate,key);
		}
		
		
		
		
		
	}
	
	public static String getCode (String templateString , Map<String,Object> map) {
		
		for(Entry<String, Object> en : map.entrySet()) {
			
			String key = en.getKey();
			Object val = en.getValue();
			if( ! (val instanceof List ) ) {
				templateString = templateString.replace("${"+key + "}", val.toString());
			}
			if(val instanceof List) {
				
			String listTem = getListTemplat(templateString, key);
			if(listTem != null) {
				
				 @SuppressWarnings("unchecked")
				 List<String> datas = (List<String>) val ;
				 
				 String code =  getCode4List(listTem, datas);
				 templateString = templateString .replace(listTem, code);
			}
			
				
			}
			
			
			
			
		}
		
		return templateString ;
		
	}
	 
	
	public static void main(String[] args) {
		String temple = " name = ${name}  hello ; age=${age} ;   <list id in  ids > private String ${id} ; </list>   <list f in  fs > private int ${f} ; </list>";
		List<String> list = new ArrayList<>();
		list.add("hello1");
		list.add("hello2");
		list.add("hello3");
		List<String> list2 = new ArrayList<>();
		list2.add("f1");
		list2.add("f2");
		list2.add("f3");
		
		 
		
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("name", "hechen");
		map.put("age", 123);
		map.put("ids", list);
		map.put("fs", list2);
		
		System.out.println(getCode(temple, map));
		
	}

}
