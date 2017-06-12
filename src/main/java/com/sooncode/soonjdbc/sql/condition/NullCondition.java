package com.sooncode.soonjdbc.sql.condition;

import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.T2E;

public class NullCondition extends Condition {

	@Override
	public SqlAndParameter getSqlSlice() {
		
		String sqlSlice = SQL_KEY.AND + T2E.toColumn(key) + this.conditionSign  + STRING.SPACING;
		SqlAndParameter sap = new SqlAndParameter();
		sap.setSqlSlice(sqlSlice);
		return sap;
	}

	 

}
