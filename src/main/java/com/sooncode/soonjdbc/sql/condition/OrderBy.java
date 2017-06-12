package com.sooncode.soonjdbc.sql.condition;

import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.constant.Sort;
import com.sooncode.soonjdbc.util.T2E;

/**
 * 排序模型
 * @author pc
 *
 */
public class OrderBy {

	private String orderBy;
	/**
	 * 创建排序模型
	 * @param key 属性名称 
	 * @param sort  DESC：降序 ；ASC：升序。
	 */
	public OrderBy(String key,Sort Sort){
		
		this.orderBy= T2E.toColumn(key)+STRING.SPACING + Sort.name();
	}	
	
	public String toString (){
		return orderBy;
	}
	
}
