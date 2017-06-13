package com.sooncode.soonjdbc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.dao.tabletype.TableRelation;
import com.sooncode.soonjdbc.dao.tabletype.TableType;
import com.sooncode.soonjdbc.exception.PrimaryKeyValueInexistence;
import com.sooncode.soonjdbc.exception.TableRelationAnalyzeException;
import com.sooncode.soonjdbc.page.One2Many;
import com.sooncode.soonjdbc.page.One2One;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.reflect.RObject;
import com.sooncode.soonjdbc.sql.ComSQL;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.util.T2E;

/**
 * Jdbc Dao  
 * 
 * @author hechenwe@gmail.com
 * 
 */

public class JdbcDao {

	private QueryService queryService = new QueryService();

	private Jdbc jdbc;

	public Jdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public JdbcDao() {

	}

	public JdbcDao(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public <T> long save(final T javaBean) {

		DbBean db = jdbc.getDbBean(javaBean);
		Parameter parameter = ComSQL.insert(db);
		return jdbc.update(parameter);

	}
	
	
	public <T> int []  saves(final List<T> javaBeans) {
		
		String sql = new String ();
		List<Map<Integer,Object>> parameters =  new ArrayList<>();
		for (T javaBean : javaBeans) {
			DbBean db = jdbc.getDbBean(javaBean);
			Parameter parameter = ComSQL.batchInsert(db);
			sql = parameter.getReadySql();
			parameters.add(parameter.getParams());
		}
		return jdbc.batchInsert(sql, parameters);
		
	}

	public <T> long update(final T javaBean) {

		DbBean dbBean = jdbc.getDbBean(javaBean);
		Object pkValue = dbBean.getPrimaryFieldValue();
		if (pkValue == null) {
				throw new PrimaryKeyValueInexistence("primary key value inexistence ! (主键值不存在!)");
		}
		Parameter parameter = ComSQL.update(dbBean);
		return jdbc.update(parameter);

	}
	public <T> long updates( final T model ,  final Conditions conditions) {
		DbBean modelDbBean = jdbc.getDbBean(model);
		Parameter parameter = ComSQL.updates(modelDbBean);
		Parameter wherePara = conditions.getWhereParameter();
		String sql = parameter.getReadySql()+ SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + wherePara.getReadySql();
		parameter.addParameter(wherePara.getParams());
		parameter.setReadySql(sql);
		return jdbc.update(parameter);
		
	}

	public <T> long delete(final T javaBean) {

		DbBean dbBean = jdbc.getDbBean(javaBean);
		Object pkValue = dbBean.getPrimaryFieldValue();
		if (pkValue == null) {
				throw new PrimaryKeyValueInexistence("primary key value inexistence ! (主键值不存在!)");
		}
		String sql = SQL_KEY.DELETE + dbBean.getTableName() + SQL_KEY.WHERE + T2E.toColumn( dbBean.getPrimaryField() ) + SQL_KEY.EQ + SQL_KEY.QUESTION;
		Map<Integer,Object> map = new HashMap<Integer,Object>() ;
		map.put(1, pkValue);
		Parameter parameter = new Parameter();
		parameter.setReadySql(sql);
		parameter.setParams(map);
		return jdbc.update(parameter);

	}
	
	/**
	 * 批量删除
	 * @param javaBean
	 * @return
	 */
	public <T> long deletes(final T javaBean) {
		Conditions conditions = new Conditions(javaBean);
		return this.deletes(conditions);
		
	}
	/**
	 * 批量删除
	 * @param javaBean
	 * @return
	 */
	public <T> long deletes(final Conditions conditions) {
		
		DbBean dbBean = jdbc.getDbBean(conditions.getLeftBean().getJavaBean());
		Parameter where = conditions.getWhereParameter();
		Parameter parameter = new Parameter();
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String sql = SQL_KEY.DELETE + tableName +  SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + where.getReadySql();
		parameter.setReadySql(sql);
		parameter.setParams(where.getParams());
		return jdbc.update(parameter);
		
	}

	 
	public <T> long saveOrUpdate(final T javaBean) {

		DbBean dbBean = jdbc.getDbBean(javaBean);
		Object pkValue = dbBean.getPrimaryFieldValue();
		Parameter p = null ;
		boolean pkValueIsInexistence = ( pkValue != null );
		if (pkValueIsInexistence) {
			RObject<T> rObj = new RObject<>(javaBean.getClass());
			rObj.invokeSetMethod(dbBean.getPrimaryField(), pkValue);
			T tJavaBean =   rObj.getObject();
			List<T> list = gets(tJavaBean);
			boolean haveOneJavaBean = ( list.size() == 1 );
			if (haveOneJavaBean) {
				p = ComSQL.update(dbBean);
			} 
		}  
		
		if(p==null){
			p = ComSQL.insert(dbBean);
		}
		return jdbc.update(p);

	}

	 
	public <T> List<T> gets(final Conditions conditions) {

		String className = conditions.getLeftBean().getClassName();
		RObject<?> rObj = new RObject<>(className);
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		String columns = ComSQL.columns4One(dbBean);
		Parameter where = conditions.getWhereParameter();
		Parameter parameter = new Parameter();
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + tableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + where.getReadySql();
		parameter.setReadySql(sql);
		parameter.setParams(where.getParams());
		List<Map<String, Object>> list = jdbc.gets(parameter);

		List<T> tes = new LinkedList<>();
		for (Map<String, Object> map : list) {
			RObject<T> obj = new RObject<>(className);
			for (Entry<String, Object> en : map.entrySet()) {
				String fieldName = en.getKey();
				Object value = en.getValue();
				obj.invokeSetMethod(fieldName, value);
			}
			tes.add((T) obj.getObject());
		}
		return tes;

	}

	public <T> List<T> gets(T javaBean) {
		Conditions c = new Conditions(javaBean);
		return gets(c);
	}

	public <T> T get(T javaBean) {
		Conditions c = new Conditions(javaBean);
		List<T> list = gets(c);
		T t = null;
		if (list.size() == 1) {
			t = list.get(0);
		}
		return t;
	}

	public <T> T get(Conditions conditions) {
		List<T> list = gets(conditions);
		T t = null;
		if (list.size() == 1) {
			t = list.get(0);
		}
		return t;
	}

	public long count(final String key, final Conditions conditions) {

		RObject<?> rObj = new RObject<>(conditions.getLeftBean().getClassName());
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String sql = "SELECT COUNT(" + key + ") AS SIZE" + " FROM " + tableName + " WHERE 1=1 " + conditions.getWhereParameter().getReadySql();
		Parameter parameter = new Parameter();
		parameter.setReadySql(sql);
		parameter.setParams(conditions.getWhereParameter().getParams());
		Map<String, Object> map = jdbc.get(parameter);
		Long n = (Long) map.get("size");
		return n;

	}
	public Object sum(final String key, final Conditions conditions) {
		String column = T2E.toColumn(key);
		RObject<?> rObj = new RObject<>(conditions.getLeftBean().getClassName());
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String sql = "SELECT SUM(" + column + ") AS SIZE" + " FROM " + tableName + " WHERE 1=1 " + conditions.getWhereParameter().getReadySql();
		Parameter parameter = new Parameter();
		parameter.setReadySql(sql);
		parameter.setParams(conditions.getWhereParameter().getParams());
		Map<String, Object> map = jdbc.get(parameter);
		Object sum = map.get("size");
		return sum;
		
	}
	public <T> Object sum(String key, T javaBean) {
		Conditions c = new Conditions(javaBean);
		Object n = this.sum(key, c);
		return n;
	}
	public Object avg(final String key, final Conditions conditions) {
		String column = T2E.toColumn(key);
		RObject<?> rObj = new RObject<>(conditions.getLeftBean().getClassName());
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String sql = "SELECT AVG(" + column + ") AS SIZE" + " FROM " + tableName + " WHERE 1=1 " + conditions.getWhereParameter().getReadySql();
		Parameter parameter = new Parameter();
		parameter.setReadySql(sql);
		parameter.setParams(conditions.getWhereParameter().getParams());
		Map<String, Object> map = jdbc.get(parameter);
		Object sum = map.get("size");
		return sum;
		
	}
	public <T> Object avg(String key, T javaBean) {
		Conditions c = new Conditions(javaBean);
		Object n = this.avg(key, c);
		return n;
	}
	

	public <T> T max(final String key, final Conditions conditions) {

		RObject<?> rObj = new RObject<>(conditions.getLeftBean().getClassName());
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String sql = "SELECT MAX(" + T2E.toColumn(key) + ") AS MAX" + " FROM " + tableName + " WHERE 1=1 " + conditions.getWhereParameter().getReadySql();
		Parameter parameter = new Parameter();
		parameter.setReadySql(sql);
		parameter.setParams(conditions.getWhereParameter().getParams());
		Map<String, Object> map = jdbc.get(parameter);
		@SuppressWarnings("unchecked")
		T max = (T) map.get("max");
		return max;

	}

	public <T> T min(final String key, final Conditions conditions) {

		RObject<?> rObj = new RObject<>(conditions.getLeftBean().getClassName());
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String sql = "SELECT MIN(" + T2E.toColumn(key) + ") AS MIN" + " FROM " + tableName + " WHERE 1=1 " + conditions.getWhereParameter().getReadySql();
		Parameter parameter = new Parameter();
		parameter.setReadySql(sql);
		parameter.setParams(conditions.getWhereParameter().getParams());
		Map<String, Object> map = jdbc.get(parameter);
		@SuppressWarnings("unchecked")
		T max = (T) map.get("min");
		return max;

	}

	public <T, E> T max(String key, E javaBean) {
		Conditions c = new Conditions(javaBean);
		return this.max(key, c);
	}

	public <T, E> T min(String key, E javaBean) {
		Conditions c = new Conditions(javaBean);
		return this.min(key, c);
	}

	public <T> long count(String key, T javaBean) {
		Conditions c = new Conditions(javaBean);
		long n = this.count(key, c);
		return n;
	}

	public Page getPage(long pageNum, long pageSize, Object leftBean, Object... otherBean) {
		Conditions conditions = new Conditions(leftBean, otherBean);
		return getPage(pageNum, pageSize, conditions);
	}

	public Page getPage(long pageNum, long pageSize, TableRelation TableRelation, Object leftBean, Object... otherBean) {
		Conditions conditions = new Conditions(leftBean, otherBean);
		return getPage(pageNum, pageSize, TableRelation, conditions);
	}

	public One2One getOne2One(Object left, Object... other) {
		Conditions conditions = new Conditions(left, other);
		return this.getOne2One(conditions);
	}

	public One2One getOne2One(Conditions conditions) {
		Page page = this.getPage(1L, 1L, conditions);
		List<One2One> list = page.getOne2One();
		One2One o2o = null;
		if (list.size() == 1) {
			o2o = list.get(0);
		}
		return o2o;
	}

	public <L, R> One2Many<L, R> getOne2Many(Object left, Object... others) {
		Conditions conditions = new Conditions(left, others);
		return this.getOne2Many(conditions);
	}

	public <L, R> One2Many<L, R> getOne2Many(Conditions conditions) {
		Page page = this.getPage(1L, Long.MAX_VALUE, conditions);
		One2Many<L, R> o2m = page.getOne2Many();
		return o2m;
	}

	public Page getPage(long pageNum, long pageSize, Conditions conditions) {

		Page page = new Page();
		List<TableType> nes = queryService.getTableType(jdbc, conditions);

		if (nes.size() == 1) {
			TableType tableType = nes.get(0);
			page = tableType.getPage(pageNum, pageSize, conditions, jdbc);
		} else if (nes.size() > 1) {
			try {
				throw new TableRelationAnalyzeException("数据库表关系分析异常，存在多种关系，无法识别！");
			} catch (TableRelationAnalyzeException e) {
				e.printStackTrace();
			}
		} else if (nes.size() == 0) {
			try {
				throw new TableRelationAnalyzeException("数据库表关系分析异常，没有合适的关系模型！");
			} catch (TableRelationAnalyzeException e) {
				e.printStackTrace();
			}
		}

		return page;

	}

	public Page getPage(long pageNum, long pageSize, TableRelation TableRelation, Conditions conditions) {
		TableType tableType = TableRelation.getTableType();
		return tableType.getPage(pageNum, pageSize, conditions, jdbc);
	}

}