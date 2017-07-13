package com.sooncode.soonjdbc.sql.condition;

 

public interface Builder {
     
	public void initLeftEntity(Object leftEntity);
	public void initOtherBeans(Object ... otherEntities);
	public void initConditionMap();
	public Conditions getConditions();
}
