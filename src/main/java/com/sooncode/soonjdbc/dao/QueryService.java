package com.sooncode.soonjdbc.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.bean.ForeignKey;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.dao.tabletype.Many2ManyTable;
import com.sooncode.soonjdbc.dao.tabletype.One2Many2ManyTable;
import com.sooncode.soonjdbc.dao.tabletype.One2ManyTable;
import com.sooncode.soonjdbc.dao.tabletype.One2OneTable;
import com.sooncode.soonjdbc.dao.tabletype.OneTable;
import com.sooncode.soonjdbc.dao.tabletype.TableType;
import com.sooncode.soonjdbc.page.Many2Many;
import com.sooncode.soonjdbc.page.One2Many;
import com.sooncode.soonjdbc.page.One2Many2Many;
import com.sooncode.soonjdbc.page.One2One;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.reflect.RObject;
import com.sooncode.soonjdbc.sql.ComSQL;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.TableRelationAnalyze;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.util.T2E;

public class QueryService {

	public List<TableType> getTableType(Jdbc jdbc, Conditions conditions) {
		List<TableType> nes = new LinkedList<>();
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean().getJavaBean());
		List<DbBean> list = getOtherDbBeans(jdbc, conditions);
		DbBean[] otherBeans = list.toArray(new DbBean[list.size()]);
		if (TableRelationAnalyze.isOne(leftDbBean, otherBeans)) {
			nes.add(new OneTable());
		}
		if (TableRelationAnalyze.isOne2One(leftDbBean, otherBeans)) {
			nes.add(new One2OneTable());
		}
		if (TableRelationAnalyze.isOne2Many(leftDbBean, otherBeans)) {
			nes.add(new One2ManyTable());
		}
		if (TableRelationAnalyze.isMany2Many(leftDbBean, otherBeans)) {
			nes.add(new Many2ManyTable());
		}
		if (otherBeans.length == 2 && TableRelationAnalyze.isOne2Many2Many(leftDbBean, otherBeans[0], otherBeans[1])) {
			nes.add(new One2Many2ManyTable());
		}
		return nes;

	}

	public <L, R> List<Bean<R>> findBean(List<Map<String, Object>> list, Bean<L> bean, DbBean dbBean) {

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
			RObject<R> rObj = new RObject<>(dbBean.getClassName());
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
				resultBean.setJavaBean(rObj.getObject());
				beans.add(resultBean);
			}
		}

		return beans;

	}

	public <T> List<T> bean2JavaBean(List<Bean<T>> beans) {
		List<T> list = new LinkedList<>();
		for (Bean<T> bean : beans) {
			list.add(bean.getJavaBean());
		}
		return list;
	}

	public <L> List<L> findBean(List<Map<String, Object>> list, DbBean dbBean) {
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

	public <L> Page getOnes(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean().getJavaBean());
		String leftTableName = leftDbBean.getTableName();
		String columns = ComSQL.columns4One(leftDbBean);
		String where = conditions.getWhereParameter().getReadySql() + getLimit(pageNum, pageSize);
		String sizeWhere = conditions.getWhereParameter().getReadySql();
		String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + leftTableName + " WHERE 1=1 " + where;
		Parameter p = conditions.getWhereParameter();
		p.setReadySql(sql);
		List<Map<String, Object>> list = jdbc.gets(p);
		List<L> result = findBean(list, leftDbBean);

		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + " WHERE 1=1 " + sizeWhere;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
		if (size == null) {
			size = 0L;
		}
		Page page = new Page(pageNum, pageSize, size);
		page.setOnes(result);
		return page;
	}

	private List<ForeignKey> comparison(List<ForeignKey> fkes, List<DbBean> dbBeans) {
		List<ForeignKey> list = new LinkedList<>();
		for (DbBean dbBean : dbBeans) {
			String tableName = dbBean.getTableName();
			for (ForeignKey fk : fkes) {
				String referName = fk.getReferDbBeanName();
				if (tableName.equals(referName)) {
					list.add(fk);
				}
			}

		}
		return list;
	}

	public <L, R> Page getOne2Ones(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean().getJavaBean());

		List<DbBean> otherDbBeans = getOtherDbBeans(jdbc, conditions);

		// --------------------------------------------------------------------------

		String leftTableName = leftDbBean.getTableName();
		String leftPk = T2E.toColumn(leftDbBean.getPrimaryField());
		List<DbBean> newDbBeans = new ArrayList<>();
		newDbBeans.add(leftDbBean);
		newDbBeans.addAll(otherDbBeans);

		String columns = ComSQL.columns(newDbBeans);
		Parameter p = conditions.getWhereParameter();
		String where = p.getReadySql() + getLimit(pageNum, pageSize);
		String otherTableNames = new String();
		String condition = new String();

		List<ForeignKey> leftFkes = leftDbBean.getForeignKeies();
		leftFkes = comparison(leftFkes, otherDbBeans);
		if (leftFkes.size() > 0) {
			for (DbBean dbBean : otherDbBeans) {

				for (int i = 0; i < otherDbBeans.size(); i++) {
					ForeignKey fk = leftFkes.get(i);
					String fkName = T2E.toColumn(fk.getForeignProperty());
					String referTableName = fk.getReferDbBeanName();
					String otherTableName = dbBean.getTableName();
					if (referTableName.equals(otherTableName)) {
						otherTableNames = otherTableNames + STRING.SPACING + STRING.COMMA + STRING.SPACING + otherTableName + STRING.SPACING;
						String otherTablePkName = T2E.toColumn(dbBean.getPrimaryField());
						condition = condition + SQL_KEY.AND + leftTableName + STRING.POINT + fkName + STRING.SPACING + STRING.EQ + STRING.SPACING + otherTableName + STRING.POINT + otherTablePkName + STRING.SPACING;
					}
				}

			}
		} else {

			for (DbBean other : otherDbBeans) {
				List<ForeignKey> fkes = other.getForeignKeies();
				String otherTableName = other.getTableName();
				for (ForeignKey f : fkes) {
					String referTableName = f.getReferDbBeanName();
					String fkName = T2E.toColumn(f.getForeignProperty());
					if (leftTableName.equals(referTableName)) {
						otherTableNames = otherTableNames + STRING.SPACING + STRING.COMMA + STRING.SPACING + otherTableName + STRING.SPACING;

						condition = condition + SQL_KEY.AND + leftTableName + STRING.POINT + leftPk + STRING.SPACING + STRING.EQ + STRING.SPACING + otherTableName + STRING.POINT + fkName + STRING.SPACING;
					}
				}

			}

		}

		String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + leftTableName + otherTableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
		p.setReadySql(sql);
		List<Map<String, Object>> list = jdbc.gets(p);
		String sizeWhere = conditions.getWhereParameter().getReadySql();
		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + otherTableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + sizeWhere;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
		List<One2One> one2ones = new LinkedList<>();
		List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
		for (Bean<L> lb : lBeans) {
			One2One o2o = new One2One();// <L, R>(lb.getJavaBean(),
										// rBean.getJavaBean());
			for (DbBean dbBean : otherDbBeans) {
				List<Bean<R>> beans = findBean(list, lb, dbBean);
				if (beans != null && beans.size() == 1) {
					Bean<R> rBean = beans.get(0);
					o2o.add(lb.getJavaBean());
					o2o.add(rBean.getJavaBean());
				}
			}
			one2ones.add(o2o);
		}

		Page page = new Page(pageNum, pageSize, size == null ? 0L : size);
		page.setOne2One(one2ones);
		return page;
	}

	public <L, R> Page getOne2Manys(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		One2Many<L, R> one2Many = new One2Many<>();
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean().getJavaBean());
		List<DbBean> otherDbBeans = getOtherDbBeans(jdbc, conditions);

		String leftTableName = leftDbBean.getTableName();
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());
		List<DbBean> newDbBeans = new ArrayList<>();
		newDbBeans.add(leftDbBean);
		newDbBeans.addAll(otherDbBeans);

		String columns = ComSQL.columns(newDbBeans);
		Parameter p = conditions.getWhereParameter();
		String where = p.getReadySql() + getLimit(pageNum, pageSize);
		String otherTableName = new String();
		String condition = new String();
		for (DbBean dbBean : otherDbBeans) {
			String tableName = dbBean.getTableName();

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
		String sizeWhere = conditions.getWhereParameter().getReadySql();
		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + otherTableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + sizeWhere;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));

		List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
		List<One2Many<L, R>> o2ms = new LinkedList<>();
		if (lBeans.size() > 0) {
			for (Bean<L> lBean : lBeans) {
				for (DbBean dbBean : otherDbBeans) {
					List<Bean<R>> rBeans = findBean(list, lBean, dbBean);
					List<R> rJavaBeans = bean2JavaBean(rBeans);
					one2Many = new One2Many<L, R>(lBeans.get(0).getJavaBean(), rJavaBeans);
					o2ms.add(one2Many);
				}
			}
		}

		Page page = new Page(pageNum, pageSize, size == null ? 0L : size, o2ms);
		return page;
	}

	public <L, M, R> Page getMany2Manys(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean().getJavaBean());
		List<DbBean> otherDbBeans = getOtherDbBeans(jdbc, conditions);
		List<DbBean> newDbBeans = new ArrayList<>();
		newDbBeans.add(leftDbBean);
		newDbBeans.addAll(otherDbBeans);

		String columns = ComSQL.columns(newDbBeans);
		Parameter p = conditions.getWhereParameter();
		String where = p.getReadySql() + getLimit(pageNum, pageSize);
		String tableNames = new String();
		String condition = new String();

		DbBean middleDbBean = otherDbBeans.get(0);
		DbBean rightDbBean = otherDbBeans.get(1);
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());
		String rightTablePk = T2E.toColumn(rightDbBean.getPrimaryField());

		String leftTableName = leftDbBean.getTableName();
		String middleTableName = middleDbBean.getTableName();
		String rightTableName = rightDbBean.getTableName();

		tableNames = leftTableName + STRING.SPACING + STRING.COMMA + STRING.SPACING + middleTableName + STRING.SPACING + STRING.COMMA + STRING.SPACING + rightTableName + STRING.SPACING;
		List<ForeignKey> fkes = middleDbBean.getForeignKeies();

		String lTableName = leftDbBean.getTableName();
		String rTableName = rightDbBean.getTableName();

		if (lTableName.equals(rTableName)) {

			String fk = T2E.toColumn(fkes.get(0).getForeignProperty());
			condition = condition + SQL_KEY.AND + middleTableName + STRING.POINT + fk + STRING.SPACING + STRING.EQ + STRING.SPACING + leftTableName + STRING.POINT + leftTablePk + STRING.SPACING;

			String fk2 = T2E.toColumn(fkes.get(1).getForeignProperty());
			condition = condition + SQL_KEY.AND + middleTableName + STRING.POINT + fk2 + STRING.SPACING + STRING.EQ + STRING.SPACING + rightTableName + STRING.POINT + rightTablePk + STRING.SPACING;
		} else {

			String fk = T2E.toColumn(fkes.get(0).getForeignProperty());
			condition = condition + SQL_KEY.AND + middleTableName + STRING.POINT + fk + STRING.SPACING + STRING.EQ + STRING.SPACING + leftTableName + STRING.POINT + leftTablePk + STRING.SPACING;

			String fk2 = T2E.toColumn(fkes.get(1).getForeignProperty());
			condition = condition + SQL_KEY.AND + middleTableName + STRING.POINT + fk2 + STRING.SPACING + STRING.EQ + STRING.SPACING + rightTableName + STRING.POINT + rightTablePk + STRING.SPACING;
		}

		String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + tableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
		p.setReadySql(sql);
		List<Map<String, Object>> list = jdbc.gets(p);
		List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
		LinkedList<Many2Many<L, M, R>> m2ms = new LinkedList<>();
		for (Bean<L> lBean : lBeans) {
			Many2Many<L, M, R> many2many = new Many2Many<>();
			many2many.setOne(lBean.getJavaBean());
			List<Bean<M>> mBeans = findBean(list, lBean, middleDbBean);
			List<One2One> o2os = new LinkedList<>();
			for (Bean<M> mBean : mBeans) {
				List<Bean<R>> rBeans = findBean(list, mBean, rightDbBean);
				if (rBeans.size() > 0) {
					One2One o2o = new One2One();

					o2o.add(mBean.getJavaBean());
					o2o.add(rBeans.get(0).getJavaBean());
					o2os.add(o2o);
				}
			}
			many2many.setMany(o2os);
			m2ms.add(many2many);
		}
		String sizeWhere = conditions.getWhereParameter().getReadySql();
		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + tableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + sizeWhere;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
		if (size == null) {
			size = 0L;
		}
		Page page = new Page(pageNum, pageSize, size, m2ms);
		return page;
	}

	public <L, M, R> Page getMany2Manys2(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean());
		List<DbBean> otherDbBeans = getOtherDbBeans(jdbc, conditions);
		List<DbBean> newDbBeans = new ArrayList<>();
		newDbBeans.add(leftDbBean);
		newDbBeans.addAll(otherDbBeans);

		String columns = ComSQL.columns(newDbBeans);
		Parameter p = conditions.getWhereParameter();
		String where = p.getReadySql() + getLimit(pageNum, pageSize);
		String tableNames = new String();
		String condition = new String();

		DbBean middleDbBean = otherDbBeans.get(0);
		DbBean rightDbBean = otherDbBeans.get(1);
		String rightTablePk = T2E.toColumn(rightDbBean.getPrimaryField());
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());
		String leftTableName = leftDbBean.getTableName();
		String middleTableName = middleDbBean.getTableName();
		String rightTableName = rightDbBean.getTableName();
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
		List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
		LinkedList<Many2Many<L, M, R>> m2ms = new LinkedList<>();
		for (Bean<L> lBean : lBeans) {
			Many2Many<L, M, R> many2many = new Many2Many<>();
			many2many.setOne(lBean.getJavaBean());
			List<Bean<M>> mBeans = findBean(list, lBean, middleDbBean);
			List<One2One> o2os = new LinkedList<>();
			for (Bean<M> mBean : mBeans) {
				List<Bean<R>> rBeans = findBean(list, mBean, rightDbBean);
				if (rBeans.size() > 0) {
					One2One o2o = new One2One(); 
					o2o.add(mBean.getJavaBean());
					o2o.add(rBeans.get(0).getJavaBean());
					o2os.add(o2o);
				}
			}
			many2many.setMany(o2os);
			m2ms.add(many2many);
		}
		String sizeWhere = conditions.getWhereParameter().getReadySql();
		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + tableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + sizeWhere;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
		if (size == null) {
			size = 0L;
		}
		Page page = new Page(pageNum, pageSize, size, m2ms);
		return page;
	}

	public <L, M, R> Page getOne2Many2Manys(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean().getJavaBean());
		List<DbBean> otherDbBeans = getOtherDbBeans(jdbc, conditions);
		List<DbBean> newDbBeans = new ArrayList<>();
		newDbBeans.add(leftDbBean);
		newDbBeans.addAll(otherDbBeans);

		String columns = ComSQL.columns(newDbBeans);
		Parameter p = conditions.getWhereParameter();
		String where = p.getReadySql() + getLimit(pageNum, pageSize);
		String tableNames = new String();
		String condition = new String();

		DbBean middleDbBean = otherDbBeans.get(0);
		String mPK = T2E.toColumn(middleDbBean.getPrimaryField());
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());
		DbBean rightDbBean = otherDbBeans.get(1);
		String leftTableName = leftDbBean.getTableName();
		String middleTableName = middleDbBean.getTableName();
		String rightTableName = rightDbBean.getTableName();

		tableNames = leftTableName + STRING.SPACING + STRING.COMMA + STRING.SPACING + middleTableName + STRING.SPACING + STRING.COMMA + STRING.SPACING + rightTableName + STRING.SPACING;
		List<ForeignKey> mFkes = middleDbBean.getForeignKeies();
		List<ForeignKey> rFkes = rightDbBean.getForeignKeies();
		for (ForeignKey mFk : mFkes) {
			if (mFk.getReferDbBeanName().toUpperCase().equals(leftTableName.toUpperCase())) {
				String fk = T2E.toColumn(mFk.getForeignProperty());
				condition = condition + SQL_KEY.AND + middleTableName + STRING.POINT + fk + STRING.SPACING + STRING.EQ + STRING.SPACING + leftTableName + STRING.POINT + leftTablePk + STRING.SPACING;

				for (ForeignKey rFk : rFkes) {
					if (rFk.getReferDbBeanName().toUpperCase().equals(middleTableName.toUpperCase())) {
						String rfk = T2E.toColumn(rFk.getForeignProperty());
						condition = condition + SQL_KEY.AND + middleTableName + STRING.POINT + mPK + STRING.SPACING + STRING.EQ + STRING.SPACING + rightTableName + STRING.POINT + rfk + STRING.SPACING;
					}
				}

			}

		}

		String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + tableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
		p.setReadySql(sql);
		List<Map<String, Object>> list = jdbc.gets(p);

		List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
		LinkedList<One2Many2Many<L, M, R>> o2m2ms = new LinkedList<>();
		for (Bean<L> lBean : lBeans) {
			One2Many2Many<L, M, R> o2m2m = new One2Many2Many<>();
			L one = lBean.getJavaBean();
			o2m2m.setOne(one);
			List<One2Many<M, R>> o2ms = new LinkedList<>();
			List<Bean<M>> mBeans = findBean(list, lBean, middleDbBean);
			for (Bean<M> mBean : mBeans) {
				List<Bean<R>> rBeans = findBean(list, mBean, rightDbBean);
				List<R> res = new LinkedList<>();
				for (Bean<R> rB : rBeans) {
					res.add(rB.getJavaBean());
				}
				One2Many<M, R> o2m = new One2Many<>();
				o2m.setOne(mBean.getJavaBean());
				o2m.setMany(res);
				o2ms.add(o2m);

			}
			o2m2m.setOne2manys(o2ms);
			o2m2ms.add(o2m2m);
		}
		String sizeWhere = conditions.getWhereParameter().getReadySql();
		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + tableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + sizeWhere;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));
		if (size == null) {
			size = 0L;
		}
		Page page = new Page(pageNum, pageSize, size);
		page.setOne2Many2Manys(o2m2ms);
		return page;
	}

	private String getLimit(long pageNum, long pageSize) {
		String limit = SQL_KEY.LIMIT + (pageNum - 1) * pageSize + SQL_KEY.COMMA + pageSize;
		return limit;
	}

	public List<DbBean> getOtherDbBeans(Jdbc jdbc, Conditions conditions) {
		DbBean[] otherBeans = conditions.getOtherBeans();
		List<DbBean> otherDbBeans = new ArrayList<>();
		if (otherBeans.length > 0) {
			for (DbBean jBean : otherBeans) {
				DbBean dbBean = jdbc.getDbBean(jBean.getJavaBean());
				dbBean.setBeanName(jBean.getBeanName());
				dbBean.setBeanName(jBean.getBeanName());
				otherDbBeans.add(dbBean);
			}
		}
		return otherDbBeans;
	}

	public Page clonePage(Page oldPage, Page newPage) {
		oldPage.initPage(newPage.getPageNumber(), newPage.getPageSize(), newPage.getTotal());
		if (oldPage.getOnes() == null || oldPage.getOnes().size() == 0) {
			oldPage.setOnes(newPage.getOnes());
		}
		if (oldPage.getOne2One() == null || oldPage.getOne2One().size() == 0) {
			oldPage.setOne2One(newPage.getOne2One());
		}
		if (oldPage.getOne2Manys() == null || oldPage.getOne2Manys().size() == 0) {
			oldPage.setOne2Manys(newPage.getOne2Manys());
		}
		if (oldPage.getMany2Manys() == null || oldPage.getMany2Manys().size() == 0) {
			oldPage.setMany2Manys(newPage.getMany2Manys());
		}
		if (oldPage.getOne2Many2Manys() == null || oldPage.getOne2Many2Manys().size() == 0) {
			oldPage.setOne2Many2Manys(newPage.getOne2Many2Manys());
		}

		return oldPage;

	}
}
