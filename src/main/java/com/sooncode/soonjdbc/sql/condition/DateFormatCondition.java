package com.sooncode.soonjdbc.sql.condition;

import com.sooncode.soonjdbc.constant.DATE_FORMAT;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.T2E;

public class DateFormatCondition extends Condition {

	@Override
	public SqlAndParameter getSqlSlice() {
		String sql = new String();
		DATE_FORMAT df = (DATE_FORMAT) this.getOther();
		sql = sql + SQL_KEY.AND + getDateFormatString(T2E.toColumn(this.getKey()), df) + this.getConditionSign() + getDateFormatString("?", df);
		SqlAndParameter sap = new SqlAndParameter() ;
		sap.setSqlSlice(sql);
		sap.addValues(this.getValues());
		return sap;
	}
	
	public static String getDateFormatString(String key, DATE_FORMAT dateFormat) {

		String str = STRING.SPACING + SQL_KEY.DATE_FORMAT + "(" + key + "," + dateFormat.getFormatString() + ")" + STRING.SPACING;
		return str;
	}

}
