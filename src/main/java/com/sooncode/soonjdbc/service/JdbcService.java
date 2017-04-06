package com.sooncode.soonjdbc.service;

import java.util.List;


import com.sooncode.soonjdbc.dao.JdbcDao;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;

/**
 * Jdbc Dao 服务
 * 
 * @author pc
 * 
 */
public class JdbcService {

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

	/*public <L, M, R> Page getPage(long pageNum, long pageSize, L left, M middle, R right) {
		return jdbcDao.getPage(pageNum, pageSize, left, middle, right);
	}*/
	
	
	public   Page getPage(long pageNum, long pageSize, Object left, Object... other) {
		return jdbcDao.getPage(pageNum, pageSize, left, other);
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

	public <T> T get(Conditions conditions) {
		return jdbcDao.get(conditions);
	}

	public <T> T get(T javaBean) {
		return jdbcDao.get(javaBean);
	}

	public <T> long count(String key, T javaBean) {
		return jdbcDao.count(key, javaBean);
	}

	
	public <T,E> T max (String key,E javaBean){
		return jdbcDao.max(key, javaBean);
	}
	
	public <T> T  max(  String key,   Conditions conditions) {
		return jdbcDao.max(key,conditions);
	}
	public <T> T  min(  String key,   Conditions conditions) {
		return jdbcDao.min(key,conditions);
	}
	public <T,E> T min (String key,E javaBean){
		return jdbcDao.min(key, javaBean);
	}
}