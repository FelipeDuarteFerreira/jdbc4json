package com.sooncode.soonjdbc.service;

import java.util.List;

import com.sooncode.soonjdbc.dao.JdbcDao;
import com.sooncode.soonjdbc.dao.polymerization.Polymerization;
import com.sooncode.soonjdbc.dao.polymerization.PolymerizationModel;
import com.sooncode.soonjdbc.page.Many2Many;
import com.sooncode.soonjdbc.page.One2Many;
import com.sooncode.soonjdbc.page.One2One;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;

/**
 * Jdbc Service  
 * 
 * @author hechenwe@gmail.com
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
	public <L,M,R> Many2Many<L,M,R> getMany2Many(Object left, Object... others) {
		return this.jdbcDao.getMany2Many(left,others);
	}
	public <L,M,R> Many2Many<L,M,R> getMany2Many(Conditions conditions) {
		return this.jdbcDao.getMany2Many(conditions);
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

	public <T> int[] saves(List<T> javaBeans) {
		return jdbcDao.saves(javaBeans);
	}

	public <T> long saveOrUpdate(T javaBean) {
		return jdbcDao.saveOrUpdate(javaBean);
	}

	public <T> long update(T javaBean) {
		return jdbcDao.update(javaBean);
	}

	public <T> long update(T model, Conditions conditions) {
		return jdbcDao.updates(model, conditions);
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

	public <E> List<PolymerizationModel<E>> polymerization(Polymerization Polymerization, Conditions conditions, String key, String... fields) {
		return jdbcDao.polymerization(Polymerization, conditions, key, fields);
	}
	
	public <T> T polymerization(Polymerization Polymerization, Conditions conditions, String key) {
		 
		return jdbcDao.polymerization(Polymerization, conditions, key);
		
	}
	
	public <T, E> T polymerization (Polymerization Polymerization, E entity, String key) {
		return jdbcDao.polymerization(Polymerization, entity, key);
	}
}