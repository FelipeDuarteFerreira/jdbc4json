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
import com.sooncode.soonjdbc.table.DbBean;
import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.T2E;

/**
 * AdvancedDbService
 * 
 * @author hechenwe@gmail.com
 * 
 */

public class AdvancedDbService {

	private QueryService queryService = new QueryService();

	private Jdbc jdbc;

	public Jdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public AdvancedDbService() {

	}

	public AdvancedDbService(Jdbc jdbc) {
		this.jdbc = jdbc;
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

	 

}