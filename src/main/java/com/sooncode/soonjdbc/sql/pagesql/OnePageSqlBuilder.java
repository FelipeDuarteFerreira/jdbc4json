package com.sooncode.soonjdbc.sql.pagesql;

import com.sooncode.soonjdbc.sql.comsql.Columns;

public class OnePageSqlBuilder implements PageSqlBuilder{

	
	public static final String SELECT_SQL = "SELECT [COLUMNS] FROM [TABLE] WHERE";
	
	
	 


	@Override
	public PageSqlAndParameter getPageSqlAndParameter(long pageNum, long pageSize, Columns columns) {
		// TODO Auto-generated method stub
		return null;
	}

}
