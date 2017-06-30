package com.sooncode.soonjdbc.sql.comsql;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.entity.SystemUser;
import com.sooncode.soonjdbc.sql.Parameter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SelectSqlBuilder__Tset {
	private static Logger logger = Logger.getLogger("InsertSqlBuilder__Tset");

	@Autowired
	private Jdbc jdbc;

	@Test
	public void getPreparedSql() {
		SystemUser user = new SystemUser();
		user.setId(1);

		DbBean db = jdbc.getDbBean(user);
		SqlBuilder sb = new DeletetSqlBuilder();
		Parameter ps = sb.getParameter(db);
		long n = jdbc.update(ps);
		logger.info("----------------------" + n);

	}

}
