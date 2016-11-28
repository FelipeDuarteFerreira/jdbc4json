package com.sooncode.example_test.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.example.entity.User;
import com.sooncode.example.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
 
@ContextConfiguration("/applicationContext.xml")
public class UserService__Test {
	private static Logger logger = Logger.getLogger("EduStatClassTeachingService____Test.class");
	@Autowired
	private UserService userService;
	
	
	
	@Test
	public void updateUser (){
		
		User u = new User();
		u.setId(1);
		u.setName("shfklj");
		boolean b1 = userService.updateUser(u);
		 logger.info(b1);
		 
		 DriverManagerDataSource d ;
		 JdbcTemplate jt;
		 
	}
	
}
