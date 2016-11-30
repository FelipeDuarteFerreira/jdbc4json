package com.sooncode.springJdbc_test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.sooncode.example.entity.User;

public class UserDao {
    
    private JdbcTemplate jdbcT = new JdbcTemplate(new DriverManagerDataSource("jdbc:mysql://127.0.0.1:3306/jdbc?",   "jdbc_test",   "000000"));
    
    public void setJdbcT(JdbcTemplate jdbcT) {
        this.jdbcT = jdbcT;
    }

    public  List  findALL() {
        String sql = "select * from user";
        return jdbcT.queryForList(sql);
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
    public int delete(int bid){
        String sql = "delete from DeptInfo where bid =?";
        return jdbcT.update(sql, new Object[]{bid});
    }     
    
    public static void main(String[] args) {      
    	UserDao dao = new UserDao();
        /*List<User> depts = dao.findALL();;
        for(User dept:depts){
            System.out.println(dept.getId() +","+dept.getName());
        }
        System.out.println("---------------------------------");*/
        
        List list = dao.findALL();
        for(Iterator it = list.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }
    }
}