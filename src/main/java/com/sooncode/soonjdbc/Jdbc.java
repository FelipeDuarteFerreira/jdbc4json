package com.sooncode.soonjdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.bean.DbBeanCache;
import com.sooncode.soonjdbc.constant.DateFormat;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.verification.SqlVerification;
import com.sooncode.soonjdbc.util.T2E;

/**
 * 执行SQL语句核心类
 * 
 * @author hechenwe@gmail.com
 *
 */

public class Jdbc {

	public final static Log logger = LogFactory.getLog(Jdbc.class);
	private JdbcTemplate jdbcTemplate;

	public Jdbc() {

	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 执行更新(包含添加,删除,修改)
	 * 
	 * @param parameter
	 * @return
	 */
	public long update(final Parameter parameter) {
		if (parameter == null) {
			return 0L;
		}

		if (parameter.isNotException() == false) {
			return 0L;
		}
		String sql = parameter.getReadySql();

		if (SqlVerification.isUpdateSql(sql) == false) {
			return 0L;
		}

		logger.debug("【SoonJdbc SQL】" + parameter.getReadySql());
		logger.debug("【SoonJdbc Parameter】" + parameter.getParams());
		return jdbcTemplate.execute(new ConnectionCallback<Long>() {
			@Override
			public Long doInConnection(Connection con) throws SQLException, DataAccessException {
				return getUpdateResult(con, parameter);
			}
		});

	}

	/**
	 * 批量执行更新(包含添加,删除,修改)
	 * 
	 * @param parameters
	 *            带更新功能的参数模型集合
	 * 
	 * 
	 * @return 成功返回true ,失败返回 false.
	 */
	public boolean updates(List<Parameter> parameters) {
		return true;
	}

	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public List<Map<String, Object>> gets(final Parameter parameter) {
		if (SqlVerification.isSelectSql(parameter.getReadySql()) == false) {
			return new LinkedList<>();
		}
		logger.debug("【SoonJdbc SQL】" + parameter.getReadySql());
		logger.debug("【SoonJdbc Parameter】" + parameter.getParams());
		return jdbcTemplate.execute(new ConnectionCallback<List<Map<String, Object>>>() {

			@Override
			public List<Map<String, Object>> doInConnection(Connection con) throws SQLException, DataAccessException {

				logger.debug(con.toString());
				PreparedStatement preparedStatement = con.prepareStatement(parameter.getReadySql());
				preparedStatement = preparedStatementSet(preparedStatement, parameter.getParams());

				ResultSet resultSet = preparedStatement.executeQuery();
				List<Map<String, Object>> list = new LinkedList<>();
				while (resultSet.next()) {
					Map<String, Object> map = new LinkedHashMap<>();
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					int columnCount = resultSetMetaData.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						String columnName = resultSetMetaData.getColumnLabel(i).toUpperCase();// 获取别名
						Object columnValue = resultSet.getObject(i);
						map.put(T2E.toField(columnName), columnValue);
					}
					list.add(map);
				}
				return list;
			}
		});

	}

	/**
	 * 执行查询语句
	 * 
	 * @param parameter
	 * @return
	 */
	public Map<String, Object> get(Parameter parameter) {

		if (SqlVerification.isSelectSql(parameter.getReadySql()) == false) {
			return new HashMap<>();
		}
		List<Map<String, Object>> list = gets(parameter);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return new HashMap<>();
		}
	}

	public <T> DbBean getDbBean(final T javaBean) {

		return jdbcTemplate.execute(new ConnectionCallback<DbBean>() {

			@Override
			public DbBean doInConnection(Connection con) throws SQLException, DataAccessException {
				DbBean db = DbBeanCache.getDbBean(con, javaBean);
				return db;
			}
		});

	}

	/**
	 * 执行存储过程
	 * 
	 * @param callName
	 *            存储过程名称
	 * @param ins
	 *            入参
	 * @return 出参
	 */
	public Object executeProcedure(final String callName, final Object... ins) {
		return jdbcTemplate.execute(new ConnectionCallback<Object>() {
			@Override
			public Object doInConnection(Connection con) throws SQLException, DataAccessException {
				String sql = getProcedureSql(callName, ins);
				CallableStatement callableStatement = null;
				// sql 中参数的个数
				int n = ins.length;
				// 创建调用存储过程的预定义SQL语句
				// 创建过程执行器
				callableStatement = con.prepareCall(sql);
				// 设置入参和出参
				for (int i = 1; i <= ins.length; i++) {
					callableStatement.setObject(i, ins[i - 1]);
				}
				if (n - ins.length == 1) {
					callableStatement.registerOutParameter(n, Types.JAVA_OBJECT); // 注册出参
					callableStatement.executeUpdate();
					Object result = callableStatement.getObject(n);
					return result;
				} else if (n == ins.length) { // 没有输出参数
					callableStatement.executeUpdate();
					return null;
				} else { // 参数不匹配
					return null;
				}
			}
		});
	}

	private String getProcedureSql(String callName, Object... paras) {

		String sql = new String();
		sql = sql + "{call " + callName + "(";
		for (int i = 0; i < paras.length; i++) {
			if (i == 0) {
				sql = sql + "?";

			} else {
				sql = sql + ",?";
			}
		}
		sql = sql + ")}";
		return sql;
	}

	private Long getUpdateResult(Connection con, Parameter p) {

		Long n;
		try {
			PreparedStatement ps = con.prepareStatement(p.getReadySql(), Statement.RETURN_GENERATED_KEYS);
			ps = preparedStatementSet(ps, p.getParams());
			n = (long) ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys(); // 自增主键
			if (resultSet.next()) {
				Long id = resultSet.getLong(1);
				return id;
			} else {
				return n;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	public int[] batchInsert(final String sql, final List<Map<Integer, Object>> parameters) {
		
		logger.debug("【SoonJdbc SQL】" + sql);
		logger.debug("【SoonJdbc Parameters】" + parameters);
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return parameters.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map<Integer, Object> parameter = parameters.get(i);
				preparedStatementSet(ps, parameter);
			}
		});
	}

	private PreparedStatement preparedStatementSet(PreparedStatement preparedStatement, Map<Integer, Object> parameter) {

		for (Entry<Integer, Object> en : parameter.entrySet()) {
			Integer index = en.getKey();
			Object obj = en.getValue();
			try {
				if (obj != null) {
					String className = obj.getClass().getName();
					if (className.equals(Date.class.getName())) {
						String d = new SimpleDateFormat(DateFormat.ALL_DATE).format(obj);
						preparedStatement.setString(index, d);
						continue;
					}
				}
				preparedStatement.setObject(index, obj);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		return preparedStatement;

	}
}
