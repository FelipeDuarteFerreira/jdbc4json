package com.sooncode.soonjdbc.reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * 反射创建的对象
 * 
 * @author pc
 *
 */
public class RObject<T> {
	public static Logger logger = Logger.getLogger("RObject.class");
	private static final String NULL_STR = "";
	private static final String CLASS = "class ";
	private static final String LIST_INTERFACE = "interface java.util.List";
	private static final String JAVA_TYPES = "Integer Long Short Byte Float Double Character Boolean Date String";
	private static final String UID = "serialVersionUID";

	/** 被反射代理的对象 */
	private T object;

	public   RObject(T object) {
		this.object = object;
	}

	@SuppressWarnings("unchecked")
	public RObject(Class<?> clas) {

		try {
			this.object = (T) clas.newInstance();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public RObject(String className) {
		Class<?> clas;
		try {
			clas = Class.forName(className);
			this.object = (T) clas.newInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 获取对象的类名 */
	public String getClassName() {
		return this.object.getClass().getSimpleName();
	}

	/** 获取对象的全类名 */
	public String getAllClassName() {
		return this.object.getClass().getName();
	}

	/** 获取被反射代理的对象 */
	public   T getObject() {
		return object;
	}

	/**
	 * 获取被反射代理对象的属性集(除serialVersionUID属性外) 包括父类的属性
	 * 
	 * @return
	 */
	public List<Field> getFields() {
		List<Field> list = new ArrayList<>();

		Class<?> thisClass = this.object.getClass();

		int n = 0;
		for (; thisClass != Object.class; thisClass = thisClass.getSuperclass()) {

			Field[] fields = thisClass.getDeclaredFields();
			if (n == 0) {
				for (Field f : fields) {
					if (!f.getName().equals(UID)) {
						list.add(f);
					}
				}

			} else {
				for (Field f : fields) {
					int i = f.getModifiers();
					boolean isPrivate = Modifier.isPrivate(i);
					if (!f.getName().equals(UID) && isPrivate == false) {
						list.add(f);
					}
				}
			}

			n++;
		}

		return list;
	}

	/**
	 * 判断属性是否存在
	 * 
	 * @param field
	 * @return
	 */
	public Boolean hasField(String field) {
		if (field == null || field.equals(NULL_STR)) {
			return false;
		}
		List<Field> fields = this.getFields();
		for (Field f : fields) {
			if (f.getName().equals(field.trim())) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 获取 list 类型的属性名称
	 * 
	 * @param listClass
	 *            list的数据类型
	 * @return 属性名称
	 */
	public String getListFieldName(Class<?> listClass) {
		List<Field> fields = getFields();
		for (Field f : fields) {
			String typeName = f.getType().toString();
			if (typeName.contains(LIST_INTERFACE)) {
				ParameterizedType pt = (ParameterizedType) f.getGenericType();
				String str = pt.getActualTypeArguments()[0].toString(); // 获取List泛型参数类型名称
				str = str.replace(CLASS, NULL_STR).trim();// 全类名
				if (str.equals(listClass.getName())) {
					return f.getName();
				}
			}
		}
		return null;
	}

	/**
	 * 执行对象的SET方法
	 * 
	 * @param fieldName
	 * @param args
	 */
	public void invokeSetMethod(String fieldName, Object... args) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, this.object.getClass());
			// 获得set方法
			Method method = pd.getWriteMethod();
			method.invoke(object, args);
		} catch (Exception e) {
			//logger.error(e.getMessage());
		}

	}

	/**
	 * 获取Set方法的参数类型
	 * 
	 * @param fieldName
	 * @return
	 */
	public Class<?> getSetMethodParamertType(String fieldName) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, this.object.getClass());
			Method method = pd.getWriteMethod();
			Class<?>[] c = method.getParameterTypes();
			return c[0];
		} catch (IntrospectionException e) {
			//logger.error(e.getMessage());
			return null;
		}

	}

	/**
	 * 执行对象的GET方法
	 * 
	 * @param fieldName
	 * @return
	 */

	public   T invokeGetMethod(String fieldName) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, this.object.getClass());
			// 获得set方法
			Method method = pd.getReadMethod();
			@SuppressWarnings("unchecked")
			T t = (T) method.invoke(this.object);
			return t;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	/** 获取对象的属性和其对应的值 */
	public Map<String, Object> getFiledAndValue() {

		String str = JAVA_TYPES;
		Map<String, Object> map = new HashMap<>();

		List<Field> fields = this.getFields();
		for (Field field : fields) {
			String name = field.getName().replace("$cglib_prop_", "");
			// logger.debug(field.getName());
			if (!field.getName().equals(UID) && str.contains(field.getType().getSimpleName())) {
				map.put(name, this.invokeGetMethod(name));
			}
		}
		return map;
	}

	/** 获取对象的第一个属性名称 （主键） */
	public String getPk() {

		Class<?> c = object.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			if (!field.getName().equals(UID)) {
				return field.getName();
			}
		}
		return null;
	}

	/** 设置对象的第一个属性名称 （主键） */
	public void setPk(Object value) {
		String pk = getPk();
		invokeSetMethod(pk, value);

	}

	/** 获取对象的第一个属性的值 */
	public   T getPkValue() {

		String str = JAVA_TYPES;// "Integer Long Short Byte Float Double
								// Character Boolean Date String";
		Class<?> c = object.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			// logger.debug(field.getName());
			if (!field.getName().equals(UID) && str.contains(field.getType().getSimpleName())) {

				return this.invokeGetMethod(field.getName());
			}
		}
		return null;
	}

	/**
	 * 反射执行方法
	 * 
	 * @param methodName
	 *            方法名称
	 * @param args
	 *            方法需要的参数集
	 * @return 方法执行的返回值
	 */

	public   T invoke(String methodName, Object... args) {
		try {
			Method method = null;
			for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					method = clazz.getDeclaredMethod(methodName, new Object().getClass());
				} catch (Exception e) {
				}
			}
			@SuppressWarnings("unchecked")
			T t = (T) method.invoke(object, args);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Method getDeclaredMethod(Object object, String methodName) {
		Method method = null;
		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName);
				return method;
			} catch (Exception e) {
			}
		}
		return null;
	}

	 

}
