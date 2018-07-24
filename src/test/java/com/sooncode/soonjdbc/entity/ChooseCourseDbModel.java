package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* CHOOSE_COURSE
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class ChooseCourseDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "CHOOSE_COURSE";

    /**选课编号*/
    public static Field chooseId = new Field(TABLE_NAME,"CHOOSE_ID");
    /**课程编号*/
    public static Field courseId = new Field(TABLE_NAME,"COURSE_ID");
    /**等级*/
    public static Field grade = new Field(TABLE_NAME,"GRADE");
    /***/
    public static Field score = new Field(TABLE_NAME,"SCORE");
    /**学生编号*/
    public static Field studentId = new Field(TABLE_NAME,"STUDENT_ID");
 
	public <T> ChooseCourseDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public ChooseCourseDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {chooseId});
	}
	 
}
	 
