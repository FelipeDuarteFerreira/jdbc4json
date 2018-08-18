package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.util.DbModel;

public interface SqlBuilder {

	public Parameter getParameter (DbModel dbModel);
	
}
