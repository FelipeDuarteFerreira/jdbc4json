package com.sooncode.soonjdbc.sql.condition;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.DATE_FORMAT;
import com.sooncode.soonjdbc.constant.DateFormat;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.constant.Sort;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.condition.sign.BetweenSign;
import com.sooncode.soonjdbc.sql.condition.sign.EqualSign;
import com.sooncode.soonjdbc.sql.condition.sign.InSign;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;
import com.sooncode.soonjdbc.sql.condition.sign.NullSign;
import com.sooncode.soonjdbc.sql.condition.sign.Sign;
import com.sooncode.soonjdbc.util.T2E;

/**
 * 查询条件构造器
 * 
 * @author hechenwe@gmail.com
 *
 */
public class Conditions {

	private DbBean leftBean;
	private DbBean[] otherBeans;
	private Map<String, Condition> conditionMap;

	private List<OrderByCondition> orderByConditions = new LinkedList<OrderByCondition>();
	private List<NullCondition> nullConditions = new LinkedList<NullCondition>();
	private List<GroupByCondition> groupByConditions = new LinkedList<GroupByCondition>();

	 
	Conditions(DbBean leftBean ,DbBean[] otherBeans,Map<String, Condition> conditionMap) {
		 this.leftBean = leftBean;
		 this.otherBeans = otherBeans;
		 this.conditionMap = conditionMap;
	}

	 

	public Conditions setCondition(String key, EqualSign EqualSign, Object value) {
		return setConditionCom(key, EqualSign, value);
	}

	public Conditions setCondition(String key, EqualSign EqualSign, Date value, DATE_FORMAT DATE_FORMAT) {
		return this.setCondition4DateFormat(key, EqualSign, value, DATE_FORMAT);
	}

	public Conditions setCondition(String key, EqualSign EqualSign, String dateFormatvalue, DATE_FORMAT DATE_FORMAT) {
		return this.setCondition4DateFormat(key, EqualSign, dateFormatvalue, DATE_FORMAT);
	}

	public Conditions setCondition(String key, LikeSign LikeSign, Object value) {
		return setConditionCom(key, LikeSign, value);
	}

	 
	public Conditions setCondition(String key, BetweenSign BetweenSign, Object start, Object end) {
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

	public Conditions setCondition(String key, BetweenSign BetweenSign, Date startDate, Date endDate, DATE_FORMAT DATE_FORMAT) {
		return setCondition4BetweenDate(key, BetweenSign, startDate, endDate, DATE_FORMAT);
	}

	public Conditions setCondition(String key, BetweenSign BetweenSign, String startDate, String endDate, DATE_FORMAT DATE_FORMAT) {

		return setCondition4BetweenDate(key, BetweenSign, startDate, endDate, DATE_FORMAT);
	}

	 
	public Conditions setCondition(String key, NullSign NullSign) {

		if (this.containsKey(key)) {
			NullCondition c = new NullCondition();
			c.setKey(key);
			c.setConditionSign(NullSign.toString());
			nullConditions.add(c);
		}
		return this;
	}

	 
	public Conditions setCondition(String key, InSign InSign, Object[] values) {

		if (this.containsKey(key)) {
			Condition c = new InCondition();
			c.setKey(key);
			c.addValues(Arrays.asList(values));
			c.setConditionSign(InSign.toString());
			conditionMap.put(new String(key), c);
		}
		return this;
	}

	 
	public Conditions setOderBy(String key, Sort Sort) {
		if (this.containsKey(key)) {
			String[] keys = key.split(STRING.ESCAPE_POINT);
			if(keys.length == 2) {
				key = T2E.toTableName(keys[0]+STRING.POINT + T2E.toColumn(keys[1]));
			}else {
				key =  T2E.toColumn(key);
			}
			OrderByCondition orderByCondition = new OrderByCondition();
			orderByCondition.setKey(key);
			orderByCondition.setSortKey(Sort.name());
			this.orderByConditions.add(orderByCondition);
		}
		return this;

	}

	public Conditions setGroupBy(String key) {
	 
		if (this.containsKey(key)) {
			String[] keys = key.split(STRING.ESCAPE_POINT);
			if(keys.length == 2) {
				key = T2E.toTableName(keys[0]+STRING.POINT + T2E.toColumn(keys[1]));
			}else {
				key =  T2E.toColumn(key);
			}
			GroupByCondition groupByCondition = new GroupByCondition();
			groupByCondition.setKey(key);
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

		sql = sql + this.getOrderBySql();
		sql = sql + this.getGroupBySql();
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

	 

	private boolean containsKey(String key) {
		for (Entry<String, Condition> en : this.conditionMap.entrySet()) {
			String k = en.getKey();
			if (k.toUpperCase().equals(key.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	private Conditions setCondition4DateFormat(String key, Sign EqualSign, Object value, DATE_FORMAT DATE_FORMAT) {
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

	private Conditions setConditionCom(String key, Sign Sign, Object value) {

		if (this.containsKey(key)) {
			Condition c = Sign.getCondition();
			c.setKey(key);
			if (value != null) {
				c.addValue(value);
			}
			c.setConditionSign(Sign.toString());
			this.conditionMap.put(new String(key), c);
		}
		return this;
	}

	private Conditions setCondition4BetweenDate(String key, BetweenSign BetweenSign, Object startDate, Object endDate, DATE_FORMAT DATE_FORMAT) {
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
}
