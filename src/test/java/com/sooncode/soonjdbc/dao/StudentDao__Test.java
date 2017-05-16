package com.sooncode.soonjdbc.dao;

 


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.entity.Student;
import com.sooncode.soontest.ManyTheadTest;
import com.sooncode.soontest.OpenInterfaceTest;
import com.sooncode.soontest.SoonTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class StudentDao__Test {
@Autowired
private StudentDao sd ;
private static Logger logger = Logger.getLogger("StudentDao__Test.class"); 	

@Test
public void getStudentByStudentId(){
	
	String studentId = "001";
	//Student s = sd.getStudent(studentId);
	//logger.info(s);
	
	
	    ManyTheadTest mtt = new ManyTheadTest(1,3);
	    SoonTest st = (SoonTest) OpenInterfaceTest.newInstance(mtt);
	    List< Object > list = st.testMethod( StudentDao.class,"getStudent",new String[]{"001"});
	    for ( Object  obj : list) {
		    System.out.println("结果:" + obj);
	    }
	
	
	
}
 
@Test
public void getStudentAndIdentity(){
	
	String studentId = "001";
	Student s = sd.getStudentAndIdentity(studentId);
	logger.info(s);
	
}
	
}
