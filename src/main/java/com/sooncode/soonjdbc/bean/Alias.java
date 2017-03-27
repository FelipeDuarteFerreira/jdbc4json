package com.sooncode.soonjdbc.bean;


/**
 * Bean 的别名映射
 * 
 * @author pc
 *
 */
public class Alias {
	private String alias;
	private Object obj;

	public <T> Alias(T t) {
		obj = t;
		alias = t.getClass().getSimpleName() + "2";

	}

	public String getAlias() {
		return alias;
	}

	public Object getObj() {
		return obj;
	}

}
