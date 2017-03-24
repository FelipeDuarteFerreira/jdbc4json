package com.sooncode.soonjdbc.sql;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sooncode.soonjdbc.sql.xml.SqlXml;
import com.sooncode.util.PathUtil;

 

 

public class SqlXml_Test {
	private static Logger logger = Logger.getLogger("SqlXml_Test.class"); 
	
	@Test
	public void getSql(){
		
		SqlXml xml = new SqlXml(PathUtil.getClassPath()+"com\\sooncode\\jdbc\\sql\\sql.xml");
		  
	}
	
}

