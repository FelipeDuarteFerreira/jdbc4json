package com.sooncode.soonjdbc.sql.condition;

import java.util.List;

import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.sql.comsql.SqlJointer;
import com.sooncode.soonjdbc.util.T2E;

public class InCondition extends Condition{

	
	private static final String SQL_SLICE = " AND [COLUMN][CONDITION_SIGN]([VALUES])";
	
	@Override
	public SqlAndParameter getSqlSlice() {
        List<Object> values = this.getValues();
		String valuesSql =  SqlJointer.join(values.size(), STRING.QUESTION, STRING.COMMA);
		 
		String sqlSlice = 
	    SQL_SLICE.replace("[COLUMN]", T2E.toColumn(this.getKey()))
				 .replace("[CONDITION_SIGN]", this.getConditionSign())
				 .replace("[VALUES]", valuesSql); 
		SqlAndParameter sap = new SqlAndParameter();
		sap.setSqlSlice(sqlSlice);
		sap.addValues(values);
		return sap;
		
	}

	 
}
