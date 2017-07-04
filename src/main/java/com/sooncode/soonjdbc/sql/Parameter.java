package com.sooncode.soonjdbc.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.exception.SqlException;

/**
 * 预编译SQL和参数 模型
 * 
 * @author pc
 *
 */
public class Parameter {

	public Parameter() {

	}
    /**
     * 
     * @param readySql 预编译SQL ，或可执行SQL。
     */
	public Parameter(String readySql) {
       this.readySql = readySql;
	}

	/** 预编译SQL */
	private String readySql;
	 
	
	/** 参数 ，从1开始 */
	private Map<Integer, Object> params = new HashMap<>();

	/**
	 * 从 1 开始，按添加的顺序递增。
	 * @param value
	 */
	public void addParameter(Object value){
		    if(value != null) {
		    	this.params.put(params.size()+1, value);
		    }
	}
	
	public void addParameters(List<Object> values){
		if(values != null) {
			for (Object value : values) {
				this.addParameter(value);
			}
		}
	}
	
	
	public void addParameters(Map<Integer,Object> values){
		for (int i = 1; i <= values.size() ;i++) {
		 Object value = values.get(i);
		 addParameter(value);
		}
	}
	
	
	
	public String getReadySql() {
		return readySql;
	}

	public void setReadySql(String readySql) {
		this.readySql = readySql;
	}

	public Map<Integer, Object> getParams() {
		return params;
	}

	public void setParams(Map<Integer, Object> params) {
		this.params = params;
	}

	 
	
	
	 
	 
	 
	/**
	 * 参数模型是否没有异常</br>
	 * 当预编译SQL为空（null），或 为空字符串时，为存在异常；当预编译中的参数大于参数Map中的个数时，存在异常。
	 * @return 存在异常时返回false；没有异常时返回true.
	 * @throws SqlException 
	 */
	public boolean isNotException()  {
		
		if(this.readySql == null || this.readySql.trim().equals(STRING.NULL_STR)){
			return false;
		}else{
			StringBuilder sql = new StringBuilder(this.readySql);
			int size = 0;
			while(sql.indexOf(STRING.QUESTION)!=-1){
				int n = sql.indexOf(STRING.QUESTION);
				sql.replace(n, n + 1, STRING.AT); 
				size ++;
			}
			if(this.params.size()>=size){
				return true;
			}else{
				 try {
					throw new SqlException("parameter is not matching （参数不匹配）");
				} catch (SqlException e) {
					e.printStackTrace();
				}
				 return false;
			}
		}
	}
}
