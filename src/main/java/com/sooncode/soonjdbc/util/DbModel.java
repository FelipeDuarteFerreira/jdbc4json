package com.sooncode.soonjdbc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sooncode.soonjdbc.reflect.RObject;

public abstract class DbModel {

	public abstract String tableName();

	public abstract List<Field> primaryKeys();

	private Class<?> javaBeanClass;

	public Class<?> getJavaBeanClass() {
		return javaBeanClass;
	}

	public void setJavaBeanClass(Class<?> javaBeanClass) {
		this.javaBeanClass = javaBeanClass;
	}

	public <T> T transform(Class<?> clas) {
		RObject<T> javaBeanR = new RObject<>(clas);
		List<Field> fields = getAllField();
		for (Field field : fields) {
			javaBeanR.invokeSetMethod(field.getPropertyName(), field.getValue());
		}
		return javaBeanR.getObject();
	}

	public static <T> List<T> transform(List<DbModel> dbModels, Class<T> clas) {
		List<T> list = new ArrayList<>();
		if (dbModels == null || dbModels.size() == 0) {
			return new ArrayList<>();
		} else {
			for (DbModel dbModel : dbModels) {
				T t = dbModel.transform(clas);
				list.add(t);
			}
			return list;
		}

	}

	protected <T> void init(T javaBean) {

		this.javaBeanClass = javaBean.getClass();
		RObject<T> javaBeanR = new RObject<>(javaBean);
		List<Field> fields = getAllField();
		for (Field field : fields) {
			field.setValue(javaBeanR.invokeGetMethod(field.getPropertyName()));
		}

	}

	public void injectPropertyValue(Map<String, Object> map) {
		List<Field> fields = getAllField();
		for (Field field : fields) {

			Object val = map.get(field.getPropertyName());
			if (val == null) {
				val = map.get(T2E.toField(field.getTableName()) + "$" + field.getPropertyName());
			}
			field.setValue(val);
		}
	}

	public <T> void injectPropertyValue(T javaBean) {
		init(javaBean);
	}

	private List<Field> getAllField() {
		List<Field> fields = new ArrayList<Field>();
		RObject<DbModel> dbModelR = new RObject<DbModel>(this);
		for (java.lang.reflect.Field f : dbModelR.getFields()) {
			if (f.getType().equals(Field.class)) {
				try {
					Field field = (Field) f.get(this);
					fields.add(field);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return fields;
	}

}
