package com.sooncode.jdbc4json.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.jdbc4json.entity.ChooseCourse;
import com.sooncode.jdbc4json.entity.Clazz;
import com.sooncode.jdbc4json.entity.Course;
import com.sooncode.jdbc4json.entity.Identity;
import com.sooncode.jdbc4json.entity.Student;
import com.sooncode.jdbc4json.entity.User;
import com.sooncode.jdbc4json.page.Many2Many;
import com.sooncode.jdbc4json.page.One;
import com.sooncode.jdbc4json.page.One2Many;
import com.sooncode.jdbc4json.page.One2One;
import com.sooncode.jdbc4json.page.Page;
import com.sooncode.jdbc4json.sql.condition.Conditions;
import com.sooncode.jdbc4json.sql.condition.sign.LikeSign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JdbcDao_Test {
	@Autowired
	private JdbcDao dao;
	private static Logger logger = Logger.getLogger("JdbcDao_Test.class");

	@Test
	public void save() {
		User u = new User();
		u.setAge(123);
		u.setName("ni hao");
		u.setSex("1");
		dao.save(u);
	}

	@Test
	public void update() {
		User u = new User();
		u.setId(11);
		u.setAge(34);
		u.setName("ioouuy");
		u.setSex("1");
		dao.update(u);
	}

	@Test
	// @Transactional
	public void delete() {
		User u = new User();
		u.setId(9);
		dao.delete(u);
		u.setName("hh");
		dao.update(u);
	}

	@Test
	public void get() {
		User u = new User();
		u.setSex("1");
		u.setName("hechen");
		Conditions c = new Conditions(u);
		c.setCondition("name", LikeSign.LIKE);
	
		List<User> list = dao.gets(c);
		logger.info(list);

	}

	@Test
	public void get2() {
		User u = new User();
		u.setSex("1");
		List<User> list = dao.gets(u);
		logger.info(list.size());

	}

	@Test
	public void saveOrUpdate() {
		User u = new User();
		u.setId(9);
		u.setSex("1");
		u.setName("hello jdbc");
		long n = dao.saveOrUpdate(u);
		logger.info("------------:" + n);

	}

	@Test
	public void count() {
		User u = new User();
		u.setSex("1");
		Conditions c = new Conditions(u);
		long n = dao.count("*", c);
		logger.info("------------:" + n);

	}
    /**
     * 单表分页
     */
	@Test
	public void getPage1() {
		User u = new User();
		u.setSex("1");
		Conditions c = new Conditions(u);
		Page  page = dao.getPage(1L, 5L, c);
		One<User> users =   page.getOne();
		List<User> list = users.getOnes();
		logger.info("---------------------------------------------------------------");
		for (User user : list) {
			logger.info(user);
		}
		logger.info("---------------------------------------------------------------");
	}
	
	/**
	 * 1对1 分页
	 */
	@Test
	public void getPage2() {
		Student s = new Student();
		//s.setStudentId("001");
		Identity id = new Identity();
		Conditions c = new Conditions(s,id);
		Page  page = dao.getPage(1L, 5L, c);
		
		One2One<Student,Identity> o2o =  page.getOne2One() ;
		
		logger.info("---------------------------------------------------------------");
		for (int i =0;i<o2o.size();i++) {
			logger.info(o2o.getLeft(i) + " -------" + o2o.getRight(i));
			 
		}
		logger.info("---------------------------------------------------------------");
	}
	
	/**
	 * 1对多 分页
	 */
	@Test
	public void getPage3() {
		Student s = new Student();
		s.setAge(22); 
		Clazz clazz = new Clazz();
		clazz.setClazzId("002");
		Conditions c = new Conditions(clazz,s);
		Page page = dao.getPage(1L, 3L, c);
		One2Many<Clazz,Student> o2m = page.getOne2Many();
		
		logger.info("---------------------------------------------------------------");
		logger.info(o2m.getOne());
		logger.info("---------------------------------------------------------------");
		for ( Student st : o2m.getMany()) {
			logger.info(st);
		}
		logger.info("---------------------------------------------------------------");
		 
	}
	/**
	 * 多对多 分页
	 */
	@Test
	public void getPage4() {
		Student s = new Student();
		s.setStudentId("001");
		ChooseCourse cc = new ChooseCourse();
		Course  co= new Course ();
		Conditions c = new Conditions(s,cc,co);
		c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dao.getPage(1L, 10L, c);
	    Many2Many<Student,ChooseCourse,Course> m2m = page.getMany2Many();

		logger.info("---------------------------------------------------------------");
		logger.info(m2m.getLeft());
		logger.info("---------------------------------------------------------------");
		
		for (int i =0;i<m2m.getMany().size();i++) {
			logger.info(m2m.getMany().getLeft(i) + " -------" + m2m.getMany().getRight(i));
			 
		}
		logger.info("---------------------------------------------------------------");
		 
	}
	
	

}
