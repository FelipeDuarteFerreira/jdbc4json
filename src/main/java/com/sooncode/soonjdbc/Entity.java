package com.sooncode.jdbc4json;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sooncode.jdbc4json.constant.STRING;
import com.sooncode.jdbc4json.reflect.RObject;
import com.sooncode.jdbc4json.util.T2E;

 

public class Entity  {

	private   Entity( ) {
		 
	}

	/**
	 * 抓取实体对象
	 * @param list
	 * @return
	 */
	public static  <T> List<T> findEntity(List<Map<String, Object>> list,Class<T> clas) {
		if (list == null || list.size() == 0) {
			return new LinkedList<>();
		}
		List<Object> objects = new LinkedList<>();
		for (Map<String, Object> map : list) {
			Object object = findEntity(map,clas);
			if (objects.size() >= 1 && object.toString().equals(objects.get(objects.size() - 1).toString())) {
				continue;
			}
			objects.add(object);
		}
		@SuppressWarnings("unchecked")
		List<T> tes = (List<T>) objects;
		return tes;
	}

	/**
	 * Map 转换成 实体对象
	 * @param map
	 * @return
	 */
	 
	public static  <T> T findEntity(Map<String, Object> map,Class<T> clas) {
		String tableName =  T2E.toColumn(clas.getSimpleName()) ;

		RObject<T> rObj = new RObject<>(clas);
		List<Field> fields = rObj.getFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			String columnName = T2E.toColumn(field.getName());
			String key = T2E.toField(tableName) + STRING.DOLLAR + T2E.toField(columnName);
			Object value = map.get(key);
			if (value == null) {
				value = map.get(field.getName());
				if (value == null) {
					continue;
				}
			}
			rObj.invokeSetMethod(fieldName, value);
		}
		if (isNull(rObj.getObject()) == false) {
			return null;
		} else {
			T t = (T) rObj.getObject();
			return t;
		}
	}

	/**
	 * 验证 object是否为空 或 其属性是否全为空
	 * 
	 * @param object
	 *            被验证的实体对象
	 * 
	 * @return object为空返回 false ; object中的所有属性对应的值为空返回false.
	 */
	public static boolean isNull(Object object) {
		if (object == null) {
			return false;
		}
		// obj的属性值不全为null
		RObject<?> rObj = new RObject<>(object);
		Map<String, Object> files = rObj.getFiledAndValue();
		boolean b = false;
		for (Map.Entry<String, Object> en : files.entrySet()) {
			if (en.getValue() == null) {
				b = b || false;
			} else {
				b = b || true;
			}
		}

		if (b == false) {
			return false;
		} else {
			return true;
		}
	}
	 
}
