package com.sooncode.jdbc4json.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.jdbc4json.entity.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class StudentController__Test {
    @Autowired
	private StudentController sc;
    @Test
    public void addStudent(   ){
    	Student s = new Student();
    	s.setName("sdflkdjflskd");
    	s.setStudentId("88998999888");
    	sc.addStudent(s); 
    	
    }
}
