package com.sooncode.soonjdbc.sql.condition;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.constant.DATE_FORMAT;
import com.sooncode.soonjdbc.constant.DateFormat;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.constant.Sort;
import com.sooncode.soonjdbc.exception.PropertyInexistenceException;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.condition.sign.BetweenSign;
import com.sooncode.soonjdbc.sql.condition.sign.EqualSign;
import com.sooncode.soonjdbc.sql.condition.sign.InSign;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;
import com.sooncode.soonjdbc.sql.condition.sign.NullSign;
import com.sooncode.soonjdbc.sql.condition.sign.Sign;
import com.sooncode.soonjdbc.table.DbBean;
import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;
import com.sooncode.soonjdbc.util.T2E;

/**
 * 查询条件构造器
 * 
 * @author hechenwe@gmail.com
 *
 */
public class AdvancedConditions {
	
	private DbModel mainDbModel;
	private DbModel[] otherDbModels;
	private DbBean leftBean;
	private DbBean[] otherBeans;
	private Map<String, Condition> conditionMap;

	private List<OrderByCondition> orderByConditions = new LinkedList<OrderByCondition>();
	private List<NullCondition> nullConditions = new LinkedList<NullCondition>();
	private List<GroupByCondition> groupByConditions = new LinkedList<GroupByCondition>();
 
	
	public AdvancedConditions(DbModel mainDbModel, DbModel... otherDbModels){
		 this.mainDbModel = mainDbModel;
		 this.otherDbModels = otherDbModels;
		 initMainDbBean(mainDbModel);
		 initOtherBeans(otherDbModels);
		 initConditionMap();
		  
	}

	 

	public AdvancedConditions setCondition(Field<?> key, EqualSign EqualSign, Object value) {
		return setConditionCom(key, EqualSign, value);
	}

	public AdvancedConditions setCondition(String key, EqualSign EqualSign, Date value, DATE_FORMAT DATE_FORMAT) {
		return this.setCondition4DateFormat(key, EqualSign, value, DATE_FORMAT);
	}

	public AdvancedConditions setCondition(String key, EqualSign EqualSign, String dateFormatvalue, DATE_FORMAT DATE_FORMAT) {
		return this.setCondition4DateFormat(key, EqualSign, dateFormatvalue, DATE_FORMAT);
	}

	public AdvancedConditions setCondition(Field<?> key, LikeSign LikeSign, Object value) {
		return setConditionCom(key, LikeSign, value);
	}

	 
	public AdvancedConditions setCondition(String key, BetweenSign BetweenSign, Object start, Object end) {
		if (this.containsKey(key)) {
			Condition condition = new BetweenCondition();
			condition.setKey(key);
			condition.setConditionSign(BetweenSign.toString());
			condition.addValue(start);
			condition.addValue(end);
			conditionMap.put(new String(key), condition);
		}
		return this;
	}

	public AdvancedConditions setCondition(String key, BetweenSign BetweenSign, Date startDate, Date endDate, DATE_FORMAT DATE_FORMAT) {
		return setCondition4BetweenDate(key, BetweenSign, startDate, endDate, DATE_FORMAT);
	}

	public AdvancedConditions setCondition(String key, BetweenSign BetweenSign, String startDate, String endDate, DATE_FORMAT DATE_FORMAT) {

		return setCondition4BetweenDate(key, BetweenSign, startDate, endDate, DATE_FORMAT);
	}

	 
	public AdvancedConditions setCondition(Field<?> key, NullSign NullSign) {
        String k = key.getPropertyName();
		if (this.containsKey(k)) {
			NullCondition c = new NullCondition();
			c.setKey(k);
			c.setConditionSign(NullSign.toString());
			nullConditions.add(c);
		}
		return this;
	}

	 
	public AdvancedConditions setCondition(Field<?> key, InSign InSign, Object[] values) {
        String k = key.getPropertyName();
		if (this.containsKey(k)) {
			Condition c = new InCondition();
			c.setKey(k);
			c.addValues(Arrays.asList(values));
			c.setConditionSign(InSign.toString());
			conditionMap.put(new String(k), c);
		}
		return this;
	}

	 
	public AdvancedConditions setOderBy(Field<?> key, Sort Sort) {
		
		String k = key.getPropertyName();
		if (this.containsKey(k)) {
			String[] keys = k.split(STRING.ESCAPE_POINT);
			if(keys.length == 2) {
				k = T2E.toTableName(keys[0]+STRING.POINT + T2E.toColumn(keys[1]));
			}else {
				k =  T2E.toColumn(k);
			}
			OrderByCondition orderByCondition = new OrderByCondition();
			orderByCondition.setKey(k);
			orderByCondition.setSortKey(Sort.name());
			this.orderByConditions.add(orderByCondition);
		}
		return this;

	}

