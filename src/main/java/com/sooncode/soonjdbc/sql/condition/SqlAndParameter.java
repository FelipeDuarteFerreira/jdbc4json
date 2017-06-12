package com.sooncode.soonjdbc.sql.condition;

import java.util.List;

public class SqlAndParameter {
private String SqlSlice;
private Object value;
private List<Object> values;
public String getSqlSlice() {
	return SqlSlice;
}
public void setSqlSlice(String sqlSlice) {
	SqlSlice = sqlSlice;
}
public Object getValue() {
	return value;
}
public void setValue(Object value) {
	this.value = value;
}
public List<Object> getValues() {
	return values;
}
public void setValues(List<Object> values) {
	this.values = values;
}


}
