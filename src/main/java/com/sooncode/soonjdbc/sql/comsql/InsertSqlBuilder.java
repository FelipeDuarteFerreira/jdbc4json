package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.ColumnsReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.ParameterReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;

public class InsertSqlBuilder implements SqlBuilder {
	public static final String INSERT_SQL = "INSERT INTO [TABLE] ([COLUMNS]) VALUES ([PARAMETERS])";
	@Override
	public Parameter getParameter(DbBean dbBean) {
		Columns c = new Columns(dbBean);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace()).addSqlReplace(new ColumnsReplace()).addSqlReplace(new ParameterReplace());
		Parameter p = new Parameter(); 
		p.setReadySql(INSERT_SQL);
		p = src.getReplacedSql(p, c);
		return p;
	}

}