package com.sooncode.soonjdbc.sql.comsql;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.DbField;
import com.sooncode.soonjdbc.util.DbModel;

public class Columns {

	private List<String> columns = new LinkedList<>();
	private List<String> questions = new LinkedList<>();
	private Map<Integer, Object> parameter = new LinkedHashMap<>();
	private List<DbField<Object>> dbFields;
	private String tableName;
    private List<DbField<Object>> primaryKeies;
	public Columns(DbModel dbModel) {
		this.tableName = dbModel.tableName();
		this.dbFields = dbModel.getAllField();
        this.primaryKeies = dbModel.primaryKeys();
		int index = 1;
		for (DbField<Object> dbField : dbFields) {
			String column = dbField.getColumnName();
			column = this.tableName + STRING.POINT + column;
			Object value = dbField.getValue();
			this.columns.add(column);
			this.questions.add(SQL_KEY.QUESTION);
			this.parameter.put(index, value);
			index++;
		}
	}

	public String getColumns() {
		String columnsString = SqlJointer.join(this.columns, SQL_KEY.COMMA);
		return columnsString;
	}

	public String getParameters() {
		String columnsString = SqlJointer.join(this.questions, SQL_KEY.COMMA);
		return columnsString;
	}

	public String getSetParameters() {
		String columnsString = SqlJointer.join(getColumnEqualQuestion(this.columns), SQL_KEY.COMMA);
		return columnsString;
	}
	public String getSetParametersExcludePK() {
		String columnsString = SqlJointer.join(getColumnEqualQuestionExcludePK(this.columns), SQL_KEY.COMMA);
		return columnsString;
	}

	public String getCondition() {
		String columnsString = SqlJointer.join(getColumnEqualQuestion(this.columns), SQL_KEY.AND);
		return columnsString;

	}

	public Map<Integer, Object> getParameter() {
		Map<Integer, Object> map = new LinkedHashMap<>();
		int index = 1;
		for (Entry<Integer, Object> en : this.parameter.entrySet()) {
			Object value = en.getValue();
			if (value != null) {
				map.put(index, value);
				index++;
			}
		}
		return map;
	}
	public Map<Integer, Object> getParameterExcludePK() {
		Map<Integer, Object> map = new LinkedHashMap<>();
		int index = 1;
		for (DbField<Object> df : this.dbFields) {
			Object value = df.getValue();
			if (value != null && !this.getPkColumnNames().contains(df.getColumnName())) {
				map.put(index, value);
				index++;
			}
		}
		return map;
	}

	public Map<Integer, Object> getAllParameter() {
		return this.parameter;

	}

	public String getTableName() {
		return this.tableName;
	}
	
	public String getPKCondition(){
		String sql = new String();
		for(int i = 0 ; i < this.primaryKeies.size() ; i++ ) {
			DbField<?> dbField = this.primaryKeies.get(i);
			sql += dbField.getColumnName() +  SQL_KEY.EQ + SQL_KEY.QUESTION +  (i != this.primaryKeies.size()-1 ? SQL_KEY.AND : "") ;
		}
		return sql;
		
	}
	 
	
	public List<Object> getPkValues() {
		List<Object> pkValues = new ArrayList<>();
		for(DbField<Object> dbField : this.primaryKeies) {
			Object val = dbField.getValue();
			pkValues.add(val);
		}
		return pkValues;
	}

	private List<String> getColumnEqualQuestion(List<String> columns) {
		List<String> list = new LinkedList<>();
		for (DbField<Object> dbField : this.dbFields) {
			String column = dbField.getColumnName();
			if (dbField.getValue() != null) {
				list.add(column + SQL_KEY.EQ + SQL_KEY.QUESTION);
			}
		}
		return list;
	}
	
	private List<String> getPkColumnNames (){
		List<String> list = new ArrayList<>();
		for(DbField<?> dbField : this.primaryKeies) {
			list.add(dbField.getColumnName());
		}
		return list;
	}
	
	private List<String> getColumnEqualQuestionExcludePK(List<String> columns) {
		List<String> list = new LinkedList<>();
		for (DbField<Object> dbField : this.dbFields) {
			String column = dbField.getColumnName();
			if (dbField.getValue() != null &&  !this.getPkColumnNames().contains(column)) {
				list.add(column + SQL_KEY.EQ + SQL_KEY.QUESTION);
			}
		}
		return list;
	}
 

}
