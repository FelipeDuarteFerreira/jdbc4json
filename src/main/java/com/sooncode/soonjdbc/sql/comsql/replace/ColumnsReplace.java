package com.sooncode.soonjdbc.sql.comsql.replace;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.Columns;

public class ColumnsReplace implements SqlReplace {

	@Override
	public Parameter getParameter(Parameter protogenesisParameter, Columns columns, SqlReplaceChainI sqlReplaceChain) {
		String sql = protogenesisParameter.getReadySql();
		sql = sql.replace("[COLUMNS]", columns.getColumns());
		protogenesisParameter.setReadySql(sql);
		return sqlReplaceChain.getReplacedSql(protogenesisParameter, columns);
	}

}
