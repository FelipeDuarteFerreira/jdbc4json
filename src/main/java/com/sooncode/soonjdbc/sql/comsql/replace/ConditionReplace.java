package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.Columns;

public class ConditionReplace implements SqlReplace{

	 

	@Override
	public Parameter getParameter(Parameter protogenesisParameter, Columns columns, SqlReplaceChainI sqlReplaceChain) {
		String sql = protogenesisParameter.getReadySql();
		sql = sql.replace("[CONDITION]", columns.getCondition());
		protogenesisParameter.setReadySql(sql);
		protogenesisParameter.addParameter(columns.getParameter());
		return sqlReplaceChain.getReplacedSql(protogenesisParameter, columns);
	}

}
