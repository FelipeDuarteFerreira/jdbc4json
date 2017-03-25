package com.sooncode.soonjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sooncode.soonjdbc.Service.StudentServiceI;
import com.sooncode.soonjdbc.entity.Student;
 

@Controller
public class StudentController {
	
    @Autowired
	private StudentServiceI js;
    
    public void addStudent( Student s ){
    	
    	js.saveStudent(s);
    	
    }
    
}
