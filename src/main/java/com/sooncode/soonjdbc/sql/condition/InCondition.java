package com.sooncode.soonjdbc.sql.condition;

import java.util.LinkedList;
import java.util.List;

import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.T2E;

public class InCondition extends Condition{

	
	private static final String SQL_SLICE = " AND [COLUMN][CONDITION_SIGN]([VALUES])";
	
	@Override
	public SqlAndParameter getSqlSlice() {
        List<Object> values = new LinkedList<>();
		String valuesSql = new String();  
		for ( int i = 0; i < this.getValues().length; i++) {
			valuesSql  = valuesSql  + STRING.QUESTION + STRING.COMMA;;
			values.add(this.getValues()[i]);
		}
		int endIndex = valuesSql.lastIndexOf(STRING.COMMA);
		valuesSql = valuesSql.substring(0, endIndex);
		
		String sqlSlice = 
	    SQL_SLICE.replace("[COLUMN]", T2E.toColumn(this.getKey()))
				 .replace("[CONDITION_SIGN]", this.getConditionSign())
				 .replace("[VALUES]", valuesSql); 
		SqlAndParameter sap = new SqlAndParameter();
		sap.setSqlSlice(sqlSlice);
		sap.setValues(values);
		return sap;
		
	}

	 
}
