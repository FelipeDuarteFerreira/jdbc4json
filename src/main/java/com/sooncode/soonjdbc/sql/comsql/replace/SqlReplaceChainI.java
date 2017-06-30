package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.comsql.Columns;

public interface SqlReplaceChainI {
 
	public String getReplacedSql ( String sql ,Columns columns);
}
