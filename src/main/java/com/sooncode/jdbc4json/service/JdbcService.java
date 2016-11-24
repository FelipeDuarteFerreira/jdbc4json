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

	@Override
	public <T> long save(T javaBean) {
		return jdbcDao.save(javaBean);
	}

	@Override
	public <T> boolean saves(T[] javaBeans) {
		return jdbcDao.saves(javaBeans);
	}

	@Override
	public <T> long saveOrUpdate(T javaBean) {
		return jdbcDao.saveOrUpdate(javaBean);
	}

	@Override
	public <T> long update(T javaBean) {
		return jdbcDao.update(javaBean);
	}

	@Override
	public <T> long delete(T javaBean) {
		return jdbcDao.delete(javaBean);
	}

	@Override
	public long count(String key, Conditions conditions) {
		return jdbcDao.count(key, conditions);
	}

	@Override
	public <T> T get(Conditions conditions, Class<T> javaBeanClass) {
		return jdbcDao.get(conditions, javaBeanClass);
	}

	@Override
	public <T> T get(T javaBean) {
		return jdbcDao.get(javaBean);
	}

	@Override
	public <T> T get(JsonBean jsonBean, Class<T> javaBeanClass) {
		return jdbcDao.get(jsonBean, javaBeanClass);
	}

	@Override
	public <T> List<T> gets(Conditions conditions, Class<T> javaBeanClass) {
		return jdbcDao.gets(conditions, javaBeanClass);
	}

	@Override
	public <T> List<T> gets(JsonBean jsonBean, Class<T> javaBeanClass) {
		return jdbcDao.gets(jsonBean, javaBeanClass);
	}

	@Override
	public <T> List<T> gets(T javaBean) {
		return jdbcDao.gets(javaBean);
	}

	@Override
	public long count(String key, JsonBean jsonBean) {
		return jdbcDao.count(key, jsonBean);
	}

	@Override
	public <T> long count(String key, T javaBean) {
		return jdbcDao.count(key, javaBean);
	}

}