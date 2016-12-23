package com.sooncode.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 
import com.sooncode.example.dao.UserDao2;
import com.sooncode.example.entity.User;

@Service
public class UserService {
	@Autowired
	private UserDao2 userDao;
	
	@Transactional
	public boolean updateUser(User user){
		boolean bool = userDao.deleteUser(user);
		boolean b = userDao.updateUser(user);
		return b;
	}
	public boolean deleteUser(User user){
		return userDao.deleteUser(user);
	}
	
	
}
