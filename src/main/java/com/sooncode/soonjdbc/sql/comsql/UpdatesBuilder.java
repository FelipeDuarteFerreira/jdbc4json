package com.sooncode.soonjdbc.sql.comsql;

import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.replace.SetParametersReplace;
import com.sooncode.soonjdbc.sql.comsql.replace.SqlReplaceChain;
import com.sooncode.soonjdbc.sql.comsql.replace.TableNameReplace;
import com.sooncode.soonjdbc.util.DbModel;

/**
 * 批量更新
 * @author hechenwe@gmail.com
 *
 */
public class UpdatesBuilder implements SqlBuilder {
	public static final String UPDATE_SQL = "UPDATE [TABLE] SET [SET_PARAMETERS] WHERE 1=1 ";
	@Override
	public Parameter getParameter(DbModel dbModel) {
		Columns columns = new Columns(dbModel);
		SqlReplaceChain src = new SqlReplaceChain();
		src.addSqlReplace(new TableNameReplace())
		   .addSqlReplace(new SetParametersReplace());
		Parameter p = new Parameter();
		p.setReadySql(UPDATE_SQL);
		p = src.getReplacedSql(p, columns);
		return p;
	}

}
