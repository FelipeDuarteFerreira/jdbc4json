package com.sooncode.soonjdbc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public static <O,N> N to(O oldObj, Class<N> newObjClass) {
        if(oldObj==null || newObjClass==null){
        	return null;
        }
		RObject<O> rOldObj = new RObject<>(oldObj);
		RObject<N> rNewObj = new RObject<>(newObjClass);

		Map<String, Object> map = rOldObj.getFiledAndValue();

		for (Entry<String, Object> en : map.entrySet()) {
			String key = en.getKey();
			Object val = en.getValue();
			if (rNewObj.hasField(key)) {
				rNewObj.invokeSetMethod(key, val);
			}
		}

		return rNewObj.getObject();

	}

	/**
	 * 批量对象转换
	 * 
	 * @param oldObjes
	 *            被转换的对象集
	 * @param newObjClass
	 *            新对象的Class
	 * @return 新对象集
	 */
	public static <O,N>  List<N> tos(List<O> oldObjes, Class<N> newObjClass) {
		List<N> list = new ArrayList<N>();
		  if(oldObjes==null || oldObjes.size()==0 || newObjClass==null){
	        	return new ArrayList<>();
	        }
		for (O obj : oldObjes) {
			N newObje = to(obj, newObjClass);
			list.add(newObje);
		}
		return list;
	}
	
}
