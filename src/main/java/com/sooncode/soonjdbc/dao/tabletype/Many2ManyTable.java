package com.sooncode.soonjdbc.dao.tabletype;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;

public class Many2ManyTable extends TableType {

	@Override
	public Page getPage(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		Page page = new Page();
		Page many2ManysPage = queryService.getMany2Manys(pageNum, pageSize, conditions, jdbc);
		page = queryService.clonePage(page, many2ManysPage);
		return page;
	}

}
