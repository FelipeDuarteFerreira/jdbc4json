package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.Columns;

public interface SqlReplace {
 
	public Parameter getParameter ( Parameter protogenesisParameter ,Columns columns,SqlReplaceChainI sqlReplaceChain);
}
