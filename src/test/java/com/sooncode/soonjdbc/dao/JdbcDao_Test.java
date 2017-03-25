package com.sooncode.soonjdbc.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sooncode.soonjdbc.dao.JdbcDao;
import com.sooncode.soonjdbc.entity.ChooseCourse;
import com.sooncode.soonjdbc.entity.Clazz;
import com.sooncode.soonjdbc.entity.Course;
import com.sooncode.soonjdbc.entity.Friend;
import com.sooncode.soonjdbc.entity.Identity;
import com.sooncode.soonjdbc.entity.School;
import com.sooncode.soonjdbc.entity.Student;
import com.sooncode.soonjdbc.entity.User;
import com.sooncode.soonjdbc.page.Many2Many;
import com.sooncode.soonjdbc.page.One2Many;
import com.sooncode.soonjdbc.page.One2Many2Many;
import com.sooncode.soonjdbc.page.One2One;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;

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
		
		Student s = new Student();
		s.setStudentId("374928393875");
		s.setName("fjsldkjflsdkfj");
		dao.save(s);
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
     
	public void delete() {
		User u = new User();
		u.setId(9);
		dao.delete(u);
		u.setName("hh");
		dao.update(u);
	}
	@Test
    @Transactional
	public void transactional() {
		User u = new User();
		u.setId(16);
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
		
		List<One2One> list =  page.getOne2One()  ;
		
		logger.info("---------------------------------------------------------------");
		for (One2One  one2One : list) {
			Student st = one2One.getOne(Student.class);
			Identity ide = one2One.getOne(Identity.class);
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
		//c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dao.getPage(1L, 10L, c);
	    Many2Many<Student,ChooseCourse,Course> m2m = page.getMany2Many();

	    Student stu = m2m.getOne();
	    List<One2One > list = m2m.getMany();
	    
	    
		logger.info("---------------------------------------------------------------");
		logger.info(stu);
		logger.info("---------------------------------------------------------------");
		
		for (One2One  o2o : list) {
			//logger.info(o2o.getLeft() + " -------" + o2o.getRight());
			 
		}
		logger.info("---------------------------------------------------------------");
		 
	}
	/**
	 * 1 对1  分页
	 */
	@Test
	public void getPage41() {
		Student s = new Student();
		s.setStudentId("001");
		ChooseCourse cc = new ChooseCourse();
		Course  co= new Course ();
		Conditions c = new Conditions(cc,s,co);
		c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dao.getPage(1L, 10L, c);
		List<One2One> list = page.getOne2One();
		logger.info("---------------------------------------------------------------");
		for (One2One  o2o : list) {
			ChooseCourse choos = o2o.getOne(ChooseCourse.class);
			Student st = o2o.getOne(Student.class);
			Course cours = o2o.getOne(Course.class);
			logger.info(choos + " ## " + st + " ## "+ cours);
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
			List<One2One >list = many2Many.getMany();
			for (One2One  one2One : list) {
				
			}
			
		}
		logger.info("---------------------------------------------------------------");
		 
		 
		
	}
	/**
	 * 多对多 分页
	 */
	@Test
	public void getPage51() {
		 
		User u = new User();
		Friend f = new Friend();
		f.setMeUserId(16);
		Conditions c = new Conditions(f,u);
		 
		Page page = dao.getPage(1L, 10L, c);
		List<One2One  > o2os = page.getOne2One();
		logger.info("---------------------------------------------------------------");
		 
		for (One2One  o : o2os) {
		  Friend fr =	o.getOne(Friend.class);
		  User user =	o.getOne(User.class);
			logger.info(fr);
			logger.info(user);
			
		}
		 
		logger.info("---------------------------------------------------------------");
		
		
		
	}
	@Test
	public void getPage6() {
		Student s = new Student();
		ChooseCourse cc = new ChooseCourse();
		Course  co= new Course ();
		//co.setCourseId("001");
		Conditions c = new Conditions(co,cc,s);
		c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dao.getPage(1L, 10L, co,cc,s);
		List<Many2Many<Course,ChooseCourse,Student>> m2ms = page.getMany2Manys();
		
		
		logger.info("---------------------------------------------------------------");
		for (Many2Many<Course, ChooseCourse, Student> many2Many : m2ms) {
			Course cou = many2Many.getOne();
			logger.info( cou);
			List<One2One >list = many2Many.getMany();
			for (One2One one2One : list) {
				ChooseCourse choo = one2One.getOne(ChooseCourse.class);
				Student stude = one2One.getOne(Student.class);
				logger.info( choo +"---" + stude);
				
			}
			
		}
		logger.info("---------------------------------------------------------------");
		
		
		
	}
	
	
	@Test
	public void getPage7() {
		School school = new School();
		school.setSchoolId(1);
		Clazz clazz = new Clazz();
		Student student = new Student();
		 
	 
		Conditions c = new Conditions(school,clazz,student);
	 
		Page page = dao.getPage(1L, 10L, c);
		List<One2Many2Many<School, Clazz, Student>> o2m2ms = page.getOne2Many2Manys();
		
		
		logger.info("---------------------------------------------------------------");
		for (One2Many2Many<School, Clazz, Student> o2m2m : o2m2ms) {
			School sc = o2m2m.getOne();
			logger.info( sc );
			List<One2Many<Clazz, Student>>list = o2m2m.getOne2manys(); 
			for (One2Many<Clazz, Student> o2m : list) {
				Clazz cl = o2m.getOne();
				List<Student> stude = o2m.getMany();
				logger.info( cl);
				logger.info( stude );
				
			}
			
		}
		logger.info("---------------------------------------------------------------");
		
		
		
	}

}
