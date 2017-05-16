package com.sooncode.soonjdbc.sql.condition;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.sooncode.soonjdbc.bean.DbBean;
import com.sooncode.soonjdbc.constant.SQL_KEY;
import com.sooncode.soonjdbc.constant.STRING;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;
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

	
	
	private String keys;

	/**
	 * 排序的SQL片段
	 */
	private String oderByes = new String();

	public Conditions(Object leftJavaBean, Object... otherJavaBeans) {
		DbBean leftBean = new DbBean(leftJavaBean);
		DbBean[] dbBeans = new DbBean[otherJavaBeans.length];
		for (int i = 0; i < otherJavaBeans.length; i++) {
			dbBeans[i] = new DbBean(otherJavaBeans[i]);
		}
		this.init(leftBean, dbBeans);
	}
 
	public Conditions setCondition(String key, Sign sign, DateFormat4Sql dateFormat4Mysql) {

		if (this.containsKey(key)) {
			Condition c = new Condition();
			c.setKey(key);
			c.setConditionSign(sign + dateFormat4Mysql.getSql());
			c.setVal(dateFormat4Mysql.getParameter());
			ces.put(new String(c.getKey()), c);
		}
		return this;
	}

	/**
	 * 设置条件
	 * 
	 * @param key
	 *            字段
	 * @param sign
	 *            条件使用的符号
	 * @return
	 */
	public Conditions setCondition(String key1, String key2) {
		if (keys == null) {
			keys = key1 + SQL_KEY.EQ + key2;
		}
		return this;
	}

	/**
	 * 设置条件
	 * 
	 * @param key 字段 
	 *            
	 * @param sign 条件使用的符号
	 *            
	 * @param value 值
	 *             
	 * @return
	 */
	public Conditions setCondition(String key, Sign sign, Object value) {

		if (this.containsKey(key)) {
			Condition c = new Condition();
			c.setKey(key);
			if (value != null) {
				c.setVal(value);
			}
			c.setConditionSign(sign + new String());
			this.ces.put(new String(key), c);
		}
		return this;
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
	public Conditions setBetweenCondition(String key, Object start, Object end) {

		Condition c = ces.get(key);
		if (c != null) {
			c.setType("0");
			String sql = STRING.SPACING + T2E.toColumn(key) + SQL_KEY.BETWEEN + start + SQL_KEY.AND + end + STRING.SPACING;
			c.setCondition(sql);
			ces.put(c.getKey(), c);
		}
		return this;
	}

	/**
	 * 设置IS NULL 条件
	 * 
	 * @param key
	 * @return
	 */
	public Conditions setIsNullCondition(String key) {//createDate

		Condition c =  getCondition(key);
		if (c != null) {
			c.setType("0");
			String sql = STRING.SPACING + T2E.toColumn(key) + SQL_KEY.IS + SQL_KEY.NULL + STRING.SPACING; 
			c.setCondition(sql);
			//ces.put(c.getKey(), c);
		}
		return this;
	}

	/**
	 * 设置IS NULL 条件
	 * 
	 * @param key
	 * @return
	 */
	public Conditions setIsNotNullCondition(String key) {

		Condition c = getCondition(key);
		if (c != null) {
			c.setType("0");
			String sql = STRING.SPACING + T2E.toColumn(key) + SQL_KEY.IS + SQL_KEY.NOT + SQL_KEY.NULL + STRING.SPACING; 
			c.setCondition(sql);
			ces.put(c.getKey(), c);
		}
		return this;
	}

	/**
	 * 设置 IN 条件
	 * 
	 * @param key
	 * @return
	 */
	public Conditions setInCondition(String key, Object[] values) {

		Condition c = getCondition(key);
		if (c != null) {
			c.setType("1");
			c.setVales(values);
			c.setConditionSign("IN");
			ces.put(c.getKey(), c);
		}
		return this;
	}

	/**
	 * 设置排序
	 * 
	 * @param key  字段 
	 *           
	 * 
	 * @param sort 排序规则：升序；降序。
	 *            
	 * 
	 * @return
	 */
	public Conditions setOderBy(String key, Sort sort) {

		String[] keys = key.split(STRING.ESCAPE_POINT);
		String con = new String();
		if (keys.length == 2) {
			con = T2E.toColumn(keys[0]) + STRING.POINT + T2E.toColumn(keys[1]);
		} else {

			con = T2E.toColumn(keys[0]);
		}
		key = con;
		if (key == null || key.equals("")) {
			return this;
		} else {
			if (this.oderByes.equals("")) {
				this.oderByes = this.oderByes + STRING.SPACING + key.toUpperCase() + STRING.SPACING + sort.name();

			} else {
				this.oderByes = this.oderByes + SQL_KEY.COMMA + key.toUpperCase() + STRING.SPACING + sort.name();
			}
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
			String key = c.getKey();
			String[] keys = key.split(STRING.ESCAPE_POINT);
			String con = new String();
			if (keys.length == 2) {
				con = T2E.toColumn(keys[0]) + STRING.POINT + T2E.toColumn(keys[1]);
			} else {

				con = T2E.toColumn(keys[0]);
			}
			if (c.getType().equals("1")) {
				if (c.getVal() != null || c.getVales() != null) {
					String sign = c.getConditionSign();
					String newSign = sign;// Sign.Signmap.get(sign);
					newSign = newSign == null ? SQL_KEY.EQ : newSign; // 如果字段不为空，但是没有条件符号，默认使用等值查询"="。
					if (newSign.contains(SQL_KEY.LIKE)) {
						sql = sql + SQL_KEY.AND + con + STRING.SPACING + SQL_KEY.LIKE + STRING.SPACING + STRING.QUESTION;// "
						if (newSign.equals(SQL_KEY.LIKE)) {
							para.put(index, STRING.PERCENT + c.getVal() + STRING.PERCENT);
							index++;
						} else if (newSign.equals(LikeSign.L_LIKE.toString())) {
							para.put(index, c.getVal() + STRING.PERCENT);
							index++;
						} else {
							para.put(index, STRING.PERCENT + c.getVal());
							index++;
						}

					} else if (sign != null && sign.equals(SQL_KEY.IN)) {

						String vales = SQL_KEY.L_BRACKET;// "(";
						for (int i = 0; i < c.getVales().length; i++) {
							if (i != 0) {
								vales = vales + STRING.SPACING + STRING.COMMA + STRING.QUESTION + STRING.SPACING;
							} else {
								vales = vales + STRING.QUESTION + STRING.SPACING;
							}
							para.put(index, c.getVales()[i]);
							index++;
						}
						vales = vales + SQL_KEY.R_BRACKET + STRING.SPACING;// ")
						sql = sql + SQL_KEY.AND + con + STRING.SPACING + SQL_KEY.IN + vales;
					} else {
						if (newSign.contains("?")) {
							sql = sql + SQL_KEY.AND + con + STRING.SPACING + newSign + STRING.SPACING;// "?";
						} else {
							sql = sql + SQL_KEY.AND + con + STRING.SPACING + newSign + STRING.SPACING + STRING.QUESTION;// "?";
						}
						para.put(index, c.getVal());
						index++;
					}
				}
			} else {// 自定义
				sql = sql + SQL_KEY.AND + c.getCondition();
			}

		}
		if (!this.oderByes.equals("")) {
			sql = sql + SQL_KEY.ORDER_BY + this.oderByes;
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
			Condition c = new Condition(key, val, null);
			list.put(new String(key), c);
		}

		this.ces = list;
	}

	
	private boolean containsKey(String key){
		for (Entry<String, Condition> en : this.ces.entrySet()) {
			String k = en.getKey();
			if(k.equals(key)){
				return true;
			}
		}
		return false;
	}
	private Condition getCondition(String key){
		for (Entry<String, Condition> en : this.ces.entrySet()) {
			String k = en.getKey();
			if(k.equals(key)){
				return en.getValue();
			}
		}
		return null;
	}
	
	
}
