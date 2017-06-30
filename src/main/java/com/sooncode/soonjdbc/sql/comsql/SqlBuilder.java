package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.sql.Parameter;

public interface SqlBuilder {

	public Parameter getParameter (DbBean dbBean);
	
}
