package com.sooncode.jdbc4json.db;

import javax.sql.DataSource;

 

public abstract class AbstractDataSource implements DataSource {
	
	 @Override
	 public int getLoginTimeout(){
		 return 0;
	 }
}
