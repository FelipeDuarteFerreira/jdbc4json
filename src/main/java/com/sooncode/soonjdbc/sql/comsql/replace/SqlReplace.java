package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.comsql.Columns;

public interface SqlReplace {
 
	public String getReplacedSql ( String sql ,Columns columns,SqlReplaceChainI sqlReplaceChain);
}
