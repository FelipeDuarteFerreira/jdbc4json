package com.sooncode.jdbc4json.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sooncode.jdbc4json.dao.JdbcDao;
import com.sooncode.jdbc4json.entity.Student;
@Service
public class StudentService {
    @Autowired
	private JdbcDao jdbcDao;
    
    public long saveStudent (Student s){
    	return jdbcDao.save(s);
    }
    
    
    @Transactional
    public long updateStudetn (Student s){
    	
    	jdbcDao.delete(s);
    	return jdbcDao.update(s);
    }
    
}
