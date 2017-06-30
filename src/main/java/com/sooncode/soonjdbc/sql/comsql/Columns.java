package com.sooncode.soonjdbc.sql.comsql;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.sql.SqlJointer;
import com.sooncode.soonjdbc.util.T2E;

public class Columns {

	private List<String> columns = new LinkedList<>();
	private List<String> questions = new LinkedList<>();
	private Map<Integer, Object> parameter = new LinkedHashMap<>();
    private String tableName ;
	public Columns(DbBean dbBean) {
		tableName = dbBean.getTableName();
		Map<String, Object> parameterMap  = dbBean.getFields();
		int index = 1;
		for (Entry<String, Object> en : parameterMap.entrySet()) {
			String column = T2E.toColumn( en.getKey());
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

	public Map<Integer, Object> getParameter() {
		return this.parameter;

	}
	
	public String getTableName(){
		return this.tableName;
	}
}
