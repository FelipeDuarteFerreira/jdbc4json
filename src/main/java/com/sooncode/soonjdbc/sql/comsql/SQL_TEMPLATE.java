package com.sooncode.soonjdbc.sql.comsql;

public class SQL_TEMPLATE {

	public static final String INSERT_SQL = "INSERT INTO [TABLE] ([COLUMNS]) VALUES ([PARAMETERS])";
	public static final String UPDATE_SQL = "UPDATE [TABLE] SET [SET_PARAMETERS] WHERE [CONDITION]";
	public static final String DELETE_SQL = "DELETE FROM [TABLE] WHERE [CONDITION]";
	public static final String SELECT_SQL = "SELECT [COLUMNS] FROM [TABLE] WHERE [CONDITION]";

}
