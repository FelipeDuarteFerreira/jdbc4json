package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.comsql.Columns;

public class ParameterReplace implements SqlReplace{

	@Override
	public String getReplacedSql(String sql, Columns columns, SqlReplaceChainI sqlReplaceChain) {

        String sqlString = new String();
        sqlString = sql.replace("[PARAMETERS]", columns.getParameters());
		return sqlReplaceChain.getReplacedSql(sqlString, columns);
	}

}
