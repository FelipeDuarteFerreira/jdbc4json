package com.sooncode.soonjdbc.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.reflect.RObject;
import com.sooncode.soonjdbc.util.T2E;

public class DbBeanCache {
    
	
	private static Map<String,Map<String, Object>> fieldsCache = new HashMap<>();
	private static Map<String,String> pkCache = new HashMap<>();
	private static Map<String,List<ForeignKey>> fkCache = new HashMap<>();
	private static Map<String,List<Index>> indexCache = new HashMap<>();
		
	 
	
	public static <T> DbBean getDbBean(Connection connection , T javaBean){
		 
		String  beanName =javaBean.getClass().getSimpleName(); 
		
		Map<String, Object> fields = fieldsCache.get(beanName);
		if(fields==null){
			fields =  getFields(connection,beanName);
			fieldsCache.put(beanName, fields);
		}
		
		String pkName = pkCache.get(beanName);
		if(pkName == null){
			 pkName =  getPrimaryField(connection, beanName);
			 pkCache.put(beanName,pkName);
		}
		 
		
		List<ForeignKey> foreignKeies = fkCache.get(beanName);
		
		if(foreignKeies == null){
			foreignKeies =  getForeignKeies(connection, beanName);
			fkCache.put(beanName, foreignKeies);
		}
		List<Index> indexes = indexCache.get(beanName);
		
		if(indexes == null){
			indexes =  getIndex(connection, beanName);
			indexCache.put(beanName, indexes);
		}
		
		if(foreignKeies!=null&&foreignKeies.size()>0){
			if(indexes != null && indexes.size()>0){
				for (ForeignKey f : foreignKeies) {
					  for (Index i : indexes) {
						if(f.getForeignProperty().equals(i.getIndexPropertyName())&& i.getUnique()==true){
						   f.setUnique(true);
						}
					}
				}
			}
		}
		
		
		DbBean dbBean = new DbBean();
		dbBean.setJavaBean(javaBean);
		dbBean.setBeanName(beanName);
 
		dbBean.setPrimaryField(pkName);
		dbBean.setForeignKeies(foreignKeies);
		
		RObject<?> rObj = new RObject<>(javaBean);
		
		Map<String,Object> map = rObj.getFiledAndValue();
		 
		for(Entry<String, Object> en : map.entrySet()){
			String key = en.getKey();
			Object val = en.getValue();
			if(fields.containsKey(key)==true ){
				fields.remove(key);
				fields.put(key, val);
			}
		}
		dbBean.setFields(fields);
		Object primaryFieldValue = map.get(pkName);
		dbBean.setPrimaryFieldValue(primaryFieldValue);
		dbBean.setClassName(javaBean.getClass().getName());
       return dbBean;
	}
	
	/**
	 * 查询数据库表的所有字段 构造 “字段属性模型”
	 * 
	 * @param databaseName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private static synchronized Map<String, Object> getFields(Connection connextion, String beanName) {
		String tableName = T2E.toTableName(beanName);
		 
		String dataBaseName = null ;//db.getDataName();
		try {

			ResultSet columnSet = connextion.getMetaData().getColumns(dataBaseName, STRING.PERCENT, tableName, STRING.PERCENT);
			Map<String, Object> map = new HashMap<>();
			while (columnSet.next()) { // 遍历某个表的字段
				String columnName = columnSet.getString("COLUMN_NAME");// .toUpperCase());
				map.put(T2E.toField(columnName), null);
			}
			return map;
		} catch (Exception e) {
			return null;
		}

	}

	private static synchronized String getPrimaryField( Connection connection, String beanName) {
		String tableName = T2E.toTableName(beanName);
		//DB db = DBs.dBcache.get(dbKey);
		String dataBaseName = null;// db.getDataName();
		String primaryKeyName = null;
		try {
			ResultSet primaryKeyResultSet = connection.getMetaData().getPrimaryKeys(dataBaseName, null, tableName);
			while (primaryKeyResultSet.next()) { // 遍历某个表的主键
				primaryKeyName = primaryKeyResultSet.getString("COLUMN_NAME");
			}
			return T2E.toField(primaryKeyName.toUpperCase());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	private static synchronized List<ForeignKey> getForeignKeies(Connection connection, String beanName) {
		String tableName = T2E.toTableName(beanName);
		//DB db = DBs.dBcache.get(dbKey);
		String dataBaseName = null;//db.getDataName();
		try {
			ResultSet foreignKeyResultSet = connection.getMetaData().getImportedKeys(dataBaseName, null, tableName);
			List<ForeignKey> list = new ArrayList<ForeignKey>();
			while (foreignKeyResultSet.next()) {// 遍历某个表的外键

				String fkColumnName = foreignKeyResultSet.getString("FKCOLUMN_NAME");
				String pkTableName = foreignKeyResultSet.getString("PKTABLE_NAME").toUpperCase();
				ForeignKey f = new ForeignKey();
				f.setForeignProperty(T2E.toField(fkColumnName));
				f.setReferDbBeanName(pkTableName);
				list.add(f);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ForeignKey>();
		}
	}

	private synchronized static List<Index> getIndex(Connection connection, String beanName) {
		String tableName = T2E.toTableName(beanName);
		//DB db = DBs.dBcache.get(dbKey);
		String dataBaseName = null;// db.getDataName();
		List<Index> indexes = new ArrayList<Index>();
		try {
			ResultSet indexResultSet = connection.getMetaData().getIndexInfo(dataBaseName, null, tableName, true, true);
			while (indexResultSet.next()) {// 遍历某个表的外键
				Index index = new Index();

				String columnName = indexResultSet.getString("COLUMN_NAME").toUpperCase();// 列名
				boolean nonUnique = indexResultSet.getBoolean("NON_UNIQUE");// 非唯一索引

				index.setIndexPropertyName(T2E.toField(columnName));
				index.setUnique(!nonUnique);
				indexes.add(index);

			}
			return indexes;

		} catch (SQLException e) {

			e.printStackTrace();
			return new ArrayList<Index>();
		}

	}
	
}
