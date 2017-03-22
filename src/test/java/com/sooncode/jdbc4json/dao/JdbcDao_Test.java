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
		 
		List<User> list = page.getOnes();
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
		
		List<One2One<Student,Identity>> list =  page.getOne2Ones() ;
		
		logger.info("---------------------------------------------------------------");
		for (One2One<Student, Identity> one2One : list) {
			Student st = one2One.getLeft();
			Identity ide = one2One.getRight();
			logger.info(st + " -------" + ide);
		}
		logger.info("---------------------------------------------------------------");
	}
	
	/**
	 * 1对多 分页
	 */
	@Test
	public void getPage3() {
		Student s = new Student();
		//s.setAge(22); 
		Clazz clazz = new Clazz();
		clazz.setClazzId("002");
		Conditions c = new Conditions(clazz,s);
		Page page = dao.getPage(1L, 3L, c);
		One2Many<Clazz,Student> o2m = page.getOne2Many();
	    Clazz cl = o2m.getOne();
		List<Student> stues = o2m.getMany();
		logger.info("---------------------------------------------------------------");
		logger.info(cl);
		logger.info("---------------------------------------------------------------");
		for ( Student st : stues) {
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

	    Student stu = m2m.getOne();
	    List<One2One<ChooseCourse, Course>> list = m2m.getMany();
	    
	    
		logger.info("---------------------------------------------------------------");
		logger.info(stu);
		logger.info("---------------------------------------------------------------");
		
		for (One2One<ChooseCourse, Course> o2o : list) {
			logger.info(o2o.getLeft() + " -------" + o2o.getRight());
			 
		}
		logger.info("---------------------------------------------------------------");
		 
	}
	/**
	 * 多对多 分页
	 */
	@Test
	public void getPage5() {
		Student s = new Student();
		ChooseCourse cc = new ChooseCourse();
		Course  co= new Course ();
		co.setCourseId("001");
		Conditions c = new Conditions(co,cc,s);
		c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dao.getPage(1L, 10L, c);
		List<Many2Many<Course,ChooseCourse,Student>> m2ms = page.getMany2Manys();
	 
		
		logger.info("---------------------------------------------------------------");
		for (Many2Many<Course, ChooseCourse, Student> many2Many : m2ms) {
			Course cou = many2Many.getOne();
			logger.info( cou);
			List<One2One<ChooseCourse, Student>>list = many2Many.getMany();
			for (One2One<ChooseCourse, Student> one2One : list) {
				ChooseCourse choo = one2One.getLeft();
				Student stude = one2One.getRight();
				logger.info( choo +"---" + stude);
				
			}
			
		}
		logger.info("---------------------------------------------------------------");
		 
		 
		
	}
	
	

}
