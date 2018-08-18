package com.sooncode.soonjdbc.util;

import java.util.HashMap;
import java.util.Map;

import com.sooncode.soonjdbc.reflect.RObject;

public class DbModel2JavaBean {

	private String beanName;
	
	private Class<?> javaBeanClass ;

	private Map<String, Object> fields = new HashMap<>();

	public DbModel2JavaBean(DbModel dbModel) {

		this.beanName = T2E.toClassName(dbModel.tableName());

		RObject<DbModel> rObj = new RObject<DbModel>(dbModel);

		Map<String, Object> newFields = rObj.getFiledAndValue();

		for (java.lang.reflect.Field field : rObj.getFields()) {

			String key = field.getName();
			if (field.getType().equals(DbField.class)) {
				field.setAccessible(true);
				DbField f;
				try {
					f = (DbField) field.get(dbModel);
					newFields.put(key, f.getValue());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else {
				newFields.remove(key);
			}

		}

		this.fields = newFields;
		this.javaBeanClass = dbModel.getJavaBeanClass();

	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}

	public Class<?> getJavaBeanClass() {
		return javaBeanClass;
	}

	public void setJavaBeanClass(Class<?> javaBeanClass) {
		this.javaBeanClass = javaBeanClass;
	}
	
	

}
