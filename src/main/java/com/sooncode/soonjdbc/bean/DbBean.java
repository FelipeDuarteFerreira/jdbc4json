package com.sooncode.soonjdbc.bean;

import java.util.List;
import java.util.Map;

import com.sooncode.soonjdbc.reflect.RObject;
import com.sooncode.soonjdbc.util.T2E;

public class DbBean {
	private Object javaBean;
	private String className;
	private String beanName;
	private String aliasTableName;
	private String primaryField;
	private Object primaryFieldValue;
	private List<ForeignKey> foreignKeies;
	private Map<String, Object> fields;

	public DbBean() {

	}

	public <T> DbBean(T t) {
		if (t instanceof Alias) {
			Alias al = (Alias) t;
			this.javaBean = al.getObj();
			this.className = this.javaBean.getClass().getName();
			this.beanName = al.getAlias();
			this.aliasTableName = T2E.toColumn(this.javaBean.getClass().getSimpleName()) + " AS " + T2E.toColumn(this.beanName);
			RObject<?> rObj = new RObject<>(this.javaBean);
			this.fields = rObj.getFiledAndValue();
		} else {

			this.javaBean = t;
			this.className = t.getClass().getName();
			this.beanName = t.getClass().getSimpleName();
			this.aliasTableName =  T2E.toColumn(this.beanName);
			RObject<T> rObj = new RObject<T>(t);
			this.fields = rObj.getFiledAndValue();
		}
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

	public String getAliasTableName() {
		return aliasTableName;
	}

	public void setAliasTableName(String aliasTableName) {
		this.aliasTableName = aliasTableName;
	}

	public void setJavaBean(Object javaBean) {
		this.javaBean = javaBean;
	}

}
