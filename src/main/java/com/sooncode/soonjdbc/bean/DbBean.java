package com.sooncode.soonjdbc.bean;

import java.util.List;
import java.util.Map;

import com.sooncode.soonjdbc.reflect.RObject;
import com.sooncode.soonjdbc.util.T2E;

public class DbBean {
	private Object javaBean;
	private String className;
	private String beanName;
    private String tableName;
	private String primaryField;
	private Object primaryFieldValue;
	private List<ForeignKey> foreignKeies;
	private Map<String, Object> fields;

	public DbBean() {

	}

	public <T> DbBean(T t) {
			this.javaBean = t;
			this.className = t.getClass().getName();
			
			this.beanName = t.getClass().getSimpleName();
			this.tableName = T2E.toTableName(this.beanName );
			RObject<T> rObj = new RObject<T>(t);
			this.fields = rObj.getFiledAndValue();
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getPrimaryField() {
		return primaryField;
	}

	public void setPrimaryField(String primaryField) {
		this.primaryField = primaryField;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}

	public Object getPrimaryFieldValue() {
		return primaryFieldValue;
	}

	public void setPrimaryFieldValue(Object primaryFieldValue) {
		this.primaryFieldValue = primaryFieldValue;
	}

	public List<ForeignKey> getForeignKeies() {
		return foreignKeies;
	}

	public void setForeignKeies(List<ForeignKey> foreignKeies) {
		this.foreignKeies = foreignKeies;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Object getJavaBean() {
		return javaBean;
	}

 

	public void setJavaBean(Object javaBean) {
		this.javaBean = javaBean;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
}
