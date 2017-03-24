package com.sooncode.soonjdbc.dao;

 

public class Bean <T> {

	private String beanName;
	
	private String key;
	
	private String val ;
	
	private  T  javaBean ;

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public T getJavaBean() {
		return javaBean;
	}

	public void setJavaBean(T javaBean) {
		this.javaBean = javaBean;
	}

	 

	 
	
}
