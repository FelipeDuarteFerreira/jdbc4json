package com.sooncode.soonjdbc.sql;

import java.util.HashMap;
import java.util.Map;

import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.exception.SqlException;

/**
 * 预编译SQL和参数 模型
 * 
 * @author pc
 *
 */
public class PreparedSql {

	/** 预编译SQL */
	private String preparedSql;
	
	private Map<Integer, Object> parameterMap = new HashMap<>();
	
	
	public PreparedSql() {

	}

	 
	public PreparedSql(String preparedSql) {
		this.preparedSql = preparedSql;
	}
	 

	public void addParameter(Object value) {
		this.parameterMap.put(parameterMap.size() + 1, value);
	}

	public void addParameter(Map<Integer, Object> values) {

		for (int i = 1; i <= values.size(); i++) {
			Object value = values.get(i);
			this.parameterMap.put(parameterMap.size() + 1, value);
		}

	}

	public String getPreparedSql() {
		return preparedSql;
	}

	public void setPreparedSql(String preparedSql) {
		this.preparedSql = preparedSql;
	}

	public Map<Integer, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParams(Map<Integer, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	/**
	 * 参数模型是否没有异常</br>
	 * 当预编译SQL为空（null），或 为空字符串时，为存在异常；当预编译中的参数大于参数Map中的个数时，存在异常。
	 * 
	 * @return 存在异常时返回false；没有异常时返回true.
	 * @throws SqlException
	 */
	public boolean isNotException() {

		if (this.preparedSql == null || this.preparedSql.trim().equals(STRING.NULL_STR)) {
			return false;
		} else {
			StringBuilder sql = new StringBuilder(this.preparedSql);
			int size = 0;
			while (sql.indexOf(STRING.QUESTION) != -1) {
				int n = sql.indexOf(STRING.QUESTION);
				sql.replace(n, n + 1, STRING.AT);
				size++;
			}

			if (this.parameterMap.size() >= size) {
				return true;
			} else {
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
