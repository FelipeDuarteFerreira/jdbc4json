package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_STUDENT
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeStudentDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_STUDENT";

    /***/
    public static Field identityId = new Field(TABLE_NAME,"IDENTITY_ID");
    /***/
    public static Field clazzId = new Field(TABLE_NAME,"CLAZZ_ID");
    /**性别*/
    public static Field sex = new Field(TABLE_NAME,"SEX");
    /**学生编号*/
    public static Field studentId = new Field(TABLE_NAME,"STUDENT_ID");
    /**姓名*/
    public static Field name = new Field(TABLE_NAME,"NAME");
    /**年龄*/
    public static Field age = new Field(TABLE_NAME,"AGE");
 
	public <T> SooncodeStudentDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeStudentDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {studentId});
	}
	 
}
	 
