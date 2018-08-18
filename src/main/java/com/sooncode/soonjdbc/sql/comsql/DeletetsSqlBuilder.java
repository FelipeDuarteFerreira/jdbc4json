package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;
import com.sooncode.soonjdbc.util.DbModel;

public class DeletetsSqlBuilder implements SqlBuilder {
	public static final String DELETE_SQL = "DELETE FROM [TABLE] WHERE 1=1";
	@Override
	public Parameter getParameter(DbModel dbModel) {
		Columns columns = new Columns(dbModel);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace());
		Parameter parameter = new Parameter(); 
		parameter.setReadySql(DELETE_SQL);
		parameter = src.getReplacedSql(parameter, columns);
		return parameter;
	}

}