	public AdvancedConditions setGroupBy(Field<?> key) {
	    String k = key.getPropertyName();
		if (this.containsKey(k)) {
			String[] keys = k.split(STRING.ESCAPE_POINT);
			if(keys.length == 2) {
				k = T2E.toTableName(keys[0]+STRING.POINT + T2E.toColumn(keys[1]));
			}else {
				k =  T2E.toColumn(k);
			}
			GroupByCondition groupByCondition = new GroupByCondition();
			groupByCondition.setKey(k);
			this.groupByConditions.add(groupByCondition);
			 
		}
		return this;
		
	}

	 
	public Parameter getWhereParameter() {

		Parameter p = new Parameter();

		String sql = new String();

		for (Entry<String, Condition> en : this.conditionMap.entrySet()) {
			Condition c = en.getValue();
			if (c.getValues().size() == 0) {
				continue;
			}
			SqlAndParameter sap = c.getSqlSlice();
			sql = sql + sap.getSqlSlice();
			p.addParameters(sap.getValues());

		}

		for (NullCondition nullCondition : nullConditions) {
			sql = sql + nullCondition.getSqlSlice().getSqlSlice();
		}

		sql = sql + this.getGroupBySql();
		sql = sql + this.getOrderBySql();
		p.setReadySql(sql);

		return p;
	}

	private String getOrderBySql() {
		String sql = new String();
		if (this.orderByConditions.size() == 0) {
			return sql;
		}
		 
		sql = sql + SQL_KEY.ORDER_BY;
		for (OrderByCondition orderByCondition : orderByConditions) {
			sql = sql + orderByCondition.getKey() + STRING.SPACING + orderByCondition.getSortKey() + SQL_KEY.COMMA;
		}
		int endIndex = sql.lastIndexOf(SQL_KEY.COMMA);
		sql = sql.substring(0, endIndex);
		return sql;
	}
	private String getGroupBySql() {
		String sql = new String();
		if (this.groupByConditions.size() == 0) {
			return sql;
		}
		
		sql = sql + SQL_KEY.GROUP_BY;
		for (GroupByCondition groupByCondition : this.groupByConditions) {
			sql = sql + groupByCondition.getKey() + SQL_KEY.COMMA;
		}
		int endIndex = sql.lastIndexOf(SQL_KEY.COMMA);
		sql = sql.substring(0, endIndex);
		return sql;
	}

	public DbBean getLeftBean() {
		return leftBean;
	}

	public DbBean[] getOtherBeans() {
		return otherBeans;
	}

	
	 

	public DbModel getMainDbModel() {
		return mainDbModel;
	}



	public DbModel[] getOtherDbModels() {
		return otherDbModels;
	}



	private boolean containsKey(String key) {
		for (Entry<String, Condition> en : this.conditionMap.entrySet()) {
			String k = en.getKey();
			if (k.toUpperCase().equals(key.toUpperCase())) {
				return true;
			}
		} 
		
		try {
			throw new PropertyInexistenceException("This property ["+key+"] in POJO class is not exist !" );
		} catch (PropertyInexistenceException e) {
			e.printStackTrace();
		}
		return false;
	}

	private AdvancedConditions setCondition4DateFormat(String key, Sign EqualSign, Object value, DATE_FORMAT DATE_FORMAT) {
		if (this.containsKey(key)) {
			Condition c = new DateFormatCondition();
			c.setOther(DATE_FORMAT);
			c.setKey(key);
			c.setConditionSign(EqualSign.toString());
			if (value instanceof Date) {
				value = new SimpleDateFormat(DateFormat.ALL_DATE).format(value);
			}
			c.addValue(value);
			conditionMap.put(new String(c.getKey()), c);
		}
		return this;
	}

	private AdvancedConditions setConditionCom(Field<?> key, Sign Sign, Object value) {
        String k = key.getPropertyName();
		if (this.containsKey(k)) {
			Condition c = Sign.getCondition();
			c.setKey(k);
			if (value != null) {
				c.addValue(value);
			}
			c.setConditionSign(Sign.toString());
			this.conditionMap.put(new String(k), c);
		}
		return this;
	}

	private AdvancedConditions setCondition4BetweenDate(String key, BetweenSign BetweenSign, Object startDate, Object endDate, DATE_FORMAT DATE_FORMAT) {
		if (this.containsKey(key)) {
			Condition c = new BetweenCondition();
			c.setKey(key);
			c.setConditionSign(BetweenSign.toString());
			Object[] values;
			if (startDate instanceof String && endDate instanceof String) {
				values = new Object[] { startDate, endDate };
			} else {
				String start = new SimpleDateFormat(DateFormat.ALL_DATE).format(startDate);
				String end = new SimpleDateFormat(DateFormat.ALL_DATE).format(endDate);
				values = new Object[] { start, end };
			}
			c.addValues(Arrays.asList(values));
			c.setOther(DATE_FORMAT);
			conditionMap.put(new String(key), c);
		}
		return this;
	}
	
	
	 
	private void initConditionMap() {
	 
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
	
	 
	private void initMainDbBean(DbModel mainDbModel) {
		this.leftBean = new DbBean(mainDbModel);
	}

	 
	private void initOtherBeans(DbModel... otherDbModels) {
		DbBean[] dbBeans = new DbBean[otherDbModels.length];
		for (int i = 0; i < otherDbModels.length; i++) {
			dbBeans[i] = new DbBean(otherDbModels[i]);
		}
		this.otherBeans = dbBeans;
	}
}
  
 

