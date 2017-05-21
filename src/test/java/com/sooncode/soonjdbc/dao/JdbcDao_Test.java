package com.sooncode.soonjdbc.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sooncode.soonjdbc.ModelTransform;
 
import com.sooncode.soonjdbc.dao.JdbcDao;
import com.sooncode.soonjdbc.dao.tabletype.TableRelation;
import com.sooncode.soonjdbc.entity.ChooseCourse;
import com.sooncode.soonjdbc.entity.SooncodeClazz;
import com.sooncode.soonjdbc.entity.SooncodeCourse;
import com.sooncode.soonjdbc.entity.SooncodeIdentity;
import com.sooncode.soonjdbc.entity.SooncodeSchool;
import com.sooncode.soonjdbc.entity.SooncodeStudent;
import com.sooncode.soonjdbc.entity.SooncodeTeacher;
import com.sooncode.soonjdbc.entity.SooncodeTeaching;
import com.sooncode.soonjdbc.entity.SystemFriend;
import com.sooncode.soonjdbc.entity.SystemUser;
import com.sooncode.soonjdbc.model.StudentAndIdentity;
import com.sooncode.soonjdbc.page.Many2Many;
import com.sooncode.soonjdbc.page.One2Many;
import com.sooncode.soonjdbc.page.One2Many2Many;
import com.sooncode.soonjdbc.page.One2One;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.sql.condition.DateFormat4Sql;
import com.sooncode.soonjdbc.sql.condition.Sort;
import com.sooncode.soonjdbc.sql.condition.sign.EqualSign;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;
import com.sooncode.util.SJson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class JdbcDao_Test {
	@Autowired
	private JdbcDao dao;
	private static Logger logger = Logger.getLogger("JdbcDao_Test.class");

	@Test
	public void save() {
		SystemUser u = new SystemUser();
		u.setAge(123);
		u.setName("hello jdbc");
		u.setSex("1");
		u.setCreateDate(new Date());
		u.setUpdateDate(new Date());

    /* for (int i = 0 ;i<1000;i++) {
	     dao.save(u);
			
		}*/
		 
	
	}

	@Test
	public void update() {
		SystemUser u = new SystemUser();
		u.setId(11);
		u.setAge(34);
		u.setName("ioouuy");
		u.setSex("1");
		dao.update(u);
	}

	@Test
	public void saveOrUpdate(){
		SystemUser u = new SystemUser();
		u.setId(11809);
		u.setAge(34);
		u.setName("ioouuy");
		u.setSex("1");
		dao.saveOrUpdate(u);
	}
	
	
	@Test
	public void delete() {
		SystemUser u = new SystemUser();
		u.setId(9);
		dao.delete(u);
		u.setName("hh");
		dao.update(u);
	}

	@Test
	@Transactional
	public void transactional() {
		SystemUser u = new SystemUser();
		u.setId(20);
		dao.delete(u);
		//u.setName("hh");
		//dao.update(u);
		//int n = 1/0;
	}
	
	
	@Test
	public void max() {
		SystemUser u = new SystemUser();
		Conditions c = new Conditions(u);
		int max = dao.max("age",c);
		logger.info(max); 
	}
	
	@Test
	public void max2() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		//Conditions c = new Conditions(u);
		int max = dao.max("age",u);
		logger.info(max); 
	}

	
	
	
	
	
	@Test
	public void get() {
		SystemUser u = new SystemUser();
		Conditions c = new Conditions(u);
		c.setCondition("name", LikeSign.LIKE , "hello jdbc");
		c.setCondition("sex", EqualSign.NOT_EQ, "0");
		c.setCondition( "createDate" ,EqualSign.LT,new DateFormat4Sql().ymd(new Date()));
		c.setBetweenCondition("age", 10, 100);
		c.setInCondition("type", new String[]{"AA","BB"});
		c.setIsNullCondition("name");
		c.setIsNotNullCondition("sex");
		c.setOderBy("name", Sort.DESC);
		Page p = dao.getPage(1L, 2L, c);
		List<SystemUser> list = p.getOnes();
		logger.info(list);

	}

	@Test
	public void get2() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		List<SystemUser> list = dao.gets(u);
		logger.info(list);

	}

	 

	@Test
	public void count() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		Conditions c = new Conditions(u);
		long n = dao.count("*", c);
		logger.info("------------:" + n);

	}
	
	
	@Test
	public void getOne2One() {
		SooncodeStudent s = new SooncodeStudent();
		s.setStudentId("001");
	    
		One2One o2o = dao.getOne2One(s, new SooncodeClazz());
		s = o2o.getOne(SooncodeStudent.class);
		SooncodeClazz cl = o2o.getOne(SooncodeClazz.class);
		logger.info(s+"------------:" + cl);
		
	}

	/**
	 * 单表分页 查询：性别是‘男’的所有学生
	 */
	@Test
	public void getPage1() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		Conditions c = new Conditions(u);
		c.setOderBy("age", Sort.DESC);
		c.setIsNullCondition("createDate");
       // c.setInCondition("sex", new String[]{"1"});
		Page page = dao.getPage(1L, 5L, c);

		List<SystemUser> list = page.getOnes();
		logger.info("---------------------------------------------------------------");
		for (SystemUser SystemUser : list) {
			logger.info(SystemUser);
		}
		logger.info("---------------------------------------------------------------");
	}

	/**
	 * 查询 学生和与之对应的身份详情
	 */
	@Test
	public void getPage4One2One() {
		SooncodeStudent s = new SooncodeStudent();
		SooncodeIdentity id = new SooncodeIdentity();
		SooncodeClazz sc = new SooncodeClazz();
		Conditions c = new Conditions(s, id,sc);
		 
		Page page = dao.getPage(1L, 5L, c);

		List<One2One> list = page.getOne2One();

		logger.info("---------------------------------------------------------------");
		for (One2One one2One : list) {
			SooncodeStudent st = one2One.getOne(SooncodeStudent.class);
			SooncodeIdentity ide = one2One.getOne(SooncodeIdentity.class);
			SooncodeClazz sc2 = one2One.getOne(SooncodeClazz.class);
			
		 
			logger.info(new SJson(st));
			logger.info(new SJson(ide));
			logger.info(new SJson(sc2));
			logger.info("---------------------------------------------------------------");
			//StudentAndIdentity si = ModelTransform.getModel(one2One, StudentAndIdentity.class);
			//logger.info(new SJson(si));
		}
		logger.info("---------------------------------------------------------------");
	}

	/**
	 * 查询 学生 ，身份 ，班级信息
	 */
	@Test
	public void getPage21() {
		SooncodeStudent s = new SooncodeStudent();
		SooncodeIdentity id = new SooncodeIdentity();
		SooncodeClazz cl = new SooncodeClazz();
		Conditions c = new Conditions(s, id, cl);
		Page page = dao.getPage(1L, 5L, c);

		List<One2One> list = page.getOne2One();

		logger.info("---------------------------------------------------------------");
		for (One2One one2One : list) {
			SooncodeStudent st = one2One.getOne(SooncodeStudent.class);
			SooncodeIdentity ide = one2One.getOne(SooncodeIdentity.class);
			SooncodeClazz c1 = one2One.getOne(SooncodeClazz.class);
			ChooseCourse cc1 = one2One.getOne(ChooseCourse.class);
			logger.info(st + " -------" + ide + " -------" + c1 + " -------" + cc1);
		}
		logger.info("---------------------------------------------------------------");
	}

	/**
	 * 查询 班级id为‘002’的所有学生  ()
	 */
	@Test
	public void getPage3() {
		SooncodeStudent s = new SooncodeStudent();
		// s.setAge(22);
		SooncodeClazz clazz = new SooncodeClazz();
		clazz.setClazzId("002");
		//Conditions c = new Conditions(clazz, s);
		Page page = dao.getPage(1L, 10L, TableRelation.ONE_MANY, clazz,s);//you qi yi shi shi yong 
		One2Many<SooncodeClazz, SooncodeStudent> o2m = page.getOne2Many();
		SooncodeClazz cl = o2m.getOne();
		List<SooncodeStudent> stues = o2m.getMany();
		logger.info("---------------------------------------------------------------");
		logger.info(cl);
		logger.info(stues);
		logger.info("---------------------------------------------------------------");
		//SooncodeClazz cla = page.getOne2One().get(0).getOne(SooncodeClazz.class);
		//SooncodeStudent mo = page.getOne2One().get(0).getOne(SooncodeStudent.class);
		//logger.info(cla);
		//logger.info(mo);
		logger.info("---------------------------------------------------------------");

	}

	/**
	 * 查询 学生id 为 "001" 选修的所有课程
	 */
	@Test
	public void getPage4() {
		SooncodeStudent s = new SooncodeStudent();
		s.setStudentId("001");
		ChooseCourse cc = new ChooseCourse();
		SooncodeCourse co = new SooncodeCourse();
		Conditions c = new Conditions(s, cc, co);
		// c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dao.getPage(1L, 10L, c);
		Many2Many<SooncodeStudent, ChooseCourse, SooncodeCourse> m2m = page.getMany2Many();

		SooncodeStudent stu = m2m.getOne();
		List<One2One> list = m2m.getMany();

		logger.info("---------------------------------------------------------------");
		logger.info(stu);

		for (One2One o2o : list) {
			ChooseCourse chooseCourse = o2o.getOne(ChooseCourse.class);
			SooncodeCourse course = o2o.getOne(SooncodeCourse.class);
			logger.info(chooseCourse + "------------" + course);
		}
		logger.info("---------------------------------------------------------------");

	}

	/**
	 * 查询 学生 选修 课程 的情况
	 */
	@Test
	public void getPage41() {
		SooncodeStudent s = new SooncodeStudent();
		s.setStudentId("001");
		ChooseCourse cc = new ChooseCourse();
		SooncodeCourse co = new SooncodeCourse();
		Conditions c = new Conditions(cc, s, co);
		c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dao.getPage(1L, 10L, c);
		List<One2One> list = page.getOne2One();
		logger.info("---------------------------------------------------------------");
		for (One2One o2o : list) {
			ChooseCourse choos = o2o.getOne(ChooseCourse.class);
			SooncodeStudent st = o2o.getOne(SooncodeStudent.class);
			SooncodeCourse cours = o2o.getOne(SooncodeCourse.class);
			logger.info(choos + " ## " + st + " ## " + cours);
		}
		logger.info("---------------------------------------------------------------");

	}

	/**
	 * 查询用户id 为 16 的所有好友
	 */
	@Test
	public void getPage42() {
		SystemUser u = new SystemUser();

		SystemFriend f = new SystemFriend();
		f.setMeUserId(16);
		Conditions c = new Conditions(f, u);

		Page page = dao.getPage(1L, 10L, c);
		List<One2One> o2os = page.getOne2One();
		logger.info("---------------------------------------------------------------");
		for (One2One o2o : o2os) {
			SystemFriend fr = o2o.getOne(SystemFriend.class);
			SystemUser user = o2o.getOne(SystemUser.class);
			logger.info(fr + " ----" + user);
		}

		logger.info("---------------------------------------------------------------");

	}

	/**
	 * 查询：选修 课程id为‘001’ 的所有学生
	 */
	@Test
	public void getPage5() {
		SooncodeStudent s = new SooncodeStudent();
		ChooseCourse cc = new ChooseCourse();
		SooncodeCourse co = new SooncodeCourse();
		co.setCourseId("001");
		Conditions c = new Conditions(co, cc, s);
		c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dao.getPage(1L, 10L, c);
		List<Many2Many<SooncodeCourse, ChooseCourse, SooncodeStudent>> m2ms = page.getMany2Manys();

		logger.info("---------------------------------------------------------------");
		for (Many2Many<SooncodeCourse, ChooseCourse, SooncodeStudent> many2Many : m2ms) {
			SooncodeCourse cou = many2Many.getOne();
			logger.info(cou);
			List<One2One> list = many2Many.getMany();
			for (One2One o : list) {
				ChooseCourse ChooseCourse = o.getOne(ChooseCourse.class);
				SooncodeStudent SooncodeStudent = o.getOne(SooncodeStudent.class);
				logger.info(ChooseCourse + " -----  " + SooncodeStudent);
			}

		}
		logger.info("---------------------------------------------------------------");

	}

	/**
	 * 查询 学校id为‘1’ 的所有班级，和班级对应 的所有学生
	 */

	@Test
	public void getPage7() {
		SooncodeSchool school = new SooncodeSchool();
		school.setSchoolId(1);
		SooncodeClazz clazz = new SooncodeClazz();
		SooncodeStudent student = new SooncodeStudent();

		Conditions c = new Conditions(school, clazz, student);

		Page page = dao.getPage(1L, 10L, c);
		List<One2Many2Many<SooncodeSchool, SooncodeClazz, SooncodeStudent>> o2m2ms = page.getOne2Many2Manys();

		logger.info("---------------------------------------------------------------");
		for (One2Many2Many<SooncodeSchool, SooncodeClazz, SooncodeStudent> o2m2m : o2m2ms) {
			SooncodeSchool sc = o2m2m.getOne();
			logger.info(sc);
			List<One2Many<SooncodeClazz, SooncodeStudent>> list = o2m2m.getOne2manys();
			for (One2Many<SooncodeClazz, SooncodeStudent> o2m : list) {
				SooncodeClazz cl = o2m.getOne();
				List<SooncodeStudent> stude = o2m.getMany();
				logger.info(cl);
				logger.info(stude);

			}

		}
		logger.info("---------------------------------------------------------------");

	}

	/**
	 * 查询：班级id="001" 的班级的学校信息，和班长信息,班主任信息。
	 */
	@Test
	public void getPage8() {

		SooncodeClazz clazz = new SooncodeClazz();
		// clazz.setClazzId("001");
		SooncodeStudent student = new SooncodeStudent();
		SooncodeSchool school = new SooncodeSchool();
		SooncodeTeacher teacher = new SooncodeTeacher();
		Conditions c = new Conditions(clazz, school, student, teacher);

		Page page = dao.getPage(1L, 10L, c);
		List<One2One> o2os = page.getOne2One();

		logger.info("---------------------------------------------------------------");
		for (One2One o2o : o2os) {
			SooncodeSchool sc = o2o.getOne(SooncodeSchool.class);
			SooncodeClazz cl = o2o.getOne(SooncodeClazz.class);
			SooncodeStudent s = o2o.getOne(SooncodeStudent.class);
			SooncodeTeacher t = o2o.getOne(SooncodeTeacher.class);
			logger.info(cl + " --- " + sc + " --- " + s + "---" + t);

		}
		logger.info("---------------------------------------------------------------");

	}

	/**
	 * 查询：班级id="001" 的班级的学校信息，和班长信息。
	 */
	@Test
	public void getPage9() {

		SooncodeSchool school = new SooncodeSchool();
		school.setSchoolId(1);
		SooncodeTeacher teacher = new SooncodeTeacher();
		Conditions c = new Conditions(school, teacher);

		Page page = dao.getPage(1L, 10L, c);
		List<One2One> o2os = page.getOne2One();

		logger.info("---------------------------------------------------------------");
		for (One2One o2o : o2os) {
			SooncodeSchool s = o2o.getOne(SooncodeSchool.class);
			SooncodeTeacher t = o2o.getOne(SooncodeTeacher.class);

			logger.info(s + " --- " + t);

		}
		logger.info("---------------------------------------------------------------");

	}

	/**
	 * 查询：老师id="4" 的老师，是哪个学校的校长。
	 */
	@Test
	public void getPage10() {

		SooncodeSchool school = new SooncodeSchool();
		// school.setSchoolId(1);
		SooncodeTeacher teacher = new SooncodeTeacher();
		teacher.setTeacherId(4);
		Conditions c = new Conditions(teacher, school);

		Page page = dao.getPage(1L, 10L, c);
		List<One2One> o2os = page.getOne2One();

		logger.info("---------------------------------------------------------------");
		for (One2One o2o : o2os) {
			SooncodeSchool s = o2o.getOne(SooncodeSchool.class);
			SooncodeTeacher t = o2o.getOne(SooncodeTeacher.class);

			logger.info(t + " --- " + s);

		}
		logger.info("---------------------------------------------------------------");

	}

	@Test
	public void getPage11() {

		SooncodeClazz clazz = new SooncodeClazz();
		clazz.setClazzId("001");
		SooncodeTeaching teaching = new SooncodeTeaching();
		SooncodeTeacher teacher = new SooncodeTeacher();
		Conditions c = new Conditions(clazz, teaching, teacher);

		Page page = dao.getPage(1L, 10L, c);
		List<Many2Many<SooncodeClazz, SooncodeTeaching, SooncodeTeacher>> m2ms = page.getMany2Manys();

		logger.info("---------------------------------------------------------------");
		for (Many2Many<SooncodeClazz, SooncodeTeaching, SooncodeTeacher> m2m : m2ms) {

			SooncodeClazz cl = m2m.getOne();
			logger.info(cl);
			List<One2One> o2o = m2m.getMany();
			for (One2One o : o2o) {
				SooncodeTeaching th = o.getOne(SooncodeTeaching.class);
				SooncodeTeacher te = o.getOne(SooncodeTeacher.class);
				logger.info(th + "---" + te);
			}

		}
		logger.info("---------------------------------------------------------------");

	}
	@Test
	public void getPage12() {
		 String str = null;
		 str.trim();
		 
		 
	}
	
	 
}
