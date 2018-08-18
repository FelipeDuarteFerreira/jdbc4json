package com.sooncode.soonjdbc.table;

import java.util.List;
import java.util.Map;

import com.sooncode.soonjdbc.exception.DbModelIsNull;
import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbModel2JavaBean;

public class DbTable {

	private String tableName;
	private String primaryField;
	private Object primaryFieldValue;
	private List<ForeignKey> foreignKeies;
	private Map<String, Object> fields;

	public DbTable() {

	}

	public <T> DbTable(DbModel dbModel) {
 
		if (dbModel == null) {
			try {
				throw new DbModelIsNull("dbModel is null !");
			} catch (DbModelIsNull e) {
				e.printStackTrace();
			}
		}

		DbModel2JavaBean dmjb = new DbModel2JavaBean(dbModel);
		this.tableName = dbModel.tableName();
		this.fields = dmjb.getFields();

	}

	public String getPrimaryField() {
		return primaryField;
	}

	public void setPrimaryField(String primaryField) {
		this.primaryField = primaryField;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}

	public Object getPrimaryFieldValue() {
		return primaryFieldValue;
	}

	public void setPrimaryFieldValue(Object primaryFieldValue) {
		this.primaryFieldValue = primaryFieldValue;
	}

	public List<ForeignKey> getForeignKeies() {
		return foreignKeies;
	}

	public void setForeignKeies(List<ForeignKey> foreignKeies) {
		this.foreignKeies = foreignKeies;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
