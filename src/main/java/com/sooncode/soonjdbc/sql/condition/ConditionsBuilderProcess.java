package com.sooncode.soonjdbc.sql.condition;


public class ConditionsBuilderProcess {

	 public static Conditions getConditions(Object leftEntity, Object... otherEntities) {
		 ConditionsBuilder cb = new ConditionsBuilder();
		 cb.initLeftEntity(leftEntity);
		 cb.initOtherBeans(otherEntities);
		 cb.initConditionMap();
		 return cb.getConditions();
	 }
}
