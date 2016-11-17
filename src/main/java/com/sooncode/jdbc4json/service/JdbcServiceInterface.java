package com.sooncode.jdbc4json.service;
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
public interface JdbcServiceInterface {
 

	public Page getPage(long pageNum, long pageSize, Conditions conditions);

	public long save(JsonBean jsonBean)  ;

	public boolean saves(List<JsonBean> jsonBeans) ;

	public boolean updates(List<JsonBean> jsonBeans)  ;

	public long saveOrUpdate(JsonBean jsonBean)  ;

	public long update(JsonBean jsonBean)  ;

	public long delete(JsonBean jsonBean)  ;

	public List<JsonBean> gets(Conditions conditions)  ;
 
	 
}