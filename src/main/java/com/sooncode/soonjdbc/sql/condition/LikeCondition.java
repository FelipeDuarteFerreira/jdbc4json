package com.sooncode.soonjdbc.sql.condition;

import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;
import com.sooncode.soonjdbc.util.T2E;

public class LikeCondition extends Condition {

	private static final String SQL_SLICE = " AND [COLUMN] LIKE ?";

	@Override
	public SqlAndParameter getSqlSlice() {
		String sqlSlice = new String();
		String key = this.getKey();
		Object val = this.getVal();
		SqlAndParameter sap = new SqlAndParameter();
		if (val == null) {
			sap.setSqlSlice(sqlSlice);
			sap.setValue(null);
			return sap;
		}
		String conditionSign = this.conditionSign;
		sqlSlice = SQL_SLICE.replace("[COLUMN]", T2E.toColumn(key));
		Object value;
		if (conditionSign.equals(SQL_KEY.LIKE)) {
			value = STRING.PERCENT + val + STRING.PERCENT;
		} else if (conditionSign.equals(LikeSign.L_LIKE.toString())) {
			value = val + STRING.PERCENT;
		} else {
			value = STRING.PERCENT + val;
		}
		sap.setSqlSlice(sqlSlice);
		sap.setValue(value);
		return sap;
	}

}
