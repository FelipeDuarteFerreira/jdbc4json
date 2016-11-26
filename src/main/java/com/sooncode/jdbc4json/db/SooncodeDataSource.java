package com.sooncode.jdbc4json.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class SooncodeDataSource implements DataSource {
    private DB db;
	
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection connection;
		 
		String DRIVER = db.getDriver();
		String IP = db.getIp();
		String PORT = db.getPort();
		String DATA_NAME = db.getDataName();
		String ENCODEING = db.getEncodeing();
		String USERNAME = db.getUserName();
		String PASSWORD = db.getPassword();
		String mysqlUrl = DB4Parperties.getMysqlUrl(IP, PORT, DATA_NAME, ENCODEING);

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
			return null;
		}
		try {
			connection = DriverManager.getConnection(mysqlUrl, USERNAME, PASSWORD);
			return connection;
		} catch (SQLException e) {
			 e.printStackTrace();
			return null;
		}

	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	
}
