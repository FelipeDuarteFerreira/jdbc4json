package com.sooncode.soonjdbc.sql.condition;

import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.T2E;

public class EqualCondition extends Condition {

	@Override
	public SqlAndParameter getSqlSlice() {
		String sqlSlice = SQL_KEY.AND + T2E.toColumn(this.key) + STRING.SPACING  + this.conditionSign + SQL_KEY.QUESTION;
		SqlAndParameter sap = new SqlAndParameter();
		sap.setSqlSlice(sqlSlice);
		sap.setValue(this.val);
		return sap;
	}

	 

}
