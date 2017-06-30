package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.Columns;

public interface SqlReplaceChainI {
 
	public Parameter getReplacedSql ( Parameter protogenesisParameter ,Columns columns);
}
