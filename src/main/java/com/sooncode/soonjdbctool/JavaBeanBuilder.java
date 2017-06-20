package com.sooncode.soonjdbctool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

public class JavaBeanBuilder {
	private static Log logger = LogFactory.getLog(JavaBeanBuilder.class);
	private JdbcTemplate jdbcTemplate;
    private String codePath;
    private String packageName;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		init();
	}

	
	
	public void setCodePath(String codePath) {
		 
		String[] str = codePath.split("/");
		String s = new String();
		for (String string : str) {
			s = s + string + File.separatorChar;
		}
		 this.codePath = s;
		 init();
	}

	
	
    public void setPackageName(String packageName) {
		this.packageName = packageName;
		init();
	}



	private void init(){
    	if(this.codePath != null  &&  this.jdbcTemplate != null && this.packageName != null){
    		logger.debug("【JavaBean代码生成路径】："+this.codePath);
    		List<Table> list = getTables();
    		for (Table table : list) {
    			String javaName = T2E.toClassName(  table.getTableName());
    			String fName = this.codePath  + javaName +".java";
    			String code = getEntityClassCode(table.getTableName());
    			writeFile(fName, code, "UTF-8");
    		}
    	}
    }

	public Table getTable( final String tableName) {

		return jdbcTemplate.execute(new ConnectionCallback<Table>() {

			@Override
			public Table doInConnection(Connection con) throws SQLException, DataAccessException {
			 
				Table t = new Table();
				DatabaseMetaData dm = con.getMetaData();
				String[] types = { "Table" };
				// 数据库名称/ userName/ 表名称 / 类型
				 
				ResultSet tableSet = dm.getTables(null,  null, tableName, types);//
				while (tableSet.next()) { // 遍历数据库的表
					 
					String tableRemarks = tableSet.getString("REMARKS");// 表注释

					t.setTableName(tableName);
					t.setTableRemarks(tableRemarks);
				}
				return t;
			}
		});

	}
	public List<Table> getTables() {
		
		return jdbcTemplate.execute(new ConnectionCallback<List<Table>>() {
			
			@Override
			public List<Table> doInConnection(Connection con) throws SQLException, DataAccessException {
				
				DatabaseMetaData dm = con.getMetaData();
				String url = dm.getURL();
				
				int n = url.indexOf('?');
				int m = url.lastIndexOf('/');
				String dataName  =  url.substring(m+1, n);
				 
				String[] types = { "Table" };
				 
				 
				 
				// 数据库名称/ userName/ 表名称 / 类型
				List<Table> list = new ArrayList<>();
				ResultSet tableSet = dm.getTables(dataName,  null, null, types);//
				while (tableSet.next()) { // 遍历数据库的表
					
					Table t = new Table();
					String tableRemarks = tableSet.getString("REMARKS");// 表注释
					String tableName = tableSet.getString("TABLE_NAME").toUpperCase();// 表注释
					
					t.setTableName(tableName);
					t.setTableRemarks(tableRemarks);
					list.add(t);
				}
				return list;
			}
		});
		
	}

	/**
	 * 查询数据库表的所有字段 构造 “字段属性模型”
	 * 
	 *
	 */
	public Map<String, Column> getColumns(  final String tableName) {

		return jdbcTemplate.execute(new ConnectionCallback<Map<String, Column>>() {

			@Override
			public Map<String, Column> doInConnection(Connection con) throws SQLException, DataAccessException {

				Map<String, Column> columnes = new HashMap<>();
				ResultSet columnSet = con.getMetaData().getColumns(null, "%", tableName, "%");
				while (columnSet.next()) { // 遍历某个表的字段

					String columnRemarks = columnSet.getString("REMARKS");

					String columnName = columnSet.getString("COLUMN_NAME".toUpperCase());
					String columnType = columnSet.getString("TYPE_NAME");
					int dataType = Integer.parseInt(columnSet.getString("DATA_TYPE"));

					String javaDataType = Jdbc2Java.getJavaData().get(Jdbc2Java.getJdbcData().get("" + dataType));

					String isAutoinCrement = columnSet.getString("IS_AUTOINCREMENT");

					Column column = new Column();
					column.setColumnName(columnName.toUpperCase());
					column.setPropertyName(T2E.toField(columnName));
					column.setDatabaseDataType(columnType);
					column.setJavaDataType(javaDataType);
					column.setColumnRemarks(columnRemarks);
					column.setIsAutoinCrement(isAutoinCrement);
					column.setColumnLength(column.getColumnName().length());
					column.setPropertyLength(column.getPropertyName().length());
					columnes.put(column.getColumnName(), column);

				}

				return columnes;
			}
		});

	}

	public PrimaryKey getPrimaryKey( final String tableName) {

		return jdbcTemplate.execute(new ConnectionCallback<PrimaryKey>() {

			@Override
			public PrimaryKey doInConnection(Connection con) throws SQLException, DataAccessException {
				PrimaryKey primaryKey = new PrimaryKey();

				ResultSet primaryKeyResultSet;

				primaryKeyResultSet = con.getMetaData().getPrimaryKeys( null, null, tableName);
				while (primaryKeyResultSet.next()) { // 遍历某个表的主键
					String primaryKeyName = primaryKeyResultSet.getString("COLUMN_NAME");
					String primaryKeySerial = primaryKeyResultSet.getString("KEY_SEQ");
					String primaryKeyDataType = "";
					primaryKey.setPrimaryPropertyName(T2E.toField(primaryKeyName));
					primaryKey.setPrimaryKeyName(primaryKeyName.toUpperCase());
					primaryKey.setDatabaseDataType(primaryKeyDataType);
					primaryKey.setPrimaryKeySerial(Short.parseShort(primaryKeySerial));
				}

				return primaryKey;
			}
		});

		 
	}

	 

	/**
	 * 獲取 實體類代碼
	 * 
	 * @param tableName
	 *            表名稱
	 * @return 實體類代碼
	 */
	public String getEntityClassCode(String tableName) {
		Table t = getTable( tableName);
		Map<String, Column> columns = getColumns( tableName);
		PrimaryKey pk = getPrimaryKey( tableName);

		Column pkColumn = columns.get(pk.getPrimaryKeyName());

		String code = "";
		String importString = "\r\n";
		importString += "package "+ this.packageName +";\r\n";
		importString += "import java.io.Serializable;\r\n";
		String an = "/**\r\n";
		an += "*" + t.getTableRemarks() + "\r\n";
		an += "* @author hechen \r\n";
		an += "* \r\n";
		an += "*/ \r\n";
		String classString = "public class " + T2E.toClassName(t.getTableName()) + " implements Serializable{ \r\n";
		classString += "\t private static final long serialVersionUID = 1L;\r\n";

		String pkString = "\t /** " + pkColumn.getColumnRemarks() + " */ \r\n";
		pkString += "\t private " + pkColumn.getJavaDataType() + " " + pkColumn.getPropertyName() + "; \r\n";
		code = importString + an + classString + pkString;

		String propertys = "";
		String getSetString = "";
		for (Entry<String, Column> en : columns.entrySet()) {
			Column c = en.getValue();
			// ----------------get set ---------------
			getSetString += "\t /** " + c.getColumnRemarks() + " */\r\n";
			getSetString += "\t public " + c.getJavaDataType() + " get" + T2E.toClassName(c.getColumnName()) + "() { \r\n";
			getSetString += "\t \t return " + c.getPropertyName() + ";\r\n";
			getSetString += "\t }\r\n";
			getSetString += "\t /** " + c.getColumnRemarks() + " */\r\n";
			getSetString += "\t public void set" + T2E.toClassName(c.getColumnName()) + "(" + c.getJavaDataType() + " " + c.getPropertyName() + ") {\r\n";
			getSetString += "\t \t this." + c.getPropertyName() + " = " + c.getPropertyName() + ";\r\n";
			getSetString += "\t }\r\n";
			getSetString += "\r\n";

			if (c.getPropertyName().equals(pkColumn.getPropertyName())) {
				continue;
			}
			String remarks = "\t /** " + c.getColumnRemarks() + " */\r\n";
			String coluString = "\t private " + c.getJavaDataType() + " " + c.getPropertyName() + " ;\r\n";
			remarks += coluString;
			propertys += remarks;

		}

		code = code + propertys + "\r\n" + getSetString + "}\r\n";
		return code;
	}

	public boolean writeFile(String fileName, String content, String encoding) {
		File file = new File(fileName);
		if (!file.exists()) { //文件不存在
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}else{//文件存在
			file.delete();
		}

		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(file, true);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(content);
			fileOutputStream.write(stringBuffer.toString().getBytes(encoding));

			fileOutputStream.close();
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} 

	}

}
