package com.sooncode.soonjdbc.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.entity.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class StudentService__Test {
    @Autowired
	private StudentService ss ;
    
    @Test
    public void save (){
    	Student s = new Student();
    	s.setStudentId("3749238029");
    	s.setName("何臣");
    	ss.saveStudent(s);
    }
    
    
    @Test
    public void updateStudetn (){
    	Student s = new Student();
    	s.setStudentId("3749238029");
    	ss.updateStudetn(s) ;
    }
}
