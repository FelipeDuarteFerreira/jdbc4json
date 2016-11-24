package com.sooncode.jdbc4json.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sooncode.example.entity.User;
import com.sooncode.jdbc4json.bean.JsonBean;
import com.sooncode.jdbc4json.sql.condition.Conditions;
import com.sooncode.jdbc4json.sql.condition.sign.LikeSign;

public class JdbcService__Test {
	private static Logger logger = Logger.getLogger("JdbcService__Test.class");
	private JdbcService jdbcService = JdbcServiceFactory.getJdbcService();
	
	
	@Test
	public void save(){
		User u = new User();
		u.setAge(123);
		u.setCreateDate(new Date());
		u.setName("hechen");
		long n = jdbcService.save(u);
		logger.info(n);
	}
	
	@Test
	public void get(){
		User u = new User();
		u.setId(5);
		Conditions c = new Conditions(u);
		u = jdbcService.get(c, User.class);
		logger.info(u);
	}
	@Test
	public void get2(){
		User u = new User();
		u.setId(5);
		u = jdbcService.get(u);
		logger.info(u);
	}
	@Test
	public void get3(){
		JsonBean user = new JsonBean("user");
		user.addField("id",5);
		User u = jdbcService.get(user,User.class);
		logger.info(u);
	}
	
	@Test
	public void gets1(){
		User u = new User();
		u.setName("hechen");
		Conditions c = new Conditions(u);
		List<User> list  = jdbcService.gets(c, User.class);
		logger.info(list);
	}
	@Test
	public void gets2(){
		User u = new User();
		u.setName("hechen");
		//Conditions c = new Conditions(u);
		List<User> list  = jdbcService.gets(u);
		logger.info(list);
	}
	@Test
	public void gets3(){
		JsonBean u = new JsonBean("user");
		u.addField("name","hechen");
		List<User> list  = jdbcService.gets(u,User.class);
		logger.info(list);
	}
	
	@Test
	public void count1(){
		JsonBean u = new JsonBean("user");
		u.addField("name","hechen");
		long n  = jdbcService.count("*", u);
		logger.info(n);
	}
	@Test
	public void count2(){
		User u = new User();
		u.setName("hechen");
		long n  = jdbcService.count("*", u);
		logger.info(n);
	}
	
	@Test
	public void count3(){
		User u = new User();
		u.setName("hechen");
		Conditions c = new Conditions(u);
		c.setCondition("name", LikeSign.L_LIKE);
		long n  = jdbcService.count("*", c);
		logger.info(n);
	}
	
	
}
