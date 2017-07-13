package com.sooncode.soonjdbc.sql.condition;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;

public class ConditionsBuilder implements Builder{

	private DbBean leftBean;
	private DbBean[] otherBeans;
	private Map<String, Condition> conditionMap;
	
	@Override
	public void initLeftEntity(Object leftEntity) {
		this.leftBean = new DbBean(leftEntity);
	}

	@Override
	public void initOtherBeans(Object... otherEntities) {
		DbBean[] dbBeans = new DbBean[otherEntities.length];
		for (int i = 0; i < otherEntities.length; i++) {
			dbBeans[i] = new DbBean(otherEntities[i]);
		}
		this.otherBeans = dbBeans;
	}

	@Override
	public void initConditionMap() {
	 
		Map<String, Object> map = this.leftBean.getFields();
		Map<String, Object> newMap = new TreeMap<>();

		for (Entry<String, Object> en : map.entrySet()) {
			String key = en.getKey();
			Object value = en.getValue();
			if (this.otherBeans != null && this.otherBeans.length > 0) {
				newMap.put(this.leftBean.getBeanName() + STRING.POINT + key, value);
			} else {
				newMap.put(key, value);
			}
		}

		if (this.otherBeans != null && this.otherBeans.length > 0) {
			for (DbBean bean : this.otherBeans) {
				Map<String, Object> otherMap = bean.getFields();
				for (Entry<String, Object> en : otherMap.entrySet()) {
					String key = en.getKey();
					Object val = en.getValue();
					newMap.put(bean.getBeanName() + STRING.POINT + key, val);
				}
			}
		}

		Map<String, Condition> list = new IdentityHashMap<>();
		for (Entry<String, Object> en : newMap.entrySet()) {
			String key = en.getKey();
			Object val = en.getValue();
			Condition c = new EqualCondition();
			c.setKey(key);
			c.addValue(val);
			c.setConditionSign(SQL_KEY.EQ);
			list.put(new String(key), c);
		}

		this.conditionMap = list;
	 
		
	}

	@Override
	public Conditions getConditions() {
		Conditions conditions = new Conditions(this.leftBean, this.otherBeans, this.conditionMap);
		return conditions;
	}

	 
	 
}
