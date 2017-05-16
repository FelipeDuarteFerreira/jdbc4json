package com.sooncode.soonjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sooncode.soonjdbc.Service.StudentServiceI;
import com.sooncode.soonjdbc.entity.SooncodeStudent;
import com.sooncode.soonjdbc.entity.SooncodeStudent;
 

@Controller
public class StudentController {
	
    @Autowired
	private StudentServiceI js;
    
    public void addStudent( SooncodeStudent s ){
    	
    	js.saveStudent(s);
    	
    }
    
}
