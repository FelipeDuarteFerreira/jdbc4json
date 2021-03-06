package com.sooncode.soonjdbc.sql;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.CLASS_NAME;
import com.sooncode.soonjdbc.constant.DateFormat;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.util.T2E;

/**
 * 通用SQL语句构造
 * 
 * @author pc
 *
 */
public class ComSQL {
	
    private ComSQL(){}
 
	/**
	 * 构造插入数据的可执行的SQL
	 * </br>1.根据object对象的类名映射成数据库表名.
	 * </br>2.根据object对象的属性,映射成字段,根据其属性值插入相应数据.
	 * 
	 * @param object
	 *            数据对象
	 * @return 可执行SQL
	 */
	public static Parameter insert(DbBean bean) {
		 
		String tableName = T2E.toColumn(bean.getBeanName());
		Map<String, Object> map = bean.getFields();
		String columnString = SQL_KEY.L_BRACKET;
		String filedString = SQL_KEY.L_BRACKET;
		int n = 0;
		Map<Integer,Object> par = new HashMap<>();
		int index=1;
		for (Map.Entry<String, Object> entry : map.entrySet()) {

			columnString = columnString + T2E.toColumn(entry.getKey());
			if (entry.getValue() == null) {
				filedString = filedString + SQL_KEY.NULL;
			} else {

				filedString = filedString + STRING.QUESTION  ; 
				if (entry.getValue().getClass().getName().equals(CLASS_NAME.DATE)) {
					par.put(index,  new SimpleDateFormat(DateFormat.ALL_DATE).format(entry.getValue()));
				} else {
					par.put(index, entry.getValue());
				}
				index++;
			}
			if (n != map.size() - 1) {
				columnString += SQL_KEY.COMMA;
				filedString += SQL_KEY.COMMA;
			} else {
				columnString += SQL_KEY.R_BRACKET ;
				filedString += SQL_KEY.R_BRACKET;
			}
			n++;

		}
		String sqlString = SQL_KEY.INSERT + tableName + columnString + SQL_KEY.VALUES + filedString;
		Parameter p = new Parameter();
		p.setParams(par);
		p.setReadySql(sqlString);
		return p;
	}
 
