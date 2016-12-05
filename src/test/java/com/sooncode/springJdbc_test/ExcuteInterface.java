package com.sooncode.springJdbc_test;

public interface ExcuteInterface {
	public <T> T exute(SqlInterface sqlInterface, ExcuteSqlInterface<T> excuteSql);
}
