package com.sooncode.soonjdbc.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.SystemUser;
import com.sooncode.soonjdbc.constant.Sort;
 
import com.sooncode.soonjdbc.service.JdbcService;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.sql.condition.ConditionsBuilderProcess;
 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JdbcService__Test {
	@Autowired
	private JdbcService jdbcService;
	private static Logger logger = Logger.getLogger("JdbcService__Test.class");
	@Test
	public void gets (){
		 
	}
	
	
	@Test
	public void test (){
		SystemUser u = new SystemUser();
		
		List<SystemUser> list = new ArrayList<>();
		list= jdbcService.gets(u);
		
		for (SystemUser su : list) {
			String email = su.getEmail();
			
			String [] sts = email.split("@");
			
			SystemUser s = new SystemUser();
			s.setSystemUserId(su.getSystemUserId());
			s.setEmail(sts[0]);
			
			jdbcService.update(s);
		}
		 
	}

}
