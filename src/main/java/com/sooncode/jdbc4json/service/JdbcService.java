package com.sooncode.jdbc4json.service;

 

import java.util.List;

import org.apache.log4j.Logger;

import com.sooncode.jdbc4json.bean.JsonBean;
import com.sooncode.jdbc4json.dao.JdbcDao;
import com.sooncode.jdbc4json.dao.JdbcDaoFactory;
import com.sooncode.jdbc4json.sql.condition.Conditions;
import com.sooncode.jdbc4json.util.Page;

/**
 * Jdbc Dao 服务
 * 
 * @author pc
 * 
 */
public class JdbcService implements JdbcServiceInterface {

	public final static Logger logger = Logger.getLogger("JdbcDao.class");

	/**
	 * 数据处理对象JDBC
	 */
	private JdbcDao jdbcDao;

	JdbcService() {
		jdbcDao = JdbcDaoFactory.getJdbcDao();
	}

	JdbcService(String dbKey) {
		jdbcDao = JdbcDaoFactory.getJdbcDao(dbKey);
	}

	public Page getPage(long pageNum, long pageSize, Conditions conditions) {
		return jdbcDao.getPage(pageNum, pageSize, conditions);
	}

	public long save(JsonBean jsonBean) {
		return jdbcDao.save(jsonBean);

	}

	public boolean saves(List<JsonBean> jsonBeans) {

		return jdbcDao.saves(jsonBeans);

	}

	public boolean updates(List<JsonBean> jsonBeans) {
		return jdbcDao.updates(jsonBeans);
	}

	public long saveOrUpdate(JsonBean jsonBean) {
		 return jdbcDao.saveOrUpdate(jsonBean);

	}

	public long update(JsonBean jsonBean) {
		return jdbcDao.update(jsonBean);

	}

	public long delete(JsonBean jsonBean) {
		return jdbcDao.delete(jsonBean);

	}

	public List<JsonBean> gets(Conditions conditions) {

	 return jdbcDao.gets(conditions);
	}
 

}