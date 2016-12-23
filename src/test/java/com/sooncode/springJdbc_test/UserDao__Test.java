package com.sooncode.springJdbc_test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserDao__Test {
	private static Logger logger = Logger.getLogger("UserDao__Test.class");
	@Autowired
	private UserDao userDao;

	@Test
	public void delete() {
		int i = userDao.delete(8);
		logger.info(i);
	}

	@Test
	public void transactional() {
		int i = userDao.delete(8);
		int j = userDao.update(8, "hechen");
		logger.info(i + " ****** " + j);
	}

	@Test
	public void subtractBalance()  {

		boolean b;
		try {
			b = userDao.subtractBalance(8, 100);
			logger.info("###################"+b);
		} catch (BalanceInsufficientEx e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
