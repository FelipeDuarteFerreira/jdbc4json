package com.sooncode.springJdbc_test;

public class Jdbc implements JdbcInterface {

	public int update(String sql) {
		Sql s = new Sql(sql);
		Excute ex = new Excute();

		return ex.exute(s, new ExcuteSqlInterface<Integer>() {

			@Override
			public Integer excuet(SqlInterface sqlInterface) {
				String sqlString = sqlInterface.getSql();

				return sqlString.length();
			}
		});
	}

	public static void main(String[] args) {
		int n = new Jdbc().update("sdfjldk");
		System.out.println("Jdbc.main()" + n);
	}

}
