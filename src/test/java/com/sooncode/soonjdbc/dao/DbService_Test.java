package com.sooncode.soonjdbc.dao;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sooncode.soonjdbc.constant.Sort;
import com.sooncode.soonjdbc.dao.polymerization.Polymerization;
import com.sooncode.soonjdbc.dao.polymerization.PolymerizationModel;
import com.sooncode.soonjdbc.entity.ClassStudent;
import com.sooncode.soonjdbc.entity.ClassStudentDbModel;
import com.sooncode.soonjdbc.entity.SystemFriend;
import com.sooncode.soonjdbc.entity.SystemFriendDbModel;
import com.sooncode.soonjdbc.entity.SystemUser;
import com.sooncode.soonjdbc.entity.SystemUserDbModel;
import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;
import com.sooncode.soonjdbc.sql.condition.sign.EqualSign;
import com.sooncode.soonjdbc.sql.condition.sign.InSign;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;
import com.sooncode.soonjdbc.sql.condition.sign.NullSign;
import com.sooncode.soonjdbc.util.DbModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class DbService_Test {
	@Autowired
	private DbService dbService;
	private static Logger logger = Logger.getLogger("JdbcDao_Test.class");

	 

	@Test
	public void save() {
		SystemUser user = new SystemUser();// system_user
		user.setAge(123);
		user.setName("hello jdbc");
		user.setSex("1");
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
 
		
		SystemUserDbModel sudm = new SystemUserDbModel(user);
		long size = dbService.save(sudm);
		 
		logger.info("-------------------------------------------------" );
		logger.info("size=" + size);
		logger.info("-------------------------------------------------" );

	}

	/**
	 * 测试 批量保存
	 */
	@Test
	public void saves4batch() {
		SystemUser u = new SystemUser();
		// u.setId(1);
		u.setAge(123);
		u.setAddress("heco");
		u.setName("hello jdbc");
		u.setSex("1");
		u.setCreateDate(new Date());
		u.setUpdateDate(new Date());
		SystemUser u2 = new SystemUser();
		// u2.setId(2);
		u2.setName("fjlskjlk");
		u2.setSex("0");
		u2.setCreateDate(new Date());
		u2.setUpdateDate(new Date());

		List<DbModel> list = new ArrayList<>();
		list.add(new SystemUserDbModel(u));
		//list.add(new SystemUserDbModel(u2));
		int[] ids = dbService.saves(list);
		logger.info(ids);

	}
	
	/**
	 * 测试 非批量保存
	 */
	@Test
	public void saves() {
		SystemUser u = new SystemUser();
		// u.setId(1);
		u.setAge(123);
		u.setName("hello jdbc");
		u.setSex("1");
		u.setCreateDate(new Date());
		u.setUpdateDate(new Date());
		 
		SystemFriend fr = new SystemFriend();
		fr.setFriendId(34345);
		
		List<DbModel> list = new ArrayList<>();
		list.add(new SystemUserDbModel(u));
		list.add(new SystemFriendDbModel(fr));
		int[] ids = dbService.saves(list);
		logger.info(ids);
		
	}

	@Test
	public void update() {
		ClassStudent cs = new ClassStudent();
		cs.setClassId(1);
		cs.setStudentId(2);
		cs.setStudentName("hechn");
		// u.setName("HE CHEN ");
		// u.setSex("1");
		dbService.update(new ClassStudentDbModel(cs));
	}
	
	
	@Test
	public void update3() {
		SystemUserDbModel sudm = new SystemUserDbModel(); 
		sudm.userId.setValue(2213);
		sudm.age.setValue(34);
		sudm.createDate.setValue(new Date());
		
		
		long size = dbService.update(sudm);
		
		logger.info("--------------------------------------------------");
		logger.info("update size " + size);
		logger.info("--------------------------------------------------");
	}

	 

	@Test
	public void updates1() {
		SystemUserDbModel u = new SystemUserDbModel();
		u.age.setValue(12);
		u.name.setValue("hechn");
		SystemUserDbModel sudm = new SystemUserDbModel();
		Conditions c = new Conditions(sudm);
		c.setCondition(sudm.userId, InSign.IN, new String[] { "123", "456" });
		c.setCondition(sudm.name, LikeSign.R_LIKE, "hec");
		long n = dbService.updates(c, u);
		logger.info(n);
	}

	@Test
	public void saveOrUpdate() {
		SystemUser u = new SystemUser();
		u.setUserId(2207);
		u.setAge(34);
		u.setName("IOOUUY");
		u.setSex("1");
		
		dbService.saveOrUpdate(new SystemUserDbModel(u));
	}

	@Test
	public void deletes() {
		Conditions c = new Conditions(new SystemUserDbModel());
		c.setCondition(new SystemUserDbModel().createDate,NullSign.IS_NULL);
		dbService.deletes(c);

	}

	@Test
	public void deletes2() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		dbService.deletes(new SystemUserDbModel(u));

	}

	@Test
	public void delete() {
		SystemUser u = new SystemUser();
		u.setUserId(2209);
		u.setAddress("hsdk");
		u.setAge(34);
		dbService.delete(new SystemUserDbModel(u));

	}

	@Test
	@Transactional
	public void transactional() {
		SystemUser u = new SystemUser();
		u.setUserId(20);
		dbService.delete(new SystemUserDbModel(u));
		// u.setName("hh");
		// dbService.update(u);
		// int n = 1/0;
	}

	 

	@Test
	public void polymerization() {
		Conditions c = new Conditions(new SystemUserDbModel());
		c.setCondition(new SystemUserDbModel().name, LikeSign.LIKE, "hechen");
		int max = dbService.polymerization(Polymerization.MAX, c, new SystemUserDbModel().age);
		logger.info(max);
	}

	@Test
	public void polymerization2() {
		 
		Conditions c = new Conditions(new SystemUserDbModel(new SystemUser())); 
		c.setGroupBy(new SystemUserDbModel().sex);
		List<PolymerizationModel<SystemUser>> pms = dbService.polymerization(Polymerization.MIN, c, new SystemUserDbModel().age, new SystemUserDbModel().name);
		for (PolymerizationModel<SystemUser> pm : pms) {
			logger.info(pm.getSize());
			logger.info(pm.getEntity());
		}
	}

	@Test
	public void max2() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		SystemUserDbModel sudm = new SystemUserDbModel(u);
		int max = dbService.polymerization(Polymerization.MAX, sudm, new SystemUserDbModel().age);
		logger.info(max);
	}

	@Test
	public void get() {
		 SystemUser u = new SystemUser();
		 u.setCreateDate(new Date());
		 u.setAge(23);
		 Conditions c = new Conditions( new SystemUserDbModel(u));
		 c.setCondition(new SystemUserDbModel().name, LikeSign.LIKE , "he");
		// c.setCondition(u.sex(), EqualSign.NOT_EQ , "0");
		// c.setCondition( "createDate" ,EqualSign.LT,new Date(),
		// DATE_FORMAT.yyyy_MM_dd);
		// c.setCondition( "createDate" ,EqualSign.LT,"2017-06-13",
		// DATE_FORMAT.yyyy_MM_dd);
		// c.setCondition( u.createDate() ,BetweenSign.BETWEEN_AND,new Date(),new
		// Date(), DATE_FORMAT.yyyy_MM_dd);
		// c.setCondition(u.age(),BetweenSign.NOT_BETWEEN_AND, 10, 100);
		// c.setCondition(u.type(), InSign.IN, new String[]{"AA","BB"});
		// c.setCondition("name",NullSign.IS_NOT_NULL);
		// c.setCondition("sex",NullSign.IS_NULL);
		// c.setOderBy("name", Sort.DESC);
		// c.setOderBy("sex", Sort.ASC);
		  Page p = dbService.getPage(1L, 2L, c);
		  List<SystemUser> list = p.getOnes();
		  logger.info(list);

		 

	}

	@Test
	public void get2() {
		 
		SystemUserDbModel sudm  = new SystemUserDbModel();
		sudm.sex.setValue("1");
		List<SystemUser> list = dbService.gets(sudm);
		logger.info(list);

	}

	@Test
	public void gets() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		Conditions c =  new Conditions(new SystemUserDbModel(u));
		c.setCondition(new SystemUserDbModel().name, LikeSign.LIKE, "he");
		c.setCondition(new SystemUserDbModel().age, EqualSign.GT, 23);
		List<SystemUser> list = dbService.gets(c);
		logger.info(list);

	}

	@Test
	public void count() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		Conditions c = new Conditions(new SystemUserDbModel(u));
		long n = dbService.polymerization(Polymerization.COUNT, c, new SystemUserDbModel().userId);
		logger.info("------------:" + n);

	}

	@Test
	public void count2() {
		SystemUser u = new SystemUser();
		// u.setSex("1");
		Conditions c = new Conditions(new SystemUserDbModel(u));
		c.setGroupBy(new SystemUserDbModel().sex);
		List<PolymerizationModel<SystemUser>> list = dbService.polymerization(Polymerization.COUNT, c, new SystemUserDbModel().userId,new SystemUserDbModel().age,new SystemUserDbModel().name );
		logger.info("------------:" + list.size());

	}

	@Test
	public void sum() {
		SystemUser u = new SystemUser();
		Conditions c = new Conditions(new SystemUserDbModel(u));
		Object n = dbService.polymerization(Polymerization.SUM, c, new SystemUserDbModel().name);
		logger.info("------------:" + n);

	}

	@Test
	public void sum2() {
		SystemUser u = new SystemUser();
		// u.setSex("0");
		Conditions c = new Conditions(new SystemUserDbModel(u));
		c.setGroupBy(new SystemUserDbModel().sex);
		List<PolymerizationModel<SystemUser>> list = dbService.polymerization(Polymerization.COUNT, c, new SystemUserDbModel().userId,new SystemUserDbModel().age,new SystemUserDbModel().name );
		logger.info("------------:" + list);

	}

	@Test
	public void avg() {
		SystemUser u = new SystemUser();
		// u.setSex("0");
		Conditions c = new Conditions(new SystemUserDbModel(u));
		c.setCondition(new SystemUserDbModel().name, EqualSign.EQ, "hechen");
		Object n = dbService.polymerization(Polymerization.AVG, c, new SystemUserDbModel().sex);
		logger.info("------------:" + n);

	}

	 

	/**
	 * 单表分页 查询：性别是‘男’的所有学生
	 */
	@Test
	public void getOnes() {
		SystemUser u = new SystemUser();
		u.setSex("1");
		Conditions c = new Conditions(new SystemUserDbModel(u));
		c.setOderBy(new SystemUserDbModel().age, Sort.DESC);
		 
		Page page = dbService.getPage(1L, 5L, c);

		List<SystemUser> list = page.getOnes();
		logger.info("-------" + page.getTotal() + "-------------------------------" + page.getTotalPages() + "-------------------------");
		for (SystemUser SystemUser : list) {
			logger.info(SystemUser);
		}
		logger.info("---------------------------------------------------------------");
	}

	
	
	
	
	/**
	 * 查询 学生和与之对应的身份详情
	 */
	/* 
	@Test
	public void getOne2One2() {
		SooncodeStudent s = new SooncodeStudent();
		SooncodeIdentity id = new SooncodeIdentity();
		SooncodeClazz sc = new SooncodeClazz();
		Conditions c = ConditionsBuilderProcess.getConditions(s, id, sc);

		Page page = dbService.getPage(1L, 5L, c);

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
			// StudentAndIdentity si = ModelTransform.getModel(one2One,
			// StudentAndIdentity.class);
			// logger.info(new SJson(si));
		}
		logger.info("---------------------------------------------------------------");
	}

    */
	/**
	 * 查询 学生 ，身份 ，班级信息
	 */
	
	/*
	@Test
	public void getOne2One0() {
		SooncodeStudent s = new SooncodeStudent();
		SooncodeIdentity id = new SooncodeIdentity();
		SooncodeClazz cl = new SooncodeClazz();
		Conditions c = ConditionsBuilderProcess.getConditions(s, id, cl);
		Page page = dbService.getPage(1L, 5L, c);

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
	*/
	/**
	 * 查询 班级id为‘002’的所有学生 ()
	 */
	
 
 
 
	/**
	 * 查询 学生id 为 "001" 选修的所有课程
	 */
	
	/*
	@Test
	public void getMany2Many() {
		SooncodeStudent s = new SooncodeStudent();
		s.setStudentId("001");
		ChooseCourse cc = new ChooseCourse();
		SooncodeCourse co = new SooncodeCourse();
		Conditions c = ConditionsBuilderProcess.getConditions(s, cc, co);
		// c.setBetweenCondition("chooseCourse.score", 50, 100);
		Page page = dbService.getPage(1L, 10L, c);
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
	*/
	/**
	 * 查询 学生 选修 课程 的情况
	 */
	
	/*
	@Test
	public void getOne2One1() {
		SooncodeStudent s = new SooncodeStudent();
		s.setStudentId("001");
		ChooseCourse cc = new ChooseCourse();
		SooncodeCourse co = new SooncodeCourse();
		Conditions c = ConditionsBuilderProcess.getConditions(cc, s, co);
		c.setCondition("chooseCourse.score", BetweenSign.NOT_BETWEEN_AND, 50, 100);
		Page page = dbService.getPage(1L, 10L, c);
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
	*/
	/**
	 * 查询用户id 为 16 的所有好友
	 */
	/*
	@Test
	public void getOne2One() {
		SystemUser u = new SystemUser();

		SystemFriend f = new SystemFriend();
		f.setMeUserId(16);
		Conditions c = ConditionsBuilderProcess.getConditions(f, u);

		Page page = dbService.getPage(1L, 10L, c);
		List<One2One> o2os = page.getOne2One();
		logger.info("---------------------------------------------------------------");
		for (One2One o2o : o2os) {
			SystemFriend fr = o2o.getOne(SystemFriend.class);
			SystemUser user = o2o.getOne(SystemUser.class);
			logger.info(fr + " ----" + user);
		}

		logger.info("---------------------------------------------------------------");

	}
	*/
	/**
	 * 查询：选修 课程id为‘001’ 的所有学生
	 */
	/*
	@Test
	public void getMany2Manys() {
		SooncodeStudent s = new SooncodeStudent();
		ChooseCourse cc = new ChooseCourse();
		SooncodeCourse co = new SooncodeCourse();
		co.setCourseId("001");
		Conditions c = ConditionsBuilderProcess.getConditions(co, cc, s);
		c.setCondition("chooseCourse.score", BetweenSign.BETWEEN_AND, 50, 100);
		Page page = dbService.getPage(1L, 10L, c);
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
	*/
	/**
	 * 查询 学校id为‘1’ 的所有班级，和班级对应 的所有学生
	 */
	/*
	@Test
	public void getOne2Many2Manys() {
		SooncodeSchool school = new SooncodeSchool();
		school.setSchoolId(1);
		SooncodeClazz clazz = new SooncodeClazz();
		SooncodeStudent student = new SooncodeStudent();

		Conditions c = ConditionsBuilderProcess.getConditions(school, clazz, student);

		Page page = dbService.getPage(1L, 10L, c);
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
	*/
	/**
	 * 查询：班级id="001" 的班级的学校信息，和班长信息,班主任信息。
	 */
	/*
	@Test
	public void getPage8() {

		SooncodeClazz clazz = new SooncodeClazz();
		// clazz.setClazzId("001");
		SooncodeStudent student = new SooncodeStudent();
		SooncodeSchool school = new SooncodeSchool();
		SooncodeTeacher teacher = new SooncodeTeacher();
		Conditions c = ConditionsBuilderProcess.getConditions(clazz, school, student, teacher);

		Page page = dbService.getPage(1L, 10L, c);
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
	*/
	/**
	 * 查询：班级id="001" 的班级的学校信息，和班长信息。
	 */
	/*
	@Test
	public void getPage9() {

		SooncodeSchool school = new SooncodeSchool();
		school.setSchoolId(1);
		SooncodeTeacher teacher = new SooncodeTeacher();
		Conditions c = ConditionsBuilderProcess.getConditions(school, teacher);

		Page page = dbService.getPage(1L, 10L, c);
		List<One2One> o2os = page.getOne2One();

		logger.info("---------------------------------------------------------------");
		for (One2One o2o : o2os) {
			SooncodeSchool s = o2o.getOne(SooncodeSchool.class);
			SooncodeTeacher t = o2o.getOne(SooncodeTeacher.class);

			logger.info(s + " --- " + t);

		}
		logger.info("---------------------------------------------------------------");

	}
	*/
	/**
	 * 查询：老师id="4" 的老师，是哪个学校的校长。
	 */
	/*
	@Test
	public void getPage10() {

		SooncodeSchool school = new SooncodeSchool();
		// school.setSchoolId(1);
		SooncodeTeacher teacher = new SooncodeTeacher();
		teacher.setTeacherId(4);
		Conditions c = ConditionsBuilderProcess.getConditions(teacher, school);

		Page page = dbService.getPage(1L, 10L, c);
		List<One2One> o2os = page.getOne2One();

		logger.info("---------------------------------------------------------------");
		for (One2One o2o : o2os) {
			SooncodeSchool s = o2o.getOne(SooncodeSchool.class);
			SooncodeTeacher t = o2o.getOne(SooncodeTeacher.class);

			logger.info(t + " --- " + s);

		}
		logger.info("---------------------------------------------------------------");

	}
	*/
	/*
	@Test
	public void getPage11() {

		SooncodeClazz clazz = new SooncodeClazz();
		clazz.setClazzId("001");
		SooncodeTeaching teaching = new SooncodeTeaching();
		SooncodeTeacher teacher = new SooncodeTeacher();
		Conditions c = ConditionsBuilderProcess.getConditions(clazz, teaching, teacher);

		Page page = dbService.getPage(1L, 10L, c);
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
	*/
	/*
	@Test
	public void getOne2Manys__page() {

		SooncodeGroups sg = new SooncodeGroups();

		SooncodeDictionary sd = new SooncodeDictionary();

		Conditions c = ConditionsBuilderProcess.getConditions(sg, sd);
		c.setCondition("sooncodeGroups.groupName", LikeSign.LIKE, "类型");
		Page page = dbService.getPage(1, Long.MAX_VALUE, c);

		List<One2Many<SooncodeGroups, SooncodeDictionary>> list = page.getOne2Manys();

		for (One2Many<SooncodeGroups, SooncodeDictionary> o2m : list) {
			logger.info(o2m.getOne());
			logger.info(o2m.getMany());
		}

	}
	
	*/

}
