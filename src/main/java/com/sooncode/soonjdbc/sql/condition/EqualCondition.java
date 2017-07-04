package com.sooncode.soonjdbc.sql.condition;

import com.sooncode.soonjdbc.util.T2E;

public class EqualCondition extends Condition {

	
	private static final String SQL_SLICE = " AND [COLUMN] [CONDITION_SIGN] ? ";
	
	@Override
	public SqlAndParameter getSqlSlice() {
		String sqlSlice = SQL_SLICE
				.replace("[COLUMN]", T2E.toColumn(this.getKey()))
				.replace("[CONDITION_SIGN]", this.conditionSign);
		SqlAndParameter sap = new SqlAndParameter();
		sap.setSqlSlice(sqlSlice);
		sap.addValue(this.values.get(0));
		return sap;
	}

	 

}
