package com.sooncode.jdbc4json.dao;
import java.util.List;
import com.sooncode.jdbc4json.bean.JsonBean;
import com.sooncode.jdbc4json.sql.condition.Conditions;
import com.sooncode.jdbc4json.util.Page;

/**
 * Jdbc Dao 服务接口
 * 
 * @author pc
 * 
 */
public interface JdbcDaoInterface {
 

	public Page getPage(long pageNum, long pageSize, Conditions conditions);

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

	public List<JsonBean> gets(Conditions conditions)  ;
	//public <T> List<T> gets(Conditions conditions)  ;
	public long count(String key, Conditions conditions) ;
	 
}