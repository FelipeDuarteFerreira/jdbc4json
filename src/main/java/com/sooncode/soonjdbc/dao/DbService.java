package com.sooncode.soonjdbc.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sooncode.soonjdbc.Entity;
import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.dao.polymerization.Polymerization;
import com.sooncode.soonjdbc.dao.polymerization.PolymerizationModel;
import com.sooncode.soonjdbc.dao.tabletype.TableType;
import com.sooncode.soonjdbc.exception.TableRelationAnalyzeException;
import com.sooncode.soonjdbc.page.Page;
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
import com.sooncode.soonjdbc.util.DbField;
import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.T2E;

/**
 * DaoService
 * 
 * @author hechenwe@gmail.com
 * 
 */

public class DbService {

	private QueryService queryService = new QueryService();

	private Jdbc jdbc;

	public Jdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public DbService() {

	}

	public DbService(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public long save(final DbModel dbModel) {
		InsertSqlBuilder isb = new InsertSqlBuilder();
		Parameter parameter = isb.getParameter(dbModel);
		return jdbc.update(parameter);

	}

	private int[] saves4batch(final List<DbModel> dbModels) {

		String sql = new String();
		List<Map<Integer, Object>> parameters = new ArrayList<>();

		for (DbModel dbModel : dbModels) {
			InsertSqlBuilder isb = new InsertSqlBuilder();
			Parameter parameter = isb.getParameter(dbModel);
			sql = parameter.getReadySql();
			parameters.add(parameter.getParams());
		}
		return jdbc.batchInsert(sql, parameters);

	}

	public int[] saves(final List<DbModel> dbModels) {

		if (dbModels == null || dbModels.size() == 0) {
			return new int[] {};
		}
		boolean isSameDbModel = true;

		Class<?> clas = dbModels.get(0).getClass();
		for (DbModel dbModel : dbModels) {
			if (clas != dbModel.getClass()) {
				isSameDbModel = false;
				break;
			}
		}

		if (isSameDbModel) {
			return saves4batch(dbModels);
		}

		int[] numbers = new int[dbModels.size()];
		for (int i = 0; i < dbModels.size(); i++) {
			numbers[i] = (int) save(dbModels.get(i));
		}
		return numbers;

	}

	public long update(DbModel dbModel) {

		SqlBuilder sqlBuilder = new UpdateSql4PrimaryKeyBuilder();
		Parameter parameter = sqlBuilder.getParameter(dbModel);
		return jdbc.update(parameter);

	}

	public long updates(Conditions conditions, DbModel valuesModel) {

		SqlBuilder sqlBuilder = new UpdatesBuilder();
		Parameter parameter = sqlBuilder.getParameter(valuesModel);// ComSQL.updates(modelDbBean);
		Parameter wherePara = conditions.getWhereParameter();
		String sql = parameter.getReadySql() + wherePara.getReadySql();
		parameter.addParameters(wherePara.getParams());
		parameter.setReadySql(sql);
		return jdbc.update(parameter);

	}

	public long delete(DbModel dbModel) {

		SqlBuilder sqlBuilder = new DeletetSqlBuilder();
		Parameter parameter = sqlBuilder.getParameter(dbModel);
		return jdbc.update(parameter);

	}

	/**
	 * 批量删除
	 * 
	 * @param javaBean
	 * @return
	 */
	public long deletes(DbModel dbModel) {
		Conditions conditions = new Conditions(dbModel);
		return this.deletes(conditions);

	}

	/**
	 * 批量删除
	 * 
	 * @param javaBean
	 * @return
	 */
	public long deletes(Conditions conditions) {

		Parameter where = conditions.getWhereParameter();
		Parameter parameter = new DeletetsSqlBuilder().getParameter(conditions.getDbModel());
		String sql = parameter.getReadySql() + where.getReadySql();
		parameter.setReadySql(sql);
		parameter.setParams(where.getParams());
		return jdbc.update(parameter);

	}

	public long saveOrUpdate(final DbModel dbModel) {

		List<DbField<Object>> primaryKeys = dbModel.primaryKeys();

		boolean pkValueIsExist = true;

		for (DbField<Object> dbField : primaryKeys) {
			if (dbField.getValue() == null) {
				pkValueIsExist = false;
				break;
			}
		}

		if (pkValueIsExist) {
			try {
				DbModel tempModel = (DbModel) dbModel.getClass().newInstance();
				tempModel.setJavaBeanClass(dbModel.getJavaBeanClass());
				for (DbField<Object> dbField : primaryKeys) {
					Field field = dbModel.getClass().getField(dbField.getPropertyName());
					field.setAccessible(true);
					DbField<Object> pk = new DbField<Object>(dbField.getTableName(), dbField.getColumnName()) {

						@Override
						public Object getValue() {
							return this.value;
						}

						@Override
						public void setValue(Object value) {
							this.value = value;

						}
					};

					pk.setValue(dbField.getValue());
					field.set(tempModel, pk);
					Object existDbModel = this.get(tempModel);
					if (existDbModel != null) {
						return this.update(dbModel);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.save(dbModel);
	}

	public <T> List<T> gets(Conditions conditions) {

		Parameter where = conditions.getWhereParameter();
		Parameter parameter = new SelectSqlBuilder().getParameter(conditions.getDbModel());
		String sql = parameter.getReadySql() + where.getReadySql();
		parameter.setReadySql(sql);
		parameter.setParams(where.getParams());
		List<Map<String, Object>> list = jdbc.gets(parameter);
		List<T> tes = Entity.findEntity(list, conditions.getDbModel().getJavaBeanClass());
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

	public <E> List<PolymerizationModel<E>> polymerization(Polymerization Polymerization, Conditions conditions, DbField<?> key, DbField<?>... fields) {
		String k = key.getPropertyName();
		String column = new String();
		if (fields.length > 0) {
			for (DbField<?> field : fields) {
				column = column + " , " + T2E.toColumn(field.getPropertyName());
			}
		}

		 
		String tableName =conditions.getDbModel().tableName();
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
			E entity = (E) Entity.findEntity(map, conditions.getDbModel().getJavaBeanClass());
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
	public <T> T polymerization(Polymerization Polymerization, Conditions conditions, DbField<?> key) {
		String k = key.getPropertyName();
		String tableName = conditions.getDbModel().tableName();
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

	public <T, E> T polymerization(Polymerization Polymerization, DbModel dbModel, DbField<?> key) {
		Conditions c = new Conditions(dbModel);
		return this.polymerization(Polymerization, c, key);
	}

}