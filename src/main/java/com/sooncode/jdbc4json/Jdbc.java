package com.sooncode.jdbc4json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sooncode.jdbc4json.bean.DbBean;
import com.sooncode.jdbc4json.bean.DbBeanCache;
import com.sooncode.jdbc4json.bean.JsonBean;
import com.sooncode.jdbc4json.constant.DATE_FORMAT;
import com.sooncode.jdbc4json.reflect.RObject;
import com.sooncode.jdbc4json.sql.Parameter;
import com.sooncode.jdbc4json.sql.verification.SqlVerification;
import com.sooncode.jdbc4json.util.T2E;

/**
 * 执行SQL语句核心类
 * 
 * @author pc
 *
 */

@Repository
public class Jdbc {

	public final static Logger logger = Logger.getLogger("Jdbc.class");

	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	 * 执行查询语句(可能有多条记录)。 可防止SQL注入，推荐使用。
	 * 
	 * @parameter 参数模型
	 * @return List
	 */
	public List<Map<String, Object>> gets(final Parameter parameter) {
		if (SqlVerification.isSelectSql(parameter.getReadySql()) == false) {
			return new LinkedList<>();
		}
        logger.debug("【SQL】"+parameter.getFormatSql());
		return jdbcTemplate.execute(new ConnectionCallback<List<Map<String, Object>>>() {

			@Override
			public List<Map<String, Object>> doInConnection(Connection con) throws SQLException, DataAccessException {
				PreparedStatement preparedStatement = con.prepareStatement(parameter.getReadySql());
				preparedStatement = preparedStatementSet(preparedStatement, parameter);

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
	 * 执行查询语句 (只有一条返回记录)。 可防止SQL注入，推荐使用。
	 * 
	 * @param sql可执行SQL
	 * @return map 记录数量不为1时返回空Map.
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

	public <T> DbBean getDbBean  (final T javaBean) {
		
		return jdbcTemplate.execute(new ConnectionCallback<DbBean>() {

			@Override
			public DbBean doInConnection(Connection con) throws SQLException, DataAccessException {
				DbBean	db = DbBeanCache.getDbBean(con, javaBean); 
				return db;
			}
		});
		
		
		
	}
	public <T> DbBean getDbBean  (final JsonBean jsonBean) {
		
		return jdbcTemplate.execute(new ConnectionCallback<DbBean>() {
			@Override
			public DbBean doInConnection(Connection con) throws SQLException, DataAccessException {
				String className = jsonBean.getClassName();
				RObject<?> rObj = new RObject<>(className);
				DbBean	db = DbBeanCache.getDbBean(con, rObj.getObject()); 
				return db;
			}
		});
		
		
		
	}

	 
	private Long getUpdateResult(Connection con, Parameter p) {

		Long n;
		try {
			PreparedStatement ps = con.prepareStatement(p.getReadySql(), Statement.RETURN_GENERATED_KEYS);
			ps = preparedStatementSet(ps, p);
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

	private PreparedStatement preparedStatementSet(PreparedStatement preparedStatement, Parameter parameter) {

		for (Entry<Integer, Object> en : parameter.getParams().entrySet()) {
			Integer index = en.getKey();
			Object obj = en.getValue();
			String className = obj.getClass().getName();
			try {
				if (className.equals(Date.class.getName())) {
					String d = new SimpleDateFormat(DATE_FORMAT.ALL_DATE).format(obj);
					preparedStatement.setString(index, d);
				} else {
					preparedStatement.setObject(index, obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		return preparedStatement;

	}
}
