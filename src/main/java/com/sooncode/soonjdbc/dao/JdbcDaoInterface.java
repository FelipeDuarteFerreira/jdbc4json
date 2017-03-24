package com.sooncode.soonjdbc.dao;
import java.util.List;

import com.sooncode.soonjdbc.bean.JsonBean;
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
    public <T> T get(JsonBean jsonBean,Class<T> javaBeanClass);
    
    public List<JsonBean> gets(Conditions conditions)  ;
    public <T> List<T> gets(Conditions conditions,Class<T> javaBeanClass)  ;
    public <T> List<T> gets(JsonBean jsonBean,Class<T> javaBeanClass)  ;
    public <T> List<T> gets(T javaBean)  ;
    
	public long save(JsonBean jsonBean)  ;
	public <T> long save(T javaBean)  ;
	public boolean saves(List<JsonBean> jsonBeans) ;
	public <T> boolean saves(T[] javaBeans) ;
	
	public boolean updates(List<JsonBean> jsonBeans)  ;
	public long saveOrUpdate(JsonBean jsonBean)  ;
	public <T> long saveOrUpdate(T javaBean)  ;

	public long update(JsonBean jsonBean)  ;
	public <T> long update(T javaBean)  ;

	public long delete(JsonBean jsonBean)  ;
	public <T> long delete(T javaBean)  ;

	 
	public long count(String key, Conditions conditions) ;
	public long count(String key, JsonBean jsonBean) ;
	public <T> long count(String key, T javaBean) ;
	 
}