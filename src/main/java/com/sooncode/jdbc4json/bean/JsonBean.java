package com.sooncode.jdbc4json.bean;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.jdbc4json.constant.DATE_FORMAT;
import com.sooncode.jdbc4json.constant.STRING;
import com.sooncode.jdbc4json.json.SJson;
import com.sooncode.jdbc4json.reflect.RObject;
import com.sooncode.jdbc4json.util.T2E;

import java.util.TreeMap;

public class JsonBean {

	private String beanName;
    private String className;
	
	
	/***
	 * 唯一标识字段
	 */
	private String id;

	/** 标识字段对应的值 */
	private Object idVal;

	private Map<String, Object> map = new LinkedHashMap<>();

	public JsonBean(){
		
	}
	 
	public <T> JsonBean(T javaBean) {
		 
		String tClassName = javaBean.getClass().getSimpleName(); 
		this.beanName  =T2E.toField(  T2E.toColumn(tClassName)); 
		this.className = javaBean.getClass().getName();
		RObject rObj = new RObject(javaBean);
		Map<String, Object> map = rObj.getFiledAndValue();
		this.map.putAll(map);
	}

	public JsonBean(String string) {
		boolean b = SJson.isJson(string);
		if (b == true) {
			SJson sj = new SJson(string);
			Map<String, Object> map = sj.getMap();
			String beanName = null;
			if (map != null && map.size() == 1) {
				for (Entry<String, Object> en : map.entrySet()) {
					beanName = en.getKey();
					String jsonString = sj.getFields(beanName).toString();
					if (SJson.isJson(jsonString) == true) {
						this.beanName = beanName;
						SJson newSj = new SJson(jsonString);
						for (Entry<String, Object> e : newSj.getMap().entrySet()) {
							this.addField(e.getKey(), e.getValue());
						}
					} else {
						this.addField(en.getKey(), en.getValue());
					}
				}

			} else {
				for (Entry<String, Object> e : map.entrySet()) {
					this.addField(e.getKey(), e.getValue());
				}
			}
		} else {
			this.beanName = string;
		}
	}

	public JsonBean(String beanName, Map<String, Object> map) {

		for (Entry<String, Object> en : map.entrySet()) {
			String key = en.getKey();
			Object val = en.getValue();
			if (beanName == null && !key.contains(STRING.DOLLAR)) {
				this.map.put(key, val);
			} else if (beanName != null && !"".equals(beanName) && key.contains(STRING.DOLLAR)) {
				String[] keys = key.split(STRING.ESCAPE_DOLLAR);
				if (keys.length == 2 && beanName.equals(keys[0])) {
					this.map.put(keys[1], val);
				}
			}
		}
	}

	public   void  addField(JsonBean  jsonBean) {
		if (jsonBean != null) {
			this.addField(jsonBean.getBeanName(), jsonBean.getFields());
		}
	}

	public void addField(String key, Object value) {
		if (key != null && !key.trim().equals("") && value != null) {
			if (value instanceof Date) {
				String str = new SimpleDateFormat(DATE_FORMAT.ALL_DATE).format(value);
				map.put(key, str);
			} else {
				map.put(key, value);
			}
		}
	}

	public void addField(String key, List<JsonBean> jsonBeans) {
		if (jsonBeans != null && jsonBeans.size() > 0) {
			List<Map<String, Object>> list = new LinkedList<>();
			for (JsonBean  j : jsonBeans) {
				list.add(j.getFields());
			}
			this.addField(key, list);
		}
	}
	 

	public void addFields(Map<String, Object> fields) {

		if (fields != null && fields.size() > 0) {
			for (Entry<String, Object> en : fields.entrySet()) {
				String key = en.getKey();
				Object value = en.getValue();
				if (!key.trim().equals("") && value != null) {
					if (value instanceof Date) {
						String str = new SimpleDateFormat(DATE_FORMAT.ALL_DATE).format(value);
						map.put(key, str);
					} else {
						map.put(key, value);
					}
				}

			}
		}

	}

	public <T> T getField(String key) {
		if (key != null && !key.trim().equals("")) {
			@SuppressWarnings("unchecked")
			T t = (T) this.map.get(key);
			return t ;
		} else {
			return null;
		}

	}

	public Map<String, Object> getFields() {
		return this.map;

	}

	public void removeField(String key) {
		if (key != null && !key.trim().equals("")) {
			this.map.remove(key);
		}
	}

	public void updateField(String key, Object value) {
		if (key != null && !key.trim().equals("") && value != null) {
			this.map.remove(key);
			this.map.put(key, value);
		}
	}

	public String getJsonString() {
		SJson sBean = new SJson(map);
		String jsonString = new String();
		if (this.beanName != null) {
			Map<String, Object> newMap = new TreeMap<>();
			newMap.put(beanName, sBean.getJsonString());
			jsonString = new SJson(newMap).getJsonString();

		} else {
			jsonString = sBean.getJsonString();
		}
		return jsonString;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String toString() {
		return this.getJsonString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getIdVal() {
		return idVal;
	}

	public void setIdVal(Object idVal) {
		this.idVal = idVal;
	}

	
	public <T> T getJavaBean(Class<T> javaBeanClass) {
		 
		RObject rObj = new RObject(javaBeanClass);
		Map<String,Object> fileds = rObj.getFiledAndValue();
		for (Entry<String,Object> en : fileds.entrySet()) {
			String key = en.getKey();
			Object val = this.map.get(key);
			if(val!=null){
				rObj.invokeSetMethod(key, val);
			}
		}
		@SuppressWarnings("unchecked")
		T javaBean = (T) rObj.getObject();
		return javaBean ;
	}

	public String getClassName() {
		return className;
	}

	
	
}
