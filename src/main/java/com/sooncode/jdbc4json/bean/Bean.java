package com.sooncode.jdbc4json.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Bean<T> {

	protected Class<?> javaBeanClass;
	
	public Bean (){
		Type type = this.getClass().getGenericSuperclass(); // 非常关键的一步
		ParameterizedType parameterizedType = (ParameterizedType) type;// ParameterizedType:表示参数化类型
		Class<?> TClass = (Class<?>) parameterizedType.getActualTypeArguments()[0]; // getActualTypeArguments():
		this.javaBeanClass = TClass; // 泛型T实际运行时的全类名
	}
	
	public Bean (Map<String,Object> map){
		
	}
	public Bean (List<Map<String,Object>> list){
		
	}
}
