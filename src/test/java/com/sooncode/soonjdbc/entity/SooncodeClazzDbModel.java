package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_CLAZZ
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeClazzDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_CLAZZ";

    /**班级编号*/
    public static Field clazzId = new Field(TABLE_NAME,"CLAZZ_ID");
    /**班级名称*/
    public static Field clazzName = new Field(TABLE_NAME,"CLAZZ_NAME");
    /***/
    public static Field studentNumber = new Field(TABLE_NAME,"STUDENT_NUMBER");
    /**学校编号*/
    public static Field schoolId = new Field(TABLE_NAME,"SCHOOL_ID");
    /**班主任编号*/
    public static Field mainTeacherId = new Field(TABLE_NAME,"MAIN_TEACHER_ID");
    /**班长编号*/
    public static Field monitorId = new Field(TABLE_NAME,"MONITOR_ID");
 
	public <T> SooncodeClazzDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeClazzDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {clazzId});
	}
	 
}
	 
