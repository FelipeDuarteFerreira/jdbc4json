package com.sooncode.soonjdbc.dao.tabletype;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.dao.QueryService;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;

public abstract class TableType {
	protected QueryService queryService = new QueryService();

	public abstract Page getPage(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc);
}
