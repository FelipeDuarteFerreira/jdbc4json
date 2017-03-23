package com.sooncode.jdbc4json.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sooncode.jdbc4json.entity.Student;
import com.sooncode.jdbc4json.service.JdbcService;

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
