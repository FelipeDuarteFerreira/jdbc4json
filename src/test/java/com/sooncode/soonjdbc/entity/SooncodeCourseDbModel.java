package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_COURSE
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeCourseDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_COURSE";

    /**课程编号*/
    public static Field courseId = new Field(TABLE_NAME,"COURSE_ID");
    /***/
    public static Field score = new Field(TABLE_NAME,"SCORE");
    /**课程名称*/
    public static Field courseName = new Field(TABLE_NAME,"COURSE_NAME");
 
	public <T> SooncodeCourseDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeCourseDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {courseId});
	}
	 
}
	 
