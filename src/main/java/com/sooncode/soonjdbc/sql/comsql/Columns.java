package com.sooncode.soonjdbc.sql.comsql;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.T2E;

public class Columns {

	private List<String> columns = new LinkedList<>();
	private List<String> questions = new LinkedList<>();
	private Map<Integer, Object> parameter = new LinkedHashMap<>();
	private Map<String, Object> parameterMap;
	private String tableName;
    private String pk;
    private Object pkValue;
	public Columns(DbBean dbBean) {
		this.tableName = dbBean.getTableName();
		this.parameterMap = dbBean.getFields();
        this.pk = T2E.toColumn(dbBean.getPrimaryField());
        this.pkValue = dbBean.getPrimaryFieldValue();
		int index = 1;
		for (Entry<String, Object> en : this.parameterMap.entrySet()) {
			String column = T2E.toColumn(en.getKey());
			column = this.tableName + STRING.POINT + column;
			Object value = en.getValue();
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

	public Map<Integer, Object> getAllParameter() {
		return this.parameter;

	}

	public String getTableName() {
		return this.tableName;
	}
	
	public String getPKCondition(){
		
		return this.pk + SQL_KEY.EQ + SQL_KEY.QUESTION;
		
	}
	
	public Object getPkValue(){
		return this.pkValue;
	}
	

	private List<String> getColumnEqualQuestion(List<String> columns) {
		List<String> list = new LinkedList<>();
		for (Entry<String, Object> en : this.parameterMap.entrySet()) {
			String column = T2E.toColumn(en.getKey());
			if (en.getValue() != null) {
				list.add(column + SQL_KEY.EQ + SQL_KEY.QUESTION);
			}
		}
		return list;
	}
	
	

}
