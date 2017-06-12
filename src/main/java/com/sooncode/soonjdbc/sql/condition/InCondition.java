package com.sooncode.soonjdbc.sql.condition;

import java.util.LinkedList;
import java.util.List;

import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.T2E;

public class InCondition extends Condition{

	@Override
	public SqlAndParameter getSqlSlice() {
        List<Object> values = new LinkedList<>();
		String sql  = SQL_KEY.L_BRACKET;// "(";
		for (int i = 0; i < this.getValues().length; i++) {
			if (i != 0) {
				sql  = sql  + STRING.SPACING + STRING.COMMA + STRING.QUESTION + STRING.SPACING;
			} else {
				sql  = sql  + STRING.QUESTION + STRING.SPACING;
			}
			values.add(this.getValues()[i]);
		}
		sql  = sql  + SQL_KEY.R_BRACKET + STRING.SPACING;// ")
		String sqlSlice =  SQL_KEY.AND + T2E.toColumn(this.getKey()) + STRING.SPACING + this.getConditionSign() + sql;
		SqlAndParameter sap = new SqlAndParameter();
		sap.setSqlSlice(sqlSlice);
		sap.setValues(values);
		return sap;
		
	}

	 
}
