package com.sooncode.soonjdbc.dao;

 


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.entity.SooncodeStudent;
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
public void getStudentAndIdentity(){
	
	 
	  sd.addQuestion("chinese_question","1","21","新课标人教版");
	 
	
}
	
}
