package com.sooncode.soonjdbc.sql.comsql.replace;

import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.Columns;

public class SqlReplaceChain implements SqlReplaceChainI{

	private List<SqlReplace> sqlReplaces = new ArrayList<>();
	 
	 
	private int index = 0;
	 
	
	public  SqlReplaceChain addSqlReplace(  SqlReplace sqlReplace ){
		sqlReplaces.add(sqlReplace);
		return this;
	}
	
	
	
	@Override
	public  Parameter getReplacedSql(Parameter protogenesisParameter, Columns columns) {
		 
		
		if(index == sqlReplaces.size()){
			return protogenesisParameter;
		}
		
		SqlReplace sr = sqlReplaces.get(index);
		index ++;
		return sr.getParameter(protogenesisParameter, columns, this);
	}
	
	
 
	 
}
