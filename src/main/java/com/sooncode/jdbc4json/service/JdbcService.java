package com.sooncode.jdbc4json.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sooncode.jdbc4json.dao.JdbcDao;
import com.sooncode.jdbc4json.page.Page;
import com.sooncode.jdbc4json.sql.condition.Conditions;

/**
 * Jdbc Dao 服务
 * 
 * @author pc
 * 
 */
public class JdbcService   {

	public final static Logger logger = Logger.getLogger("JdbcDao.class");

	/**
	 * 数据处理对象JDBC
	 */
	private JdbcDao jdbcDao;

	 

	public JdbcDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public Page getPage(long pageNum, long pageSize, Conditions conditions) {
		return jdbcDao.getPage(pageNum, pageSize, conditions);
	}
 
	public <T> long save(T javaBean) {
		return jdbcDao.save(javaBean);
	}

	public <T> long saveOrUpdate(T javaBean) {
		return jdbcDao.saveOrUpdate(javaBean);
	}

	public <T> long update(T javaBean) {
		return jdbcDao.update(javaBean);
	}

	public <T> long delete(T javaBean) {
		return jdbcDao.delete(javaBean);
	}

	public long count(String key, Conditions conditions) {
		return jdbcDao.count(key, conditions);
	}

	public <T> List<T> gets(T javaBean) {
		return jdbcDao.gets(javaBean);
	}

	public <T> long count(String key, T javaBean) {
		return jdbcDao.count(key, javaBean);
	}

}