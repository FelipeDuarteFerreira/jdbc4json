package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* STUDENT_TOTAL
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class StudentTotalDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "STUDENT_TOTAL";

    /***/
    public static Field total = new Field(TABLE_NAME,"TOTAL");
    /***/
    public static Field chinese = new Field(TABLE_NAME,"CHINESE");
    /***/
    public static Field id = new Field(TABLE_NAME,"ID");
    /***/
    public static Field math = new Field(TABLE_NAME,"MATH");
 
	public <T> StudentTotalDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public StudentTotalDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {id});
	}
	 
}
	 
