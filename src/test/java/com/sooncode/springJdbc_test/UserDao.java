package com.sooncode.springJdbc_test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.sooncode.example.entity.User;

public class UserDao {
    
    private JdbcTemplate jdbcT = new JdbcTemplate(new DriverManagerDataSource("jdbc:mysql://127.0.0.1:3306/jdbc?",   "root",   "hechenwe@gmail.com"));
    
    public void setJdbcT(JdbcTemplate jdbcT) {
        this.jdbcT = jdbcT;
    }

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
    public int delete(int id){
        String sql = "delete from user where id =?";
        return jdbcT.update(sql, new Object[]{id});
    }
    
    public int update(int id ,String name){
    	String sql = "update  user  set name=? where id =?";
    	return jdbcT.update(sql, new Object[]{name,id});
    }     
    
    public static void main(String[] args) {      
    	UserDao dao = new UserDao();
        List<User> depts = dao.findALL();;
        for(User dept:depts){
            System.out.println(dept.getId() +","+dept.getName());
        }
        System.out.println("---------------------------------"); 
        
        /*List list = dao.findALL();
        for(Iterator it = list.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }*/
    	
    	//int n = dao.delete(1);
    	//System.out.println("UserDao.main()"+n);
    	int m = dao.update(7,"AAA");
    	System.out.println("UserDao.main()"+m);
    }
}