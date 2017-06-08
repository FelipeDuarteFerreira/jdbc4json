package com.sooncode.soonjdbc;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.sooncode.soonjdbc.page.One2One;
import com.sooncode.soonjdbc.reflect.RObject;

/**
 * 数据模型转换
 * @author hechenwe@gmail.com
 *
 */
public class ModelTransform {

	
	
	public static <T> T getModel(One2One one2one,Class<T> tClass){
		
		RObject<T> rObj = new RObject<>(tClass);
		List<Field> fields = rObj.getFields();
		for (Field f : fields) {
			Class<?> cls  = f.getType();
			String name = f.getName();
			Object obj = one2one.getOne(cls);
			rObj.invokeSetMethod(name, obj);
		}
		return rObj.getObject();
	}
	
	public static <T> List<T> getModel(List<One2One> one2ones,Class<T> tClass){
		List<T> list = new LinkedList<>();
		for (One2One one2one : one2ones) {
			list.add(getModel(one2one, tClass));
		}
		return list;
	}
	
}
