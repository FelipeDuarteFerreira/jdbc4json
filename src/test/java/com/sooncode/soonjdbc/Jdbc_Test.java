package com.sooncode.soonjdbc;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.sql.Parameter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class Jdbc_Test {
	private static Logger logger = Logger.getLogger("Jdbc_Test.class");
	@Autowired
	private Jdbc jdbc ;

	@Test
	public void executeProcedure() {
		Integer in = 1;
		Object r = jdbc.executeProcedure("proc_name2", in,2);
		logger.info(r);
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
