package com.sooncode.springJdbc_test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sooncode.example.entity.User;

@Repository

public class UserDao {
    
    @Autowired
    private JdbcTemplate jdbcT ;
    
    

    public  List<User>  findALL() {
        String sql = "select * from user";
        return jdbcT.queryForList(sql,User.class);
    }
    
    public List<User> findALLDepts() {
        List<User> depts = new ArrayList<User>();;
        String sql = "select * from Dept";
        List list = jdbcT.queryForList(sql); 
        Iterator iterator = list.iterator();
        User dept = null;
        while (iterator.hasNext()) {
            Map map4dept = (Map) iterator.next();
            dept = new User();       
                   
            depts.add(dept);
        }
        return depts;
    }    
    @Transactional(propagation=Propagation.REQUIRED)
    public int delete(int id){
        String sql = "delete from user where id =?";
         int n=  jdbcT.update(sql, new Object[]{id});
        /*String string  = null;
        if(string.equals("")) {
            int i = 0;
        }*/
        return  n;
    }
    @Transactional (propagation=Propagation.REQUIRED)
    public int update(int id ,String name){
    	String sql = "update  user  set name=? where id =?";
    	int n = jdbcT.update(sql, new Object[]{name,id});
    	return n;
    }     
    /**
     * 扣除余额
     * @param id
     * @param balance
     * @return
     * @throws BalanceInsufficientEx 
     */
    @Transactional (propagation=Propagation.REQUIRED)
    public boolean subtractBalance(int id ,int balance)   {
    	
    	 
    	String sql = "update  user  set balance = balance-? where id =?";
    	int n = jdbcT.update(sql, new Object[]{balance,id});
    	
    	String select = "select * from user where id = '"+id+"'";
    	List<Map<String, Object>> list = jdbcT.queryForList(select); 
        Map<String,Object> map = list.get(0); 
    	int bal = (int) map.get("balance");
    	if(bal<0){
    		throw new BalanceInsufficientEx("余额不足");
    	}
    	if(n==1){
    		return true;
    	}
    	return false;
    }     
    
}