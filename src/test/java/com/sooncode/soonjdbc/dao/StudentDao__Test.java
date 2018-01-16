package com.sooncode.soonjdbc.dao;

 


import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class StudentDao__Test {
@Autowired
private StudentDao sd ;
private static Logger logger = Logger.getLogger("StudentDao__Test.class"); 	
 
@Test
public void creadTabelTest(){
	
	 
	  sd.creadTabel();
	 
	
}
	
}
