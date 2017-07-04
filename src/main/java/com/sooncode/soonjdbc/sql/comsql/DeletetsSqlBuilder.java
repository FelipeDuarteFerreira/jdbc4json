package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;

public class DeletetsSqlBuilder implements SqlBuilder {
	public static final String DELETE_SQL = "DELETE FROM [TABLE] WHERE ";
	@Override
	public Parameter getParameter(DbBean dbBean) {
		Columns columns = new Columns(dbBean);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace());
		Parameter parameter = new Parameter(); 
		parameter.setReadySql(DELETE_SQL);
		parameter = src.getReplacedSql(parameter, columns);
		return parameter;
	}

}
