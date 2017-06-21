package com.sooncode.soonjdbc.sql.condition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
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
 * @author pc
 *
 */
public class Conditions {

	private DbBean leftBean;
	private DbBean[] otherBeans;
	private Map<String, Condition> ces;

	/**
	 * 排序的SQL片段
	 */
	private String oderByes = new String();
	private String groupBy = new String();

	public Conditions(Object leftJavaBean, Object... otherJavaBeans) {
		DbBean leftBean = new DbBean(leftJavaBean);
		DbBean[] dbBeans = new DbBean[otherJavaBeans.length];
		for (int i = 0; i < otherJavaBeans.length; i++) {
			dbBeans[i] = new DbBean(otherJavaBeans[i]);
		}
		this.init(leftBean, dbBeans);
	}

	/**
	 * 设置条件
	 * 
	 * @param key
	 *            字段
	 * 
	 * @param EqualSign
	 *            条件使用的符号
	 * 
	 * @param value
	 *            值
	 * 
	 * @return
	 */

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

	/**
	 * 设置Between条件
	 * 
	 * @param key
	 *            字段
	 * 
	 * @param start
	 *            下线值
	 * 
	 * @param end
	 *            上线值
	 * 
	 * @return
	 */
	public Conditions setCondition(String key, BetweenSign BetweenSign, Object start, Object end) {
		if (this.containsKey(key)) {
			Condition c = new BetweenCondition();
			c.setKey(key);
			c.setConditionSign(BetweenSign.toString());
			Object[] values = new Object[] { start, end };
			c.setValues(values);
			ces.put(new String(key), c);
		}
		return this;
	}

	public Conditions setCondition(String key, BetweenSign BetweenSign, Date startDate, Date endDate, DATE_FORMAT DATE_FORMAT) {
		return setCondition4BetweenDate(key, BetweenSign, startDate, endDate, DATE_FORMAT);
	}

	public Conditions setCondition(String key, BetweenSign BetweenSign, String startDate, String endDate, DATE_FORMAT DATE_FORMAT) {

		return setCondition4BetweenDate(key, BetweenSign, startDate, endDate, DATE_FORMAT);
	}

	/**
	 * 设置 NULL 条件
	 * 
	 * @param key
	 * @return
	 */
	public Conditions setCondition(String key, NullSign NullSign) {

		if (this.containsKey(key)) {
			Condition c = new NullCondition();
			c.setKey(key);
			c.setConditionSign(NullSign.toString());
			ces.put(new String(key), c);
		}
		return this;
	}

	/**
	 * 设置 IN 条件
	 * 
	 * @param key
	 * @return
	 */
	public Conditions setCondition(String key, InSign InSign, Object[] values) {

		if (this.containsKey(key)) {
			Condition c = new InCondition();
			c.setKey(key);
			c.setValues(values);
			c.setConditionSign(InSign.toString());
			ces.put(new String(key), c);
		}
		return this;
	}

	/**
	 * 设置排序
	 * 
	 * @param key
	 *            字段
	 * 
	 * 
	 * @param sort
	 *            排序规则：升序；降序。
	 * 
	 * 
	 * @return
	 */
	public Conditions setOderBy(String key, Sort Sort) {

		String[] keys = key.split(STRING.ESCAPE_POINT);
		String con = new String();
		if (keys.length == 2) {
			con = T2E.toColumn(keys[0]) + STRING.POINT + T2E.toColumn(keys[1]);
		} else {
			con = T2E.toColumn(keys[0]);
		}
		key = con;
		if (key != null && !key.equals("")) {
			String sql = new String();
			if (this.oderByes.equals("")) {
				sql = STRING.SPACING;
			} else {
				sql = SQL_KEY.COMMA;
			}
			this.oderByes = this.oderByes + sql + key.toUpperCase() + STRING.SPACING + Sort.name();
		}

		return this;

	}

	public Conditions setGroupBy(String field) {
		if (this.groupBy.equals("")) {
			this.groupBy = this.groupBy + SQL_KEY.GROUP_BY + T2E.toColumn(field) + STRING.SPACING;
		}
		return this;

	}

	/**
	 * 获取预编译SQL模型
	 * 
	 * @return
	 */
	public Parameter getWhereParameter() {

		Parameter p = new Parameter();
		Map<Integer, Object> para = new HashMap<>();
		String sql = new String();
		int index = 1;
		for (Entry<String, Condition> en : this.ces.entrySet()) {
			Condition c = en.getValue();
			boolean isNullCondition = c.getConditionSign().contains(" IS NULL ") || c.getConditionSign().contains(" IS NOT NULL ");
			if (c.getVal() == null && c.getValues() == null && isNullCondition == false) {
				continue;
			}
			SqlAndParameter sap = c.getSqlSlice();
			sql = sql + sap.getSqlSlice();
			List<Object> list = sap.getValues();
			if (list != null && list.size() != 0) {
				for (Object val : list) {
					para.put(index, val);
					index++;
				}
			}
			if (sap.getValue() != null) {
				para.put(index, sap.getValue());
				index++;
			}

		}
		if (!this.oderByes.equals("")) {
			sql = sql + SQL_KEY.ORDER_BY + this.oderByes;
		}

		if (!this.groupBy.equals("")) {
			sql = sql + this.groupBy;
		}
		p.setReadySql(sql);
		p.setParams(para);
		return p;
	}

	public DbBean getLeftBean() {
		return leftBean;
	}

	public DbBean[] getOtherBeans() {
		return otherBeans;
	}

	private void init(DbBean leftBean, DbBean... otherBeans) {
		this.leftBean = leftBean;
		this.otherBeans = otherBeans;
		Map<String, Object> map = leftBean.getFields();
		Map<String, Object> newMap = new TreeMap<>();

		for (Entry<String, Object> en : map.entrySet()) {
			String key = en.getKey();
			Object value = en.getValue();
			if (otherBeans != null && otherBeans.length > 0) {
				newMap.put(leftBean.getBeanName() + STRING.POINT + key, value);
			} else {
				newMap.put(key, value);
			}
		}

		if (otherBeans != null && otherBeans.length > 0) {
			for (DbBean bean : otherBeans) {
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
			c.setVal(val);
			c.setConditionSign(SQL_KEY.EQ);
			list.put(new String(key), c);
		}

		this.ces = list;
	}

	private boolean containsKey(String key) {
		for (Entry<String, Condition> en : this.ces.entrySet()) {
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
			c.setVal(value);
			ces.put(new String(c.getKey()), c);
		}
		return this;
	}

	private Conditions setConditionCom(String key, Sign Sign, Object value) {

		if (this.containsKey(key)) {
			Condition c = Sign.getCondition();
			c.setKey(key);
			if (value != null) {
				c.setVal(value);
			}
			c.setConditionSign(Sign.toString());
			this.ces.put(new String(key), c);
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
			c.setValues(values);
			c.setOther(DATE_FORMAT);
			ces.put(new String(key), c);
		}
		return this;
	}
}
