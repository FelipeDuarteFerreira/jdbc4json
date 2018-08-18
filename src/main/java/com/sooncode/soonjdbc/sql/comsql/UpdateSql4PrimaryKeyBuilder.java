package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.PrimaryKeyReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SetParametersReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;
import com.sooncode.soonjdbc.util.DbModel;

public class UpdateSql4PrimaryKeyBuilder implements SqlBuilder {
	public static final String UPDATE_SQL = "UPDATE [TABLE] SET [SET_PARAMETERS] WHERE [CONDITION]";
	@Override
	public Parameter getParameter(DbModel dbModel) {
		Columns columns = new Columns(dbModel);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace()).addSqlReplace(new SetParametersReplace()).addSqlReplace(new PrimaryKeyReplace());

		Parameter p = new Parameter();
		p.setReadySql(UPDATE_SQL);
		p = src.getReplacedSql(p, columns);
		return p;
	}

}
