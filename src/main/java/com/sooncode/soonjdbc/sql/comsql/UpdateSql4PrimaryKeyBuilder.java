package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.PrimaryKeyReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SetParametersReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;

public class UpdateSql4PrimaryKeyBuilder implements SqlBuilder {

	@Override
	public Parameter getParameter(DbBean dbBean) {
		Columns columns = new Columns(dbBean);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace()).addSqlReplace(new SetParametersReplace()).addSqlReplace(new PrimaryKeyReplace());

		Parameter p = new Parameter();
		p.setReadySql(SQL_TEMPLATE.UPDATE_SQL);
		p = src.getReplacedSql(p, columns);
		return p;
	}

}
