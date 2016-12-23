package com.sooncode.springJdbc_test;

public class Excute implements ExcuteInterface {

	@Override
	public <T> T exute(SqlInterface sqlInterface, ExcuteSqlInterface<T> excuteSql) {
		return excuteSql.excuet(sqlInterface);
	}

}
