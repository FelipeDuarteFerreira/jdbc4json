package com.sooncode.soonjdbc.dao;

import java.lang.reflect.Field;
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
import com.sooncode.soonjdbc.util.DbModel;
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

	public long save(final DbModel dbModel) {

		DbBean dbBean = jdbc.getDbBean(dbModel);
		InsertSqlBuilder isb = new InsertSqlBuilder();
		Parameter parameter = isb.getParameter(dbBean);
		return jdbc.update(parameter);

	}

	public int[] saves(final List<DbModel> dbModels) {

		String sql = new String();
		List<Map<Integer, Object>> parameters = new ArrayList<>();
		for (DbModel dbModel : dbModels) {
			DbBean dbBean = jdbc.getDbBean(dbModel);
			InsertSqlBuilder isb = new InsertSqlBuilder();
			Parameter parameter = isb.getParameter(dbBean);
			sql = parameter.getReadySql();
			parameters.add(parameter.getParams());
		}
		return jdbc.batchInsert(sql, parameters);

	}

	public <T> long update(final DbModel dbModel) {

		DbBean dbBean = jdbc.getDbBean(dbModel);
		Object pkValue = dbBean.getPrimaryFieldValue();
		if (pkValue == null) {
			throw new PrimaryKeyValueInexistence("primary key value inexistence ! (主键值不存在!)");
		}
		SqlBuilder sqlBuilder = new UpdateSql4PrimaryKeyBuilder();
		Parameter parameter = sqlBuilder.getParameter(dbBean);
		return jdbc.update(parameter);

	}

	public <T> long updates(final Conditions conditions, final T model) {
		DbBean modelDbBean = jdbc.getDbBean(model);

		SqlBuilder sqlBuilder = new UpdatesBuilder();
		Parameter parameter = sqlBuilder.getParameter(modelDbBean);// ComSQL.updates(modelDbBean);
		Parameter wherePara = conditions.getWhereParameter();
		String sql = parameter.getReadySql() + wherePara.getReadySql();
		parameter.addParameters(wherePara.getParams());
		parameter.setReadySql(sql);
		return jdbc.update(parameter);

	}

	public <T> long delete(final DbModel dbModel) {

		DbBean dbBean = jdbc.getDbBean(dbModel);
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
	public <T> long deletes(final DbModel dbModel) {
		Conditions conditions = new Conditions(dbModel);
		return this.deletes(conditions);

	}

	/**
	 * 批量删除
	 * 
	 * @param javaBean
	 * @return
	 */
	public <T> long deletes(final Conditions conditions) {

		DbBean dbBean = jdbc.getDbBean(conditions.getMainDbModel());
		Parameter where = conditions.getWhereParameter();
		Parameter parameter = new DeletetsSqlBuilder().getParameter(dbBean);
		String sql = parameter.getReadySql() + where.getReadySql();
		parameter.setReadySql(sql);
		parameter.setParams(where.getParams());
		return jdbc.update(parameter);

	}

	public long saveOrUpdate(final DbModel dbModel) {

		DbBean dbBean = jdbc.getDbBean(dbModel);
		Object pkValue = dbBean.getPrimaryFieldValue();
		Parameter p = null;
		if (pkValue != null) {

			Class<?> dbModelClass = dbModel.getClass();
			Object model = null;
			try {
				DbModel tempModel = (DbModel) dbModelClass.newInstance();
				tempModel.setJavaBeanClass(dbModel.getJavaBeanClass());
				Field field = dbModelClass.getField(dbBean.getPrimaryField());
				field.setAccessible(true);
				for(Field f :dbModelClass.getDeclaredFields()) {
					if( !f.getName().equals(dbBean.getPrimaryField()) ) {
						f.set(tempModel, new com.sooncode.soonjdbc.util.Field(dbBean.getTableName(), T2E.toColumn(f.getName())));
					}
				}
				model = get(tempModel);
				if (model != null) {
					SqlBuilder sqlBuilder = new UpdateSql4PrimaryKeyBuilder();
					p = sqlBuilder.getParameter(dbBean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (p == null) {
			SqlBuilder sqlBuilder = new InsertSqlBuilder();
			p = sqlBuilder.getParameter(dbBean);
		}
		return jdbc.update(p);

	}

	public <T> List<T> gets(final Conditions conditions) {

		DbBean dbBean = jdbc.getDbBean(conditions.getMainDbModel());
		Parameter where = conditions.getWhereParameter();
		Parameter parameter = new SelectSqlBuilder().getParameter(dbBean);
		String sql = parameter.getReadySql() + where.getReadySql();
		parameter.setReadySql(sql);
		parameter.setParams(where.getParams());
		List<Map<String, Object>> list = jdbc.gets(parameter);
		List<T> tes = Entity.findEntity(list, conditions.getMainDbModel().getJavaBeanClass());
		return tes;

	}

	public <T> List<T> gets(DbModel dbModel) {
		Conditions conditions = new Conditions(dbModel);
		return this.gets(conditions);
	}

	public <T> T get(DbModel dbModel) {
		List<T> list = this.gets(dbModel);
		return (list.size() == 1) ? list.get(0) : null;
	}

	public <T> T get(Conditions conditions) {
		List<T> list = gets(conditions);
		return (list.size() == 1) ? list.get(0) : null;
	}

	public Page getPage(long pageNum, long pageSize, DbModel mainDbModel, DbModel... otherDbModels) {
		Conditions conditions = new Conditions(mainDbModel, otherDbModels);
		return getPage(pageNum, pageSize, conditions);
	}

	public Page getPage(long pageNum, long pageSize, TableRelation TableRelation, DbModel mainDbModel, DbModel... otherDbModels) {
		Conditions conditions = new Conditions(mainDbModel, otherDbModels);
		return getPage(pageNum, pageSize, TableRelation, conditions);
	}

	public One2One getOne2One(DbModel mainDbModel, DbModel... otherDbModels) {
		Conditions conditions = new Conditions(mainDbModel, otherDbModels);
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

	public <L, R> One2Many<L, R> getOne2Many(DbModel mainDbModel, DbModel... otherDbModels) {
		Conditions conditions = new Conditions(mainDbModel, otherDbModels);
		return this.getOne2Many(conditions);
	}

	public <L, R> One2Many<L, R> getOne2Many(Conditions conditions) {
		Page page = this.getPage(1L, Long.MAX_VALUE, conditions);
		One2Many<L, R> o2m = page.getOne2Many();
		return o2m;
	}

	public <L, M, R> Many2Many<L, M, R> getMany2Many(DbModel mainDbModel, DbModel... otherDbModels) {
		Conditions conditions = new Conditions(mainDbModel, otherDbModels);
		return this.getMany2Many(conditions);
	}

	public <L, M, R> Many2Many<L, M, R> getMany2Many(Conditions conditions) {
		Page page = this.getPage(1L, Long.MAX_VALUE, conditions);
		Many2Many<L, M, R> m2m = page.getMany2Many();
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

	public <E> List<PolymerizationModel<E>> polymerization(Polymerization Polymerization, Conditions conditions, com.sooncode.soonjdbc.util.Field key, com.sooncode.soonjdbc.util.Field... fields) {
        String k = key.getPropertyName();
		String column = new String();
		if (fields.length > 0) {
			for (com.sooncode.soonjdbc.util.Field field : fields) {
				column = column + " , " + T2E.toColumn(field.getPropertyName());
			}
		}
		 
		DbBean dbBean = jdbc.getDbBean(conditions.getMainDbModel());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String KEY = new String();

		if (!k.equals("*")) {
			KEY = T2E.toColumn(k);
		} else {
			KEY = k;
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
			E entity = (E) Entity.findEntity(map, conditions.getMainDbModel().getJavaBeanClass());
			pm.setEntity(entity);
			polymerizationModels.add(pm);
		}

		return polymerizationModels;

	}

	/**
	 * 
	 * @param Polymerization
	 * @param conditions
	 * @param key
	 * @return MAX MIN return type int ;
	 */
	@SuppressWarnings("unchecked")
	public <T> T polymerization(Polymerization Polymerization, Conditions conditions, com.sooncode.soonjdbc.util.Field key) {
        String k = key.getPropertyName();
		DbBean dbBean = jdbc.getDbBean(conditions.getMainDbModel());
		String tableName = T2E.toTableName(dbBean.getBeanName());
		String KEY = new String();
		if (k.equals("*")) {
			KEY = k;
		} else {
			KEY = T2E.toColumn(k);
		}
		Parameter parameter = new PolymerizationSqlBuilder().getParameter(tableName, Polymerization, KEY, STRING.NULL_STR);
		parameter.setReadySql(parameter.getReadySql() + conditions.getWhereParameter().getReadySql());
		parameter.setParams(conditions.getWhereParameter().getParams());
		Map<String, Object> map = jdbc.get(parameter);
		T size = (T) map.get("size");
		return (T) (size == null ? 0 : size);

	}

	public <T, E> T polymerization(Polymerization Polymerization, DbModel dbModel, com.sooncode.soonjdbc.util.Field key) {
		Conditions c = new Conditions(dbModel);
		return this.polymerization(Polymerization, c, key);
	}

}