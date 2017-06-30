package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.Columns;

public class PrimaryKeyReplace implements SqlReplace{

	 

	@Override
	public Parameter getParameter(Parameter protogenesisParameter, Columns columns, SqlReplaceChainI sqlReplaceChain) {
		String sql = protogenesisParameter.getReadySql();
		sql = sql.replace("[CONDITION]", columns.getPKCondition());
		protogenesisParameter.setReadySql(sql);
		protogenesisParameter.addParameter(columns.getPkValue());
		return sqlReplaceChain.getReplacedSql(protogenesisParameter, columns);
	}

}
