package com.sooncode.soonjdbc.dao.tabletype;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;

public class OneTable extends TableType {

	@Override
	public Page getPage(long pageNum, long pageSize,Conditions conditions, Jdbc jdbc) {
		 
		Page page = new Page();
		Page onesPage = queryService.getOnes(pageNum, pageSize, conditions, jdbc);
		page = queryService.clonePage(page, onesPage);
		return page;
	}

}
