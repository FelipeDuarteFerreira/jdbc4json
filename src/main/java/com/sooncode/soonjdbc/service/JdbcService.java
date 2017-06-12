package com.sooncode.soonjdbc.service;

import java.util.List;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.dao.JdbcDao;
import com.sooncode.soonjdbc.page.One2Many;
import com.sooncode.soonjdbc.page.One2One;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.util.T2E;

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

	public One2One getOne2One(Conditions conditions) {
		return this.jdbcDao.getOne2One(conditions);
	}

	public One2One getOne2One(Object left, Object... others) {
		return this.jdbcDao.getOne2One(left, others);
	}

	public <L, R> One2Many<L, R> getOne2Many(Conditions conditions) {
		return this.jdbcDao.getOne2Many(conditions);
	}

	public <L, R> One2Many<L, R> getOne2Many(Object left, Object... others) {
		return this.jdbcDao.getOne2Many(left, others);
	}

	public Page getPage(long pageNum, long pageSize, Object left, Object... other) {
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

	/**
	 * 批量删除
	 * 
	 * @param javaBean
	 * @return
	 */
	public <T> long deletes(final T javaBean) {

		return jdbcDao.deletes(javaBean);

	}

	/**
	 * 批量删除
	 * 
	 * @param javaBean
	 * @return
	 */
	public <T> long deletes(final Conditions conditions) {

		return jdbcDao.deletes(conditions);

	}

	public long count(String key, Conditions conditions) {
		return jdbcDao.count(key, conditions);
	}

	public <T> List<T> gets(T javaBean) {
		return jdbcDao.gets(javaBean);
	}

	public <T> List<T> gets(Conditions conditions) {
		return jdbcDao.gets(conditions);
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

	public <T, E> T max(String key, E javaBean) {
		return jdbcDao.max(key, javaBean);
	}

	public <T> T max(String key, Conditions conditions) {
		return jdbcDao.max(key, conditions);
	}

	public <T> T min(String key, Conditions conditions) {
		return jdbcDao.min(key, conditions);
	}

	public <T, E> T min(String key, E javaBean) {
		return jdbcDao.min(key, javaBean);
	}

	public Object sum(String key, Conditions conditions) {
		return jdbcDao.sum(key, conditions);
	}

	public <E> Object sum(String key, E javaBean) {
		return jdbcDao.sum(key, javaBean);
	}

	public Object avg(String key, Conditions conditions) {
		return jdbcDao.avg(key, conditions);
	}

	public <E> Object avg(String key, E javaBean) {
		return jdbcDao.avg(key, javaBean);
	}
}