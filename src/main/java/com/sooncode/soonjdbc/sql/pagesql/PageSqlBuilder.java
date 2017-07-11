package com.sooncode.soonjdbc.sql.pagesql;

import com.sooncode.soonjdbc.sql.comsql.Columns;

public interface PageSqlBuilder {
	public PageSqlAndParameter getPageSqlAndParameter(long pageNum, long pageSize, Columns columns);
}
