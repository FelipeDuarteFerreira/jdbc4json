package com.sooncode.soonjdbc.sql.condition;

import java.util.LinkedList;
import java.util.List;

import com.sooncode.soonjdbc.constant.DATE_FORMAT;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.T2E;

public class BetweenCondition extends Condition {

	@Override
	public SqlAndParameter getSqlSlice() {
		 String sqlSlice = new String();
		
		if(this.other != null){
			  DATE_FORMAT df = (DATE_FORMAT) this.other;
			  sqlSlice = SQL_KEY.AND +  DateFormatCondition.getDateFormatString(T2E.toColumn(this.key), df)   + this.conditionSign +  DateFormatCondition.getDateFormatString(SQL_KEY.QUESTION, df)   + SQL_KEY.AND +  DateFormatCondition.getDateFormatString(SQL_KEY.QUESTION, df)  + STRING.SPACING;
		}else{
			sqlSlice = SQL_KEY.AND +   T2E.toColumn(this.key) + this.conditionSign + SQL_KEY.QUESTION + SQL_KEY.AND + SQL_KEY.QUESTION + STRING.SPACING;
		}
		
		List<Object> values = new LinkedList<>();
		values.add(this.values[0]);
		values.add(this.values[1]);
		SqlAndParameter sap = new SqlAndParameter();
		sap.setSqlSlice(sqlSlice);
		sap.setValues(values);
		return sap;
	}

	 

}
