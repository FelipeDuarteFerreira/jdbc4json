package com.sooncode.soonjdbc.dao.tabletype;

public class TableRelation {
	public static final TableRelation  ONE = new TableRelation(OneTable.class);
	public static final TableRelation  ONE_ONE = new TableRelation(One2OneTable.class);
	public static final TableRelation  ONE_MANY = new TableRelation(One2ManyTable.class);
	public static final TableRelation  MANY_MANY = new TableRelation(Many2ManyTable.class);
	public static final TableRelation  ONE_MANY_MANY = new TableRelation(One2Many2ManyTable.class);
	
	public TableType getTableType(){
		return this.tableType;
	}
	
	
	
	private TableType tableType;
	private TableRelation(Class<?> clas){
		try {
			tableType = (TableType) clas.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
