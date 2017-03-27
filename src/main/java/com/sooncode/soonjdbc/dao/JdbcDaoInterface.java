package com.sooncode.soonjdbc.dao;
import java.util.List;

import com.sooncode.soonjdbc.page.Page;
import com.sooncode.soonjdbc.sql.condition.Conditions;

/**
 * Jdbc Dao 服务接口
 * 
 * @author pc
 * 
 */
public interface JdbcDaoInterface {
 

	public Page getPage(long pageNum, long pageSize, Conditions conditions);
	
    public <T> T get(Conditions conditions,Class<T> javaBeanClass);
    public <T> T get(T javaBean);
   
    
    
    public <T> List<T> gets(Conditions conditions,Class<T> javaBeanClass)  ;
   
    public <T> List<T> gets(T javaBean)  ;
    
	public <T> long save(T javaBean)  ;
	public <T> boolean saves(T[] javaBeans) ;
	
	public <T> long saveOrUpdate(T javaBean)  ;

	public <T> long update(T javaBean)  ;

	public <T> long delete(T javaBean)  ;

	 
	public long count(String key, Conditions conditions) ;
	public <T> long count(String key, T javaBean) ;
	 
}