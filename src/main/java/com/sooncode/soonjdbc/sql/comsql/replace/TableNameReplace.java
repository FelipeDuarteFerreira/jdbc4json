package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.comsql.Columns;

public class TableNameReplace implements SqlReplace{
  
	@Override
	public String getReplacedSql(String sql, Columns columns, SqlReplaceChainI sqlReplaceChain) {
        String sqlString = new String();
        sqlString = sql.replace("[TABLE]", columns.getTableName());
		return sqlReplaceChain.getReplacedSql(sqlString, columns);
	}

}
