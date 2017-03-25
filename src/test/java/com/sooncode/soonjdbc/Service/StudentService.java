package com.sooncode.soonjdbc.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sooncode.soonjdbc.dao.JdbcDao;
import com.sooncode.soonjdbc.entity.Student;
@Service
public class StudentService implements StudentServiceI {
    @Autowired
	private JdbcDao jdbcDao;
    
    @Override
    public long saveStudent (Student s){
    	return jdbcDao.save(s);
    }
    
    @Override
    @Transactional
    public long updateStudetn (Student s){
    	
    	jdbcDao.delete(s);
    	return jdbcDao.update(s);
    }
    
}
