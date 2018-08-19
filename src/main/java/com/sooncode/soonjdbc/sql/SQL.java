package com.sooncode.soonjdbc.sql;

import java.util.HashMap;
import java.util.Map;

import com.sooncode.soonjdbc.sql.condition.Cond;
import com.sooncode.soonjdbc.sql.condition.sign.Sign;
import com.sooncode.soonjdbc.util.DbField;
import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbctool.T2E;

 

/**
 * SQL 
 * 
 * @author hechenwe@gmail.com
 *
 */
public class SQL {

	/** 预编译SQL */
	private String readySql = "";

	/** 参数 ，从1开始 */
	private Map<Integer, Object> params = new HashMap<>();

	public String toString() {
		return this.readySql;
	}

	public Parameter getParameter() {
		Parameter p = new Parameter();
		p.setReadySql(this.readySql);
		p.setParams(params);
		return p;
	}

	public SQL SELECT() {
		this.readySql = "SELECT * ";
		return this;
	}

	public SQL SELECT(DbField<?>... dbFields) {
		 
		return this;
	}
	public SQL SELECT(DbModel... dbModels) {
		
		return this;
	}

	 

	public SQL FROM(DbModel ... dbModels) {

		 
		return this;
	}
	 
	public SQL INNER_JOIN(DbModel dbModel) {
		return this;
	}

	public SQL ON(DbField<?> dbField1,DbField<?> dbField2) {
		return this ;
	}

	public SQL WHERE() {
		return this;
	}

	public SQL WHERE(Cond cond) {

		 
		return this;
	}
	public SQL WHERE(DbField<?> dbField1,DbField<?> dbField2) {
		
		
		return this;
	}
	
	public SQL AND(DbField<?> dbField1,DbField<?> dbField2) {
		return this;
	}
	public SQL AND(DbField<?> dbField,Sign sign , Object value) {
		return this;
	}

  
	/**
	 * 排序语句片段
	 * 
	 * @param fields
	 *            需要排序的属性集
	 * @return SelSql 对象
	 */
	public SQL ORDER_BY(String... fields) {
		 
		return this;
	}
 

	public SQL GROUP_BY(String key) {
		this.readySql = this.readySql + " GROUP BY " + T2E.toColumn(key) + " ";
		return this;
	}
 
	 
	public SQL LIMIT(Long pageNumber, Long pageSize) {
		Long index = (pageNumber - 1) * pageSize;
		String sql = " LIMIT " + index + "," + pageSize;
		this.readySql = this.readySql + sql;
		return this;

	}

}