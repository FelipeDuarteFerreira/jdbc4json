package com.sooncode.soonjdbc.sql.verification;

import com.sooncode.soonjdbc.constant.STRING;

/**
 * SQL 验证
 * 
 * @author pc
 *
 */
public class SqlVerification {
	private static final String SELECT = "SELECT"+STRING.SPACING;
	private static final String UPDATE = "UPDATE"+STRING.SPACING;
	private static final String DELETE = "DELETE"+STRING.SPACING;
	private static final String INSERT = "INSERT"+STRING.SPACING;
    /**
     * 是否是查询语句
     * @param sql SQL语句
     * @return true ; false
     */
	public static boolean isSelectSql(String sql) {
		boolean isSelectSql = false;
		if (sql != null) {
			sql = sql.trim().toUpperCase();
			StringBuffer sb = new StringBuffer(sql);
			int n = sb.indexOf(SELECT);
			if (n == 0) {
				isSelectSql =  true;
			}  
		}
		return isSelectSql;
	}
    /**
     * 是否是更新语句
     * @param sql SQL语句
     * @return true ; false
     */
	public static boolean isUpdateSql(String sql) {
		boolean isUpdateSql = false;
		if (sql != null) {
			sql = sql.trim().toUpperCase();
			StringBuffer sb = new StringBuffer(sql);
			int update = sb.indexOf(UPDATE);
			int insert = sb.indexOf(INSERT);
			int delete = sb.indexOf(DELETE);
			if(update + insert + delete == 1 ){
				isUpdateSql = true;
			}
		}
		return isUpdateSql;
	}
}