	public static Parameter batchInsert(DbBean bean) {
		
		String tableName = T2E.toColumn(bean.getBeanName());
		Map<String, Object> map = bean.getFields();
		String columnString = SQL_KEY.L_BRACKET;
		String filedString = SQL_KEY.L_BRACKET;
		int n = 0;
		Map<Integer,Object> par = new HashMap<>();
		int index=1;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			
			columnString = columnString + T2E.toColumn(entry.getKey());
			if (entry.getValue() == null) {
				filedString = filedString + STRING.QUESTION;
				par.put(index, null);
			} else {
				filedString = filedString + STRING.QUESTION  ; 
				if (entry.getValue().getClass().getName().equals(CLASS_NAME.DATE)) {
					par.put(index,  new SimpleDateFormat(DateFormat.ALL_DATE).format(entry.getValue()));
				} else {
					par.put(index, entry.getValue());
				}
			}
			index++;
			if (n != map.size() - 1) {
				columnString += SQL_KEY.COMMA;
				filedString += SQL_KEY.COMMA;
			} else {
				columnString += SQL_KEY.R_BRACKET ;
				filedString += SQL_KEY.R_BRACKET;
			}
			n++;
			
		}
		String sqlString = SQL_KEY.INSERT + tableName + columnString + SQL_KEY.VALUES + filedString;
		Parameter p = new Parameter();
		p.setParams(par);
		p.setReadySql(sqlString);
		return p;
	}
	

	 
	/**
	 * 获取修改数据的SQL
	 * 
	 * @param obj
	 * @return
	 */
	public static Parameter update(DbBean dbBean) {
		Parameter p = new Parameter();
		String tableName = T2E.toColumn(dbBean.getBeanName());
		Map<String, Object> map = dbBean.getFields();
		String s = new String();
		String pk = T2E.toColumn(dbBean.getPrimaryField());
		
		String pkString = pk + SQL_KEY.EQ + STRING.QUESTION ; 
		s = s+ pkString;
		Map<Integer,Object> param = new HashMap<>();
		param.put(1, dbBean.getPrimaryFieldValue());
		int index=2;
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = T2E.toColumn(entry.getKey().trim());
			if (entry.getValue() != null && !key.equals(pk)) {
				 
				s = s +SQL_KEY.COMMA + T2E.toColumn(entry.getKey())  + SQL_KEY.EQ + STRING.QUESTION ;
				param.put(index,entry.getValue());
				index++;
			}
		}
		param.put(param.size()+1, dbBean.getPrimaryFieldValue());
		String sql = SQL_KEY.UPDATE + tableName + SQL_KEY.SET  + s + SQL_KEY.WHERE + pkString;
		p.setReadySql(sql);
		p.setParams(param);
		return p;
	}
	
	
	public static Parameter updates(DbBean dbBean) {
		Parameter p = new Parameter();
		String tableName = T2E.toColumn(dbBean.getBeanName());
		Map<String, Object> map = dbBean.getFields();
		String s = new String();
		Map<Integer,Object> param = new HashMap<>();
		int index=1;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				if(index > 1){
					s = s +SQL_KEY.COMMA ;
				}
				s = s + T2E.toColumn(entry.getKey())  + SQL_KEY.EQ + STRING.QUESTION ;
				param.put(index,entry.getValue());
				index++;
			}
		}
		String sql = SQL_KEY.UPDATE + tableName + SQL_KEY.SET  + s ;
		p.setReadySql(sql);
		p.setParams(param);
		return p;
	}
 
	
	
	/**
	 * 获取查询语句的可执行SQL (单表)
	 * 
	 * @param object
	 * @return 可执行SQL
	 */
	public static Parameter select(DbBean dbBean) {
		String tableName = T2E.toColumn(dbBean.getBeanName());
		Map<String, Object> map = dbBean.getFields();
		int m = 0;
		String s = SQL_KEY.ONE_EQ_ONE;
		String c = new String();
		Map<Integer,Object> paramet = new HashMap<>();
		Integer index = 1;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				s = s +SQL_KEY.AND;
				s = s + tableName+STRING.POINT+ T2E.toColumn(entry.getKey()) +SQL_KEY.EQ +STRING.QUESTION;
				paramet.put(index,entry.getValue());
				index++;
			}
			if (m != 0) {
				c = c + SQL_KEY.COMMA;
			}
			c = c + tableName+STRING.POINT+T2E.toColumn(entry.getKey()) + SQL_KEY.AS +   tableName+STRING.UNDERLINE+T2E.toColumn(entry.getKey());
			m++;
		}
		String sql = SQL_KEY.SELECT  + c  + SQL_KEY.FROM + tableName + SQL_KEY.WHERE + s;
		Parameter p = new Parameter();
		p.setReadySql(sql);
		p.setParams(paramet);
		return p;
	}
	 
	 
	
	/**
	 * 获取字段
	 * @param obj
	 * @return
	 */
	public static String columns (DbBean bean){
		String tableName = T2E.toTableName( bean.getBeanName() ) ;
		 
		Map<String, Object> columns = bean.getFields();
		int m = 0;
		String c = new String();
		for (Entry<String, Object> en : columns.entrySet()) {
			String columnName = T2E.toColumn(en.getKey());
			
			if (m != 0) {
				c = c + SQL_KEY.COMMA;
			}
			c = c +tableName + STRING.POINT+ columnName + SQL_KEY.AS+tableName + STRING.DOLLAR+ columnName;
			m++;
		}
		return c;
	}
	
	
	/**
	 * 获取字段
	 * @param obj
	 * @return
	 */
	public static String columns4One (DbBean bean){
		//String tableName = T2E.toTableName( bean.getBeanName() ) ;
		
		Map<String, Object> columns = bean.getFields();
		int m = 0;
		String c = new String();
		for (Entry<String, Object> en : columns.entrySet()) {
			String columnName = T2E.toColumn(en.getKey());
			
			if (m != 0) {
				c = c + SQL_KEY.COMMA;
			}
			c = c + columnName ;
			m++;
		}
		return c;
	}
	
	
	/**
	 * 获取字段
	 * @param obj
	 * @return
	 */
	public static String columns (List<DbBean> beans){
		 String sql = new String ();
		 int i = 0;
		 for (DbBean b : beans) {
			 if(i != 0){
				 sql = sql + SQL_KEY.COMMA;
			 }
			sql = sql + columns(b);
			i++;
		}
		 return sql;
	}
	 
	
	
	/**
	 * 查询条件sql片段
	 * 
	 * @param object
	 * @return 可执行SQL
	 */
	public static Parameter where(DbBean dbBean) {
		Parameter p = new Parameter();
		String tableName = T2E.toColumn(dbBean.getBeanName());
		Map<String, Object> map = dbBean.getFields();
		String s = new String ();
		Map<Integer,Object> paramets = new HashMap<>();
		Integer index = 1;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				s = s + SQL_KEY.AND;
				s = s + tableName +  STRING.POINT + T2E.toColumn(entry.getKey())+SQL_KEY.EQ +STRING.QUESTION ; 
				paramets.put(index,entry.getValue());
			}
		}
		p.setReadySql(s);
		p.setParams(paramets);
		return p;
	}
	
	 
	
 
	 
	/**
	 * 获取查询语句的可执行SQL(带分页)
	 * 
	 * @param object
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static Parameter select(DbBean dbBean, Long pageNumber, Long pageSize) {
		Long index = (pageNumber - 1) * pageSize;
		Parameter p = select(dbBean);
		String sql = p.getReadySql() + SQL_KEY.LIMIT + index + STRING.COMMA + pageSize;
		p.setReadySql(sql);
		return p;
	}
 
	/**
	 * 获取记录的条数的可执行SQL
	 * 
	 * @param object
	 * @return 可执行SQL
	 */
	public static Parameter selectSize(DbBean dbBean) {
		Parameter p = new Parameter();
		String tableName = T2E.toColumn(dbBean.getBeanName());
		Map<String, Object> map = dbBean.getFields();
		String s = SQL_KEY.ONE_EQ_ONE ;
		Map<Integer,Object> paramet = new HashMap<>();
		int index =1;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				s = s +SQL_KEY.AND;
				s = s + T2E.toColumn(entry.getKey()) + SQL_KEY.EQ + STRING.QUESTION ; 
				paramet.put(index,entry.getValue());
				index ++;
			}
		}
		String sql =  SQL_KEY.SELECT + SQL_KEY.COUNT + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM + tableName +SQL_KEY.WHERE+ s;
		p.setReadySql(sql);
		p.setParams(paramet);
		return p;
		
	}

 
	/**
	 * 获取记录的条数的可执行SQL
	 * 
	 * @param object
	 * @return 可执行SQL
	 */
	public static Parameter O2OSize(DbBean left, DbBean... others) {
		 
		String leftTable = T2E.toColumn(left.getBeanName());
		
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < others.length; i++) {
			String simpleName = others[i].getBeanName();
			String table = T2E.toColumn(simpleName);
		 
			String pk = T2E.toColumn(others[i].getPrimaryField());
			map.put(table, pk);
		}
		
		String where = new String ();
		String from = STRING.SPACING + leftTable;
		int m = 0;
		
		 
		for (Map.Entry<String, String> en : map.entrySet()) {
			if (m != 0) {
				where = where + SQL_KEY.AND;
			}
			where = where + leftTable + STRING.POINT  + en.getValue() + SQL_KEY.EQ  + en.getKey() + STRING.POINT + en.getValue();
			from = from + STRING.COMMA + en.getKey();
			m++;
		}
		
		Parameter leftP = where(left);
		String leftWhereSql  = leftP.getReadySql();
		Map<Integer,Object> paramets =leftP.getParams();
		String leftWhere = leftWhereSql ;
		
		for (DbBean obj : others) {
			Parameter thisP = where(obj);
			String thisWhereSql  = thisP.getReadySql();
			Map<Integer,Object> thisParamets =thisP.getParams();
			leftWhere = leftWhere + thisWhereSql;
			for(int i = 1;i<=thisParamets.size();i++){
				Object value = thisParamets.get(i);
				paramets.put(paramets.size()+1 ,value);
			}
		}
		
		
		String sql = SQL_KEY.SELECT + SQL_KEY.COUNT + SQL_KEY.AS + SQL_KEY.SIZE + SQL_KEY.FROM  + from + SQL_KEY.WHERE + where + leftWhere;
		leftP.setReadySql(sql);
		leftP.setParams(paramets);
		return leftP;
		
	}

	 

	 
	 
	 
	 
	/**
	 * 获取 一对一模型的可执行SQL
	 * 
	 * @param left
	 *            被参照表对应的实体类
	 * @param other
	 *            其他参照表对应的实体类 ,至少有一个实体类
	 * @return 可执行SQL
	 */
	public static Parameter getO2O(DbBean left, DbBean... others) {
		
		String leftTable = T2E.toColumn(left.getBeanName());
		
		Map<String, Object> leftFileds = left.getFields();//EntityCache.getKeyAndValue(left);
		String col = new String();
		int n = 0;
		for (Map.Entry<String, Object> en : leftFileds.entrySet()) {
			if (n != 0) {
				col = col +STRING.COMMA ;
			}
			col = col + STRING.SPACING  + leftTable +STRING.POINT  + T2E.toColumn(en.getKey()) +SQL_KEY.AS  + leftTable + STRING.UNDERLINE + T2E.toColumn(en.getKey());
			n++;
		}
		
		Map<String, String> map = new HashMap<>();
		
		for (DbBean db : others) {
			
			String table = T2E.toColumn(db.getBeanName());
			//RObject rObject = new RObject(obj);
			String pk = T2E.toColumn(db.getPrimaryField());
			map.put(table, pk);
			Map<String, Object> field = db.getFields();//EntityCache.getKeyAndValue(obj) ;
			
			for (Map.Entry<String, Object> en : field.entrySet()) {
				col = col +STRING.COMMA  + table + STRING.POINT  + T2E.toColumn(en.getKey()) + SQL_KEY.AS  + table + STRING.UNDERLINE+ T2E.toColumn(en.getKey());
			}
		}
		
		String where = new String();
		String from = STRING.SPACING + leftTable;
		int m = 0;
		 
		for (Map.Entry<String, String> en : map.entrySet()) {
			if (m != 0) {
				where = where + SQL_KEY.AND ; 
			}
			where = where + leftTable + STRING.POINT  + en.getValue() + SQL_KEY.EQ  + en.getKey() + STRING.POINT+ en.getValue();
			from = from + STRING.COMMA + en.getKey();
			m++;
		}
		
		Parameter leftP = where(left);
		String leftWhereSql  = leftP.getReadySql();
		Map<Integer,Object> paramets =leftP.getParams();
		String leftWhere = leftWhereSql ;
		
		for (DbBean db : others) {
			Parameter thisP = where(db);
			String thisWhereSql  = thisP.getReadySql();
			Map<Integer,Object> thisParamets =thisP.getParams();
			leftWhere = leftWhere + thisWhereSql;
			for(int i = 1;i<=thisParamets.size();i++){
				Object value = thisParamets.get(i);
				paramets.put(paramets.size()+1 ,value);
			}
		}
		
		String sql =SQL_KEY.SELECT  + col + SQL_KEY.FROM + from + SQL_KEY.WHERE  + where + leftWhere;
		
		leftP.setReadySql(sql);
		leftP.setParams(paramets);
		return leftP;
	}

	 
}
