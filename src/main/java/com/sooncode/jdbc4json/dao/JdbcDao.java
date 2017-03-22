package com.sooncode.jdbc4json.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sooncode.jdbc4json.Jdbc;
import com.sooncode.jdbc4json.bean.DbBean;
import com.sooncode.jdbc4json.bean.ForeignKey;
import com.sooncode.jdbc4json.bean.JsonBean;
import com.sooncode.jdbc4json.constant.SQL_KEY;
import com.sooncode.jdbc4json.constant.STRING;
import com.sooncode.jdbc4json.page.Many2Many;
import com.sooncode.jdbc4json.page.One;
import com.sooncode.jdbc4json.page.One2Many;
import com.sooncode.jdbc4json.page.One2One;
import com.sooncode.jdbc4json.page.Page;

import com.sooncode.jdbc4json.reflect.RObject;
import com.sooncode.jdbc4json.sql.ComSQL;
import com.sooncode.jdbc4json.sql.PageData;
import com.sooncode.jdbc4json.sql.Parameter;
import com.sooncode.jdbc4json.sql.TableRelationAnalyze;
import com.sooncode.jdbc4json.sql.condition.Conditions;
import com.sooncode.jdbc4json.util.T2E;

/**
 * Jdbc Dao 服务
 * 
 * @author pc
 * 
 */
@Repository
public class JdbcDao {

	public final static Logger logger = Logger.getLogger("JdbcDao.class");

	@Autowired
	private Jdbc jdbc;

	public <T> long save(final T javaBean) {

		DbBean db = jdbc.getDbBean(javaBean);
		Parameter parameter = ComSQL.insert(db);
		return jdbc.update(parameter);

	}

	public <T> long update(final T javaBean) {

		DbBean dbBean = jdbc.getDbBean(javaBean);
		Object pkValue = dbBean.getPrimaryFieldValue();
		if (pkValue == null) {
			return 0L;
		}
		Parameter parameter = ComSQL.update(dbBean);
		return jdbc.update(parameter);

	}

	public <T> long delete(final T javaBean) {

		DbBean dbBean = jdbc.getDbBean(javaBean);
		Object pkValue = dbBean.getPrimaryFieldValue();
		if (pkValue == null) {
			return 0L;
		}
		Parameter parameter = ComSQL.delete(dbBean);
		return jdbc.update(parameter);

	}

	@SuppressWarnings("unchecked")
	public <T> long saveOrUpdate(final T javaBean) {

		DbBean dbBean = jdbc.getDbBean(javaBean);
		Object pkValue = dbBean.getPrimaryFieldValue();
		Parameter p = new Parameter();
		if (pkValue != null) {
			RObject<?> rObj = new RObject<>(dbBean.getClassName());
			rObj.invokeSetMethod(dbBean.getPrimaryField(), pkValue);
			List<T> list = gets((T) rObj.getObject());
			if (list.size() == 1) {
				dbBean = jdbc.getDbBean(javaBean);
				p = ComSQL.update(dbBean);
			}

		} else {
			p = ComSQL.insert(dbBean);
		}

		return jdbc.update(p);

	}

	@SuppressWarnings("unchecked")
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
			RObject<?> obj = new RObject<>(className);
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

	public <T> long count(String key, T javaBean) {
		Conditions c = new Conditions(javaBean);
		long n = this.count(key, c);
		return n;
	}

	public <L, M, R> Page getPage(long pageNum, long pageSize, Conditions conditions) {

		String limit = SQL_KEY.LIMIT + (pageNum - 1) * pageSize + SQL_KEY.COMMA + pageSize;

		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean());
		JsonBean[] otherBeans = conditions.getOtherBeans();
		List<DbBean> otherDbBeans = new ArrayList<>();

		if (otherBeans.length > 0) {
			for (JsonBean jBean : otherBeans) {
				DbBean dbBean = jdbc.getDbBean(jBean);
				otherDbBeans.add(dbBean);
			}
		}

