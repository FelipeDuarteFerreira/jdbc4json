package com.sooncode.soonjdbc.sql.comsql.replace;

import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.sql.comsql.Columns;

public class SqlReplaceChain implements SqlReplaceChainI{

	private static List<SqlReplace> sqlReplaces = new ArrayList<>();
	 
	 
	private int index = 0;
	 
	
	public  SqlReplaceChain addSqlReplace(  SqlReplace sqlReplace ){
		sqlReplaces.add(sqlReplace);
		return this;
	}
	
	
	
	@Override
	public  String getReplacedSql(String sql, Columns columns) {
		 
		
		if(index >= sqlReplaces.size()){
			return sql;
		}
		
		SqlReplace sr = sqlReplaces.get(index);
		index ++;
		return sr.getReplacedSql(sql, columns, this);
	}
	
	
	 
 
	 
}
