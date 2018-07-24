package com.sooncode.soonjdbc.util;

public class Field {
	
	private String tableName;

	private String propertyName ;
	
	private String columnName;
	
	
	private Object value;
	
	
	
	
	public Field (String tableName,String columnName) {
		this.tableName = tableName;
		this.propertyName = T2E.toField(columnName) ;
		this.columnName = columnName ; 
	}

	public String getPropertyName() {
		return propertyName;
	}

	 

	public String getColumnName() {
		return columnName;
	}

	 

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getTableName() {
		return tableName;
	}

	 
	
	
}