		int n = getRelation(leftDbBean, otherDbBeans.toArray(new DbBean[otherBeans.length]));
		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());

		// 1.单表
		if (n == 1) {
			String columns = ComSQL.columns4One(leftDbBean);
			String where = conditions.getWhereParameter().getReadySql() + limit;
			String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + leftTableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + where;
			Parameter p = conditions.getWhereParameter();
			p.setReadySql(sql);
			List<Map<String, Object>> list = jdbc.gets(p);
			List<L> result = findBean(list, leftDbBean);
			One<L> one = new One<>(result);
			String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + where;
			Parameter sizeP = conditions.getWhereParameter();
			sizeP.setReadySql(sizeSql);
			Map<String, Object> map = jdbc.get(sizeP);
			Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
			if (size == null) {
				size = 0L;
			}
			Page pager = new Page(pageNum, pageSize, size, one);
			return pager;

		} else if (n == 2) {// 1对1

			One2One<L, R> one2one = new One2One<>();

			PageData pd = one2one(leftDbBean, otherDbBeans, conditions, limit);
			List<Bean<L>> lBeans = findBean(pd.getList(), null, leftDbBean);
			for (Bean<L> lb : lBeans) {
				for (DbBean dbBean : otherDbBeans) {
					List<Bean<R>> beans = findBean(pd.getList(), lb, dbBean);
					if (beans != null && beans.size() == 1) {
						Bean<R> rBean = beans.get(0);
						one2one.add(lb.getJavaBean(), rBean.getJavaBean());
					}
				}
			}

			long size = pd.getSize();
			Page pager = new Page(pageNum, pageSize, size, one2one);
			return pager;

		} else if (n == 3) { // 一对多
			One2Many<L, R> one2Many = new One2Many<>();
			PageData pd = one2Many(leftDbBean, otherDbBeans, conditions, limit);
			List<Bean<L>> lBeans = findBean(pd.getList(), null, leftDbBean);
			List<One2Many<L, R>> o2ms = new LinkedList<>();
			if (lBeans.size() > 0) {
				for (Bean<L> lBean : lBeans) {
					for (DbBean dbBean : otherDbBeans) {
						List<Bean<R>> rBeans = findBean(pd.getList(), lBean, dbBean);
						List<R> rJavaBeans = bean2JavaBean(rBeans);
						one2Many = new One2Many<L, R>(lBeans.get(0).getJavaBean(), rJavaBeans);
						o2ms.add(one2Many);
					}
				}
			}
			long size = pd.getSize();
			Page pager = new Page(pageNum, pageSize, size, o2ms);
			return pager;
		} else if (n == 4) {// 多对多

			List<DbBean> newDbBeans = new ArrayList<>();
			newDbBeans.add(leftDbBean);
			newDbBeans.addAll(otherDbBeans);

			String columns = ComSQL.columns(newDbBeans);
			Parameter p = conditions.getWhereParameter();
			String where = p.getReadySql() + limit;
			String tableNames = new String();
			String condition = new String();

			DbBean middleDbBean = otherDbBeans.get(0);
			DbBean rightDbBean = otherDbBeans.get(1);
			String middleTableName = T2E.toTableName(middleDbBean.getBeanName());
			String rightTableName = T2E.toTableName(rightDbBean.getBeanName());
			String rightTablePk = T2E.toColumn(rightDbBean.getPrimaryField());
			tableNames = leftTableName + STRING.SPACING + STRING.COMMA + STRING.SPACING + middleTableName + STRING.SPACING + STRING.COMMA + STRING.SPACING + rightTableName + STRING.SPACING;
			List<ForeignKey> fkes = middleDbBean.getForeignKeies();
			for (ForeignKey f : fkes) {
				if (f.getReferDbBeanName().toUpperCase().equals(leftTableName.toUpperCase())) {
					String fk = T2E.toColumn(f.getForeignProperty());
					condition = condition + SQL_KEY.AND + middleTableName + STRING.POINT + fk + STRING.SPACING + STRING.EQ + STRING.SPACING + leftTableName + STRING.POINT + leftTablePk + STRING.SPACING;

				}
				if (f.getReferDbBeanName().toUpperCase().equals(rightTableName.toUpperCase())) {
					String fk = T2E.toColumn(f.getForeignProperty());
					condition = condition + SQL_KEY.AND + middleTableName + STRING.POINT + fk + STRING.SPACING + STRING.EQ + STRING.SPACING + rightTableName + STRING.POINT + rightTablePk + STRING.SPACING;

				}
			}

			String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + tableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
			p.setReadySql(sql);
			List<Map<String, Object>> list = jdbc.gets(p);
			Many2Many<L, M, R> many2many = new Many2Many<>();
			List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
			LinkedList<Many2Many<L, M, R>> m2ms = new LinkedList<>();
			for (Bean<L> lBean : lBeans) {
				many2many.setOne(lBean.getJavaBean());
				List<Bean<M>> mBeans = findBean(list, lBean, middleDbBean);
				One2One<M, R> one2one = new One2One<>();
				for (Bean<M> mBean : mBeans) {
					List<Bean<R>> rBeans = findBean(list, mBean, rightDbBean);
					if (rBeans.size() > 0) {
						one2one.add(mBean.getJavaBean(), rBeans.get(0).getJavaBean());
					}
				}
				many2many.setMany(one2one);
				m2ms.add(many2many);
			}

			String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + tableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
			Parameter sizeP = conditions.getWhereParameter();
			sizeP.setReadySql(sizeSql);
			Map<String, Object> map = jdbc.get(sizeP);
			Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
			if (size == null) {
				size = 0L;
			}
			Page pager = new Page(pageNum, pageSize, size, m2ms);
			return pager;
		} else if (n == 5) {// 未知
			return new Page();

		} else {
			return new Page();
		}
	}

	/**
	 * 获取表关系模式
	 * 
	 * @param leftDbBean
	 * @param otherBeans
	 * @return 单表 ：1 ; 一对一：2 一对多：3 ; 多对多：4 ;未知 ：5
	 */

	private int getRelation(DbBean leftDbBean, DbBean... otherBeans) {
		if (TableRelationAnalyze.isOne(leftDbBean, otherBeans)) {
			return 1;
		} else if (TableRelationAnalyze.isOne2One(leftDbBean, otherBeans)) {
			return 2;
		} else if (TableRelationAnalyze.isOne2Many(leftDbBean, otherBeans)) {
			return 3;
		} else if (TableRelationAnalyze.isMany2Many(leftDbBean, otherBeans)) {
			return 4;
		} else {
			return 5;
		}

	}

	/*
	 * private List<JsonBean> findJsonBean(List<Map<String, Object>> list,
	 * String mainBeanName, String id, Object idVal, DbBean dbBean) { if (id !=
	 * null && idVal != null) { List<Map<String, Object>> newlist = new
	 * LinkedList<>(); for (Map<String, Object> map : list) { mainBeanName
	 * =T2E.toField(T2E.toColumn(mainBeanName)) ; Object thisVal =
	 * map.get(mainBeanName + STRING.DOLLAR + id); if
	 * (thisVal.toString().equals(idVal.toString())) { newlist.add(map); } }
	 * list = newlist; }
	 * 
	 * String dbBeanName = dbBean.getBeanName(); String pkName =
	 * dbBean.getPrimaryField(); List<JsonBean> jsonBeans = new LinkedList<>();
	 * String str = new String(); for (Map<String, Object> map : list) {
	 * JsonBean jsonBean = new JsonBean(); for (Entry<String, Object> en :
	 * map.entrySet()) { String key = en.getKey(); Object val = en.getValue();
	 * String[] strs = key.split(STRING.ESCAPE_DOLLAR); if (strs.length > 0) {
	 * String beanName = strs[0]; String pr = strs[1];
	 * 
	 * if (dbBeanName.toUpperCase().equals(beanName.toUpperCase())) {
	 * jsonBean.addField(pr, val); if
	 * (pkName.toUpperCase().equals(pr.toUpperCase())) { jsonBean.setId(pkName);
	 * jsonBean.setIdVal(val); } } } } if (jsonBeans.size() == 0) {
	 * jsonBeans.add(jsonBean); str = str + jsonBean.getIdVal().toString() +
	 * STRING.AT; } else { if (!str.contains(jsonBean.getIdVal().toString())) {
	 * jsonBeans.add(jsonBean); str = str + jsonBean.getIdVal().toString() +
	 * STRING.AT; } }
	 * 
	 * }
	 * 
	 * return jsonBeans;
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	private <L, R> List<Bean<R>> findBean(List<Map<String, Object>> list, Bean<L> bean, DbBean dbBean) {
		if (bean != null) {
			List<Map<String, Object>> newlist = new LinkedList<>();
			for (Map<String, Object> map : list) {
				String name = T2E.toField(T2E.toColumn(bean.getBeanName()));

				Object thisVal = map.get(name + STRING.DOLLAR + bean.getKey());
				if (thisVal.toString().equals(bean.getVal())) {
					newlist.add(map);
				}
			}
			list = newlist;
		}

		String dbBeanName = dbBean.getBeanName();
		String pkName = dbBean.getPrimaryField();
		List<Bean<R>> beans = new LinkedList<>();
		String str = new String();
		for (Map<String, Object> map : list) {
			String idName;
			String idValue;
			Bean<R> resultBean = new Bean<>();
			resultBean.setBeanName(dbBean.getBeanName());
			RObject<?> rObj = new RObject<>(dbBean.getClassName());
			for (Entry<String, Object> en : map.entrySet()) {
				String key = en.getKey();
				Object val = en.getValue();
				String[] strs = key.split(STRING.ESCAPE_DOLLAR);
				if (strs.length == 2) {
					String beanName = strs[0];
					String pr = strs[1];

					if (dbBeanName.toUpperCase().equals(beanName.toUpperCase())) {
						rObj.invokeSetMethod(pr, val);
						if (pkName.toUpperCase().equals(pr.toUpperCase())) {
							idName = pkName;
							idValue = val.toString();
							resultBean.setKey(idName);
							resultBean.setVal(idValue);
						}
					}
				}
			}
			if (beans.size() == 0 || !str.contains(resultBean.getVal())) {

				str = str + resultBean.getVal() + STRING.AT;
				resultBean.setJavaBean((R) rObj.getObject());
				beans.add(resultBean);
			}
		}

		return beans;

	}

	private <T> List<T> bean2JavaBean(List<Bean<T>> beans) {
		List<T> list = new LinkedList<>();
		for (Bean<T> bean : beans) {
			list.add(bean.getJavaBean());
		}
		return list;
	}

	/**
	 * 一对多
	 * 
	 * @param leftDbBean
	 * @param otherDbBeans
	 * @param conditions
	 * @return
	 */

	private PageData one2Many(DbBean leftDbBean, List<DbBean> otherDbBeans, Conditions conditions, String limit) {
		PageData pd = new PageData();
		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());
		List<DbBean> newDbBeans = new ArrayList<>();
		newDbBeans.add(leftDbBean);
		newDbBeans.addAll(otherDbBeans);

		String columns = ComSQL.columns(newDbBeans);
		Parameter p = conditions.getWhereParameter();
		String where = p.getReadySql() + limit;
		String otherTableName = new String();
		String condition = new String();
		for (DbBean dbBean : otherDbBeans) {
			String tableName = T2E.toTableName(dbBean.getBeanName());

			otherTableName = otherTableName + STRING.SPACING + STRING.COMMA + STRING.SPACING + tableName + STRING.SPACING;
			List<ForeignKey> fkes = dbBean.getForeignKeies();
			for (ForeignKey f : fkes) {
				if (f.getReferDbBeanName().toUpperCase().equals(leftTableName.toUpperCase())) {
					String fk = T2E.toColumn(f.getForeignProperty());
					condition = condition + SQL_KEY.AND + tableName + STRING.POINT + fk + STRING.SPACING + STRING.EQ + STRING.SPACING + leftTableName + STRING.POINT + leftTablePk + STRING.SPACING;

				}
			}
		}
		String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + leftTableName + otherTableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
		p.setReadySql(sql);
		List<Map<String, Object>> list = jdbc.gets(p);
		pd.setList(list);

		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + otherTableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
		if (size != null) {
			pd.setSize(size);
		} else {
			pd.setSize(0);
		}
		return pd;
	}

	/**
	 * 一对一**
	 * 
	 * @param leftDbBean
	 * @param otherDbBeans
	 * @param conditions
	 * @return
	 */
	private PageData one2one(DbBean leftDbBean, List<DbBean> otherDbBeans, Conditions conditions, String limit) {

		PageData pd = new PageData();
		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());

		List<DbBean> newDbBeans = new ArrayList<>();
		newDbBeans.add(leftDbBean);
		newDbBeans.addAll(otherDbBeans);

		String columns = ComSQL.columns(newDbBeans);
		Parameter p = conditions.getWhereParameter();
		String where = p.getReadySql() + limit;
		String otherTableNames = new String();
		String condition = new String();

		List<ForeignKey> leftPkes = leftDbBean.getForeignKeies();
		int n = 0;
		for (ForeignKey fk : leftPkes) {
			String fkName = T2E.toColumn(fk.getForeignProperty());
			String referTableName = T2E.toTableName(fk.getReferDbBeanName());
			for (DbBean dbBean : otherDbBeans) {
				String otherTableName = T2E.toTableName(dbBean.getBeanName());
				if (n == 0) {
					otherTableNames = otherTableNames + STRING.SPACING + STRING.COMMA + STRING.SPACING + otherTableName + STRING.SPACING;
				}
				String otherTablePkName = T2E.toColumn(dbBean.getPrimaryField());

				if (referTableName.equals(otherTableName)) {
					condition = condition + SQL_KEY.AND + leftTableName + STRING.POINT + fkName + STRING.SPACING + STRING.EQ + STRING.SPACING + otherTableName + STRING.POINT + otherTablePkName + STRING.SPACING;
				}

			}
			n++;
		}

		String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + leftTableName + otherTableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
		p.setReadySql(sql);
		List<Map<String, Object>> list = jdbc.gets(p);
		pd.setList(list);

		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + otherTableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
		if (size != null) {
			pd.setSize(size);
		} else {
			pd.setSize(0);
		}
		return pd;
	}

	private <L> List<L> findBean(List<Map<String, Object>> list, DbBean dbBean) {
		List<L> javaBeans = new LinkedList<>();
		for (Map<String, Object> map : list) {
			RObject<L> rObj = new RObject<>(dbBean.getClassName());
			for (Entry<String, Object> en : map.entrySet()) {
				String fieldName = en.getKey();
				Object value = en.getValue();
				rObj.invokeSetMethod(fieldName, value);
			}
			javaBeans.add(rObj.getObject());
		}
		return javaBeans;
	}

}