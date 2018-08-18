package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.ColumnsReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;
import com.sooncode.soonjdbc.util.DbModel;

public class SelectSqlBuilder implements SqlBuilder {
	public static final String SELECT_SQL = "SELECT [COLUMNS] FROM [TABLE] WHERE 1=1";
	@Override
	public Parameter getParameter(DbModel dbModel) {
		Columns columns = new Columns(dbModel);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new ColumnsReplace())
		   .addSqlReplace(new TableNameReplace());
		Parameter parameter = new Parameter(); 
		parameter.setReadySql(SELECT_SQL);
		parameter = src.getReplacedSql(parameter, columns);
		return parameter;
	}

}
