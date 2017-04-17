package com.sooncode.soonjdbc.Service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.entity.User;
import com.sooncode.soonjdbc.service.JdbcService;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.sql.condition.Sort;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JdbcService__Test {
	@Autowired
	private JdbcService jdbcService;
	private static Logger logger = Logger.getLogger("JdbcService__Test.class");
	@Test
	public void gets (){
		User u = new User();
		u.setSex("1");
		//u.setName("hello jdbc");
		Conditions c = new Conditions(u);
		c.setCondition("name", LikeSign.LIKE,"hello jdbc");
		c.setOderBy("name", Sort.DESC);

		List<User> list = jdbcService.gets(c);
		logger.info(list);	
	}

}
