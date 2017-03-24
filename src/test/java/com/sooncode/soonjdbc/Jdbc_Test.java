package com.sooncode.jdbc4json;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sooncode.jdbc4json.sql.Parameter;

public class Jdbc_Test {
	private static Logger logger = Logger.getLogger("Jdbc_Test.class");
	private Jdbc jdbc ;

	@Test
	public void executeProcedure() {
		String sql = "{call proc_name2(?,?)}";
		Integer in = 1;
	//	Object r = jdbc.procedure(sql, in);
	//	logger.info(r);
	}
	
	@Test
	public void get() {
		String sql = "SELECT \n" +
				"	TEACHER_ID , \n" +
				"	TEACHER_AGE , \n" +
				"	TEACHER_NAME , \n" +
				"	CLAZZ_ID , \n" +
				"	SEX\n" +
				"	FROM TEACHER\n" +
				"	WHERE 1=1\n" +
				"	AND TEACHER.TEACHER_NAME = ? ORDER BY CLAZZ_ID ASC ";
		Parameter p = new Parameter(sql);
		p.addParameter("hechen");
	 
		List<Map<String,Object>> list = jdbc.gets(p);
	    logger.info(list);
	}
	 
}
