package com.sooncode.soonjdbc.dao.tabletype;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;

public class One2OneTable extends TableType {

	@Override
	public Page getPage(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		Page page = new Page();
		Page one2onesPage = queryService.getOne2Ones(pageNum, pageSize, conditions, jdbc);
		page = queryService.clonePage(page, one2onesPage);
		return page;
	}

}
