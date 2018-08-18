package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.ColumnsReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.ParameterReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;
import com.sooncode.soonjdbc.util.DbModel;

public class InsertSqlBuilder implements SqlBuilder {
	public static final String INSERT_SQL = "INSERT INTO [TABLE] ([COLUMNS]) VALUES ([PARAMETERS])";
	@Override
	public Parameter getParameter(DbModel dbModel) {
		Columns c = new Columns(dbModel);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace()).addSqlReplace(new ColumnsReplace()).addSqlReplace(new ParameterReplace());
		Parameter p = new Parameter(); 
		p.setReadySql(INSERT_SQL);
		p = src.getReplacedSql(p, c);
		return p;
	}

}
