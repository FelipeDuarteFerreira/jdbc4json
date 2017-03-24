package com.sooncode.soonjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sooncode.soonjdbc.entity.Student;
import com.sooncode.soonjdbc.service.JdbcService;

@Controller
public class StudentController {
    @Autowired
	private JdbcService js;
    
    public void addStudent( Student s ){
    	
    	js.save(s);
    	 
    }
    
    public static void main(String[] args) {
    	int i = 2<<3;
    	System.out.println(i);
	}
}
