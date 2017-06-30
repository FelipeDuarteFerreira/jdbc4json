package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.SQL_TEMPLATE;
import com.sooncode.soonjdbc.sql.comsql.replace.ColumnsReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.ParameterReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;

public class InsertSqlBuilder implements SqlBuilder {

	

	@Override
	public Parameter getPreparedSql(DbBean dbBean) {
		Columns c = new Columns(dbBean);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace()).addSqlReplace(new ColumnsReplace()).addSqlReplace(new ParameterReplace());
		String sql = src.getReplacedSql(SQL_TEMPLATE.INSERT_SQL, c);
		Parameter ps = new Parameter();
		ps.setReadySql(sql);;
		ps.addParameter(c.getParameter());
		return ps;
	}

}
