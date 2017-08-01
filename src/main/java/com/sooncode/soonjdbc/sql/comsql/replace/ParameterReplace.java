package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.Columns;

public class ParameterReplace implements SqlReplace{

	 

	@Override
	public Parameter getParameter(Parameter protogenesisParameter, Columns columns, SqlReplaceChainI sqlReplaceChain) {
		String sql = protogenesisParameter.getReadySql();
		sql= sql.replace("[PARAMETERS]",   columns.getParameters());
		protogenesisParameter.setReadySql(sql);
		protogenesisParameter.addParameters(columns.getAllParameter());
		return sqlReplaceChain.getReplacedSql(protogenesisParameter, columns);
	}

}
