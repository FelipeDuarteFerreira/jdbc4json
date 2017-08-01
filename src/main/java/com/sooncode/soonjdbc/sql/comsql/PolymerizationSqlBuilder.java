package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.dao.polymerization.Polymerization;
import com.sooncode.soonjdbc.sql.Parameter;

public class PolymerizationSqlBuilder  {
	
	public static final String SELECT_POLYMERIZATION_SQL = "SELECT [POLYMERIZATION_KEY]([KEY]) AS SIZE [COLUMNS] FROM [TABLE] WHERE 1=1";

 
	public Parameter getParameter(String tableName, Polymerization polymerization ,String key,String columns) {
		String selectPolymerizationSql = SELECT_POLYMERIZATION_SQL.replace("[POLYMERIZATION_KEY]", polymerization.getKey())
				.replace("[KEY]", key)
				.replace("[COLUMNS]", columns)
				.replace("[TABLE]", tableName);
		Parameter parameter = new Parameter();
		parameter.setReadySql(selectPolymerizationSql);
		return parameter;
	}

}
