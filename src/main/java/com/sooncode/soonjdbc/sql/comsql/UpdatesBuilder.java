package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.SetParametersReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;

/**
 * 批量更新
 * @author hechenwe@gmail.com
 *
 */
public class UpdatesBuilder implements SqlBuilder {
	public static final String UPDATE_SQL = "UPDATE [TABLE] SET [SET_PARAMETERS] WHERE ";
	@Override
	public Parameter getParameter(DbBean dbBean) {
		Columns columns = new Columns(dbBean);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace())
		   .addSqlReplace(new SetParametersReplace());
		Parameter p = new Parameter();
		p.setReadySql(UPDATE_SQL);
		p = src.getReplacedSql(p, columns);
		return p;
	}

}
