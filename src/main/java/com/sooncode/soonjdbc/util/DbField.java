package com.sooncode.soonjdbc.util;

public abstract class DbField<T>{
	
	private String tableName;

	private String propertyName ;
	
	private String columnName;
	protected T value;
	public DbField (String tableName,String columnName) {
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

	public String getTableName() {
		return tableName;
	}
	 

	public abstract T getValue();  

	public abstract void setValue(T value);  


	 
	
	
}
