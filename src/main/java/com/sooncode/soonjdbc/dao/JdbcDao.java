package com.sooncode.soonjdbc.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sooncode.soonjdbc.Entity;
import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.dao.polymerization.Polymerization;
import com.sooncode.soonjdbc.dao.polymerization.PolymerizationModel;
import com.sooncode.soonjdbc.dao.tabletype.TableRelation;
import com.sooncode.soonjdbc.dao.tabletype.TableType;
import com.sooncode.soonjdbc.exception.PrimaryKeyValueInexistence;
import com.sooncode.soonjdbc.exception.TableRelationAnalyzeException;
import com.sooncode.soonjdbc.page.Many2Many;
import com.sooncode.soonjdbc.page.One2Many;
import com.sooncode.soonjdbc.page.One2One;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.reflect.RObject;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.comsql.DeletetSqlBuilder;
import com.sooncode.soonjdbc.sql.comsql.DeletetsSqlBuilder;
import com.sooncode.soonjdbc.sql.comsql.InsertSqlBuilder;
import com.sooncode.soonjdbc.sql.comsql.PolymerizationSqlBuilder;
import com.sooncode.soonjdbc.sql.comsql.SelectSqlBuilder;
import com.sooncode.soonjdbc.sql.comsql.SqlBuilder;
import com.sooncode.soonjdbc.sql.comsql.UpdateSql4PrimaryKeyBuilder;
import com.sooncode.soonjdbc.sql.comsql.UpdatesBuilder;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.sql.condition.ConditionsBuilderProcess;
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

		DbBean dbBean = jdbc.getDbBean(javaBean);
		InsertSqlBuilder isb = new InsertSqlBuilder();
		Parameter parameter = isb.getParameter(dbBean);
		return jdbc.update(parameter);

	}

	public <T> int[] saves(final List<T> javaBeans) {

		String sql = new String();
		List<Map<Integer, Object>> parameters = new ArrayList<>();
		for (T javaBean : javaBeans) {
			DbBean dbBean = jdbc.getDbBean(javaBean);
			InsertSqlBuilder isb = new InsertSqlBuilder();
			Parameter parameter = isb.getParameter(dbBean);
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
		SqlBuilder sqlBuilder = new UpdateSql4PrimaryKeyBuilder();
		Parameter parameter = sqlBuilder.getParameter(dbBean);
		return jdbc.update(parameter);

	}

	public <T> long updates(final T model, final Conditions conditions) {
		DbBean modelDbBean = jdbc.getDbBean(model);

		SqlBuilder sqlBuilder = new UpdatesBuilder();
		Parameter parameter = sqlBuilder.getParameter(modelDbBean);// ComSQL.updates(modelDbBean);
		Parameter wherePara = conditions.getWhereParameter();
		String sql = parameter.getReadySql() + wherePara.getReadySql();
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
		SqlBuilder sqlBuilder = new DeletetSqlBuilder();
		Parameter parameter = sqlBuilder.getParameter(dbBean);
		return jdbc.update(parameter);

	}

	/**
	 * 批量删除
	 * 
	 * @param javaBean
	 * @return
	 */
	public <T> long deletes(final T javaBean) {
		Conditions conditions = ConditionsBuilderProcess.getConditions(javaBean);
		return this.deletes(conditions);

	}

	/**
	 * 批量删除
	 * 
	 * @param javaBean
	 * @return
	 */
	public <T> long deletes(final Conditions conditions) {

		DbBean dbBean = jdbc.getDbBean(conditions.getLeftBean().getJavaBean());
		Parameter where = conditions.getWhereParameter();
		Parameter parameter = new DeletetsSqlBuilder().getParameter(dbBean);
		String sql = parameter.getReadySql()+ where.getReadySql();
		parameter.setReadySql(sql);
		parameter.setParams(where.getParams());
		return jdbc.update(parameter);

	}

	public <T> long saveOrUpdate(final T javaBean) {

		DbBean dbBean = jdbc.getDbBean(javaBean);
		Object pkValue = dbBean.getPrimaryFieldValue();
		Parameter p = null;
		boolean pkValueIsInexistence = (pkValue != null);
		if (pkValueIsInexistence) {
			RObject<T> rObj = new RObject<>(javaBean.getClass());
			rObj.invokeSetMethod(dbBean.getPrimaryField(), pkValue);
			T tJavaBean = rObj.getObject();
			List<T> list = gets(tJavaBean);
			boolean haveOneJavaBean = (list.size() == 1);
			if (haveOneJavaBean) {
				SqlBuilder sqlBuilder = new UpdateSql4PrimaryKeyBuilder();
				p = sqlBuilder.getParameter(dbBean);
			}
		}

		if (p == null) {
			SqlBuilder sqlBuilder = new InsertSqlBuilder();
			p = sqlBuilder.getParameter(dbBean);
		}
		return jdbc.update(p);

	}

	public <T> List<T> gets(final Conditions conditions) {

		String className = conditions.getLeftBean().getClassName();
		RObject<?> rObj = new RObject<>(className);
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		Parameter where = conditions.getWhereParameter();
		Parameter parameter = new SelectSqlBuilder().getParameter(dbBean);
		String sql = parameter.getReadySql() + where.getReadySql();
		parameter.setReadySql(sql);
		parameter.setParams(where.getParams());
		List<Map<String, Object>> list = jdbc.gets(parameter);
		List<T> tes = Entity.findEntity(list, rObj.getObject().getClass());
		return tes;

	}

	public <T> List<T> gets(T javaBean) {
		Conditions conditions = ConditionsBuilderProcess.getConditions(javaBean);
		return this.gets(conditions);
	}

	public <T> T get(T javaBean) {
		List<T> list = this.gets(javaBean);
		return (list.size() == 1) ? list.get(0) : null;
	}

	public <T> T get(Conditions conditions) {
		List<T> list = gets(conditions);
		return (list.size() == 1) ? list.get(0) : null;
	}

	public Page getPage(long pageNum, long pageSize, Object leftBean, Object... otherBean) {
		Conditions conditions = ConditionsBuilderProcess.getConditions(leftBean, otherBean);
		return getPage(pageNum, pageSize, conditions);
	}

	public Page getPage(long pageNum, long pageSize, TableRelation TableRelation, Object leftBean, Object... otherBean) {
		Conditions conditions = ConditionsBuilderProcess.getConditions(leftBean, otherBean);
		return getPage(pageNum, pageSize, TableRelation, conditions);
	}

	public One2One getOne2One(Object left, Object... other) {
		Conditions conditions = ConditionsBuilderProcess.getConditions(left, other);
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
		Conditions conditions = ConditionsBuilderProcess.getConditions(left, others);
		return this.getOne2Many(conditions);
	}

	public <L, R> One2Many<L, R> getOne2Many(Conditions conditions) {
		Page page = this.getPage(1L, Long.MAX_VALUE, conditions);
		One2Many<L, R> o2m = page.getOne2Many();
		return o2m;
	}
	public <L,M,R> Many2Many<L,M,R> getMany2Many(Object left, Object... others) {
		Conditions conditions = ConditionsBuilderProcess.getConditions(left, others);
		return this.getMany2Many(conditions);
	}
	
	public <L,M,R> Many2Many<L,M,R> getMany2Many(Conditions conditions){
		Page page = this.getPage(1L, Long.MAX_VALUE, conditions);
		Many2Many<L,M,R> m2m = page.getMany2Many();
		return m2m;
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

	public <E> List<PolymerizationModel<E>> polymerization(Polymerization Polymerization, Conditions conditions, String key, String... fields) {

		String column = new String();
		if (fields.length > 0) {
			for (String field : fields) {
				column = column + " , " + T2E.toColumn(field);
			}
		}
		RObject<?> rObj = new RObject<>(conditions.getLeftBean().getClassName());
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String KEY = new String();

		if (!key.equals("*")) {
			KEY = T2E.toColumn(key);
		} else {
			KEY = key;
		}

		Parameter parameter = new PolymerizationSqlBuilder().getParameter(tableName, Polymerization, KEY, column);
		parameter.setReadySql(parameter.getReadySql() + conditions.getWhereParameter().getReadySql());
		parameter.setParams(conditions.getWhereParameter().getParams());
		List<Map<String, Object>> list = jdbc.gets(parameter);
		List<PolymerizationModel<E>> polymerizationModels = new LinkedList<>();
		for (Map<String, Object> map : list) {
			PolymerizationModel<E> pm = new PolymerizationModel<>();
			pm.setSize(map.get("size"));
			@SuppressWarnings("unchecked")
			E entity = (E) Entity.findEntity(map, rObj.getObject().getClass());
			pm.setEntity(entity);
			polymerizationModels.add(pm);
		}

		return polymerizationModels;

	}

	public <T> T polymerization(Polymerization Polymerization, Conditions conditions, String key) {

		RObject<?> rObj = new RObject<>(conditions.getLeftBean().getClassName());
		DbBean dbBean = jdbc.getDbBean(rObj.getObject());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String KEY = new String();
		if ( key.equals("*")) {
			KEY = key;
		} else {
			KEY = T2E.toColumn(key);
		}
		Parameter parameter = new PolymerizationSqlBuilder().getParameter(tableName, Polymerization, KEY, STRING.NULL_STR);
		parameter.setReadySql(parameter.getReadySql() + conditions.getWhereParameter().getReadySql());
		parameter.setParams(conditions.getWhereParameter().getParams());
		Map<String, Object> map = jdbc.get(parameter);
		@SuppressWarnings("unchecked")
		T size = (T) map.get("size");
		return size;

	}

	public <T, E> T polymerization(Polymerization Polymerization, E entity, String key) {
		Conditions c = ConditionsBuilderProcess.getConditions(entity);
		return this.polymerization(Polymerization, c, key);
	}

}