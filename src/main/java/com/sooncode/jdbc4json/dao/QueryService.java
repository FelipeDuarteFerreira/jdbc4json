package com.sooncode.jdbc4json.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.jdbc4json.Jdbc;
import com.sooncode.jdbc4json.bean.DbBean;
import com.sooncode.jdbc4json.bean.ForeignKey;
import com.sooncode.jdbc4json.bean.JsonBean;
import com.sooncode.jdbc4json.constant.SQL_KEY;
import com.sooncode.jdbc4json.constant.STRING;
import com.sooncode.jdbc4json.page.Many2Many;
import com.sooncode.jdbc4json.page.One2Many;
import com.sooncode.jdbc4json.page.One2Many2Many;
import com.sooncode.jdbc4json.page.One2One;
import com.sooncode.jdbc4json.page.Page;
import com.sooncode.jdbc4json.reflect.RObject;
import com.sooncode.jdbc4json.sql.ComSQL;
import com.sooncode.jdbc4json.sql.Parameter;
import com.sooncode.jdbc4json.sql.TableRelationAnalyze;
import com.sooncode.jdbc4json.sql.condition.Conditions;
import com.sooncode.jdbc4json.util.T2E;

class QueryService {
	/**
	 * 获取表关系模式
	 * 
	 * @param leftDbBean
	 * @param otherBeans
	 * @return 单表 ：1 ; 一对一：2 一对多：3 ; 多对多：4 ;一对多对多 ：5 ; 6:未知
	 */

	public int getRelation(Jdbc jdbc, Conditions conditions) {

		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean());
		List<DbBean> list = getOtherDbBeans(jdbc, conditions);
		DbBean[] otherBeans = list.toArray(new DbBean[list.size()]);
		if (TableRelationAnalyze.isOne(leftDbBean, otherBeans)) {
			return 1;
		} else if (TableRelationAnalyze.isOne2One(leftDbBean, otherBeans)) {
			return 2;
		} else if (TableRelationAnalyze.isOne2Many(leftDbBean, otherBeans)) {
			return 3;
		} else if (TableRelationAnalyze.isMany2Many(leftDbBean, otherBeans)) {
			return 4;
		} else if ( otherBeans.length==2 && TableRelationAnalyze.isOne2Many2Many(leftDbBean, otherBeans[0], otherBeans[1])) {
			return 5;
		} else {
			return 6;
		}

	}

	@SuppressWarnings("unchecked")
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
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean());
		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());
		String columns = ComSQL.columns4One(leftDbBean);
		String where = conditions.getWhereParameter().getReadySql() + getLimit(pageNum, pageSize);
		String sql = SQL_KEY.SELECT + columns + SQL_KEY.FROM + leftTableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + where;
		Parameter p = conditions.getWhereParameter();
		p.setReadySql(sql);
		List<Map<String, Object>> list = jdbc.gets(p);
		List<L> result = findBean(list, leftDbBean);

		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + where;
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

	public <L, R> Page getOne2Ones(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean());
		List<One2One<L, R>> one2ones = new LinkedList<>();
		List<DbBean> otherDbBeans = getOtherDbBeans(jdbc, conditions);

		// --------------------------------------------------------------------------

		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());
		List<DbBean> newDbBeans = new ArrayList<>();
		newDbBeans.add(leftDbBean);
		newDbBeans.addAll(otherDbBeans);

		String columns = ComSQL.columns(newDbBeans);
		Parameter p = conditions.getWhereParameter();
		String where = p.getReadySql() + getLimit(pageNum, pageSize);
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
		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + otherTableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
		Parameter sizeP = conditions.getWhereParameter();
		sizeP.setReadySql(sizeSql);
		Map<String, Object> map = jdbc.get(sizeP);
		Long size = (Long) map.get(T2E.toField(SQL_KEY.SIZE));

		List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
		for (Bean<L> lb : lBeans) {
			for (DbBean dbBean : otherDbBeans) {
				List<Bean<R>> beans = findBean(list, lb, dbBean);
				if (beans != null && beans.size() == 1) {
					Bean<R> rBean = beans.get(0);
					One2One<L, R> o2o = new One2One<L, R>(lb.getJavaBean(), rBean.getJavaBean());
					one2ones.add(o2o);
				}
			}
		}

		Page page = new Page(pageNum, pageSize, size == null ? 0L : size);
		page.setOne2Ones(one2ones);
		return page;
	}

	public <L, R> Page getOne2Manys(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
		One2Many<L, R> one2Many = new One2Many<>();
		DbBean leftDbBean = jdbc.getDbBean(conditions.getLeftBean());
		List<DbBean> otherDbBeans = getOtherDbBeans(jdbc, conditions);

		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());
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

		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + leftTableName + otherTableName + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
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
		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());
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
		List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
		LinkedList<Many2Many<L, M, R>> m2ms = new LinkedList<>();
		for (Bean<L> lBean : lBeans) {
			Many2Many<L, M, R> many2many = new Many2Many<>();
			many2many.setOne(lBean.getJavaBean());
			List<Bean<M>> mBeans = findBean(list, lBean, middleDbBean);
			List<One2One<M, R>> o2os = new LinkedList<>();
			for (Bean<M> mBean : mBeans) {
				List<Bean<R>> rBeans = findBean(list, mBean, rightDbBean);
				if (rBeans.size() > 0) {
					One2One<M, R> o2o = new One2One<>(mBean.getJavaBean(), rBeans.get(0).getJavaBean());
					o2os.add(o2o);
				}
			}
			many2many.setMany(o2os);
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
		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());
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
		List<Bean<L>> lBeans = findBean(list, null, leftDbBean);
		LinkedList<Many2Many<L, M, R>> m2ms = new LinkedList<>();
		for (Bean<L> lBean : lBeans) {
			Many2Many<L, M, R> many2many = new Many2Many<>();
			many2many.setOne(lBean.getJavaBean());
			List<Bean<M>> mBeans = findBean(list, lBean, middleDbBean);
			List<One2One<M, R>> o2os = new LinkedList<>();
			for (Bean<M> mBean : mBeans) {
				List<Bean<R>> rBeans = findBean(list, mBean, rightDbBean);
				if (rBeans.size() > 0) {
					One2One<M, R> o2o = new One2One<>(mBean.getJavaBean(), rBeans.get(0).getJavaBean());
					o2os.add(o2o);
				}
			}
			many2many.setMany(o2os);
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
		Page page = new Page(pageNum, pageSize, size, m2ms);
		return page;
	}
	public <L, M, R> Page getOne2Many2Manys(long pageNum, long pageSize, Conditions conditions, Jdbc jdbc) {
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
		String mPK = T2E.toColumn(middleDbBean.getPrimaryField() );
		DbBean rightDbBean = otherDbBeans.get(1);
		String leftTableName = T2E.toTableName(leftDbBean.getBeanName());
		String leftTablePk = T2E.toColumn(leftDbBean.getPrimaryField());
		String middleTableName = T2E.toTableName(middleDbBean.getBeanName());
		String rightTableName = T2E.toTableName(rightDbBean.getBeanName());
		 
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
			L one =  lBean.getJavaBean();
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
		
		String sizeSql = SQL_KEY.SELECT + SQL_KEY.COUNT_START + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + tableNames + SQL_KEY.WHERE + SQL_KEY.ONE_EQ_ONE + condition + where;
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
		JsonBean[] otherBeans = conditions.getOtherBeans();
		List<DbBean> otherDbBeans = new ArrayList<>();
		if (otherBeans.length > 0) {
			for (JsonBean jBean : otherBeans) {
				DbBean dbBean = jdbc.getDbBean(jBean);
				otherDbBeans.add(dbBean);
			}
		}
		return otherDbBeans;
	}
}
