package com.sooncode.example.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sooncode.example.entity.User;
import com.sooncode.jdbc4json.Jdbc;
import com.sooncode.jdbc4json.JdbcFactory;
import com.sooncode.jdbc4json.sql.Parameter;
@Transactional
@Repository
public class UserDao {
	private Jdbc jdbc = JdbcFactory.getJdbc();
	@Transactional
	public boolean updateUser(User user){
		Integer id = user.getId();
		Parameter p = new Parameter();
		String sql ="UPDATE  USER  SET   NAME = 'LSDKJFLSDSL'  WHERE ID = '"+id+"'";
		p.setReadySql(sql);
		long n = jdbc.update(p);
		if(n == 1){
			return true;
		}else{
			return false;
		}
	}
	@Transactional
	public boolean deleteUser(User user){
		Integer id = user.getId();
		Parameter p = new Parameter();
		String sql ="DELETE FROM  USER WHERE ID = '"+id+"'";
		p.setReadySql(sql);
		long n = jdbc.update(p);
		if(n == 1){
			return true;
		}else{
			return false;
		}
	}
}
