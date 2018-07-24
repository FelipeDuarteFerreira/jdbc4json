package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_TEACHER
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeTeacherDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_TEACHER";

    /**老师编号*/
    public static Field teacherId = new Field(TABLE_NAME,"TEACHER_ID");
    /**老师年龄*/
    public static Field teacherAge = new Field(TABLE_NAME,"TEACHER_AGE");
    /**创建时间*/
    public static Field createDate = new Field(TABLE_NAME,"CREATE_DATE");
    /**老师名称*/
    public static Field teacherName = new Field(TABLE_NAME,"TEACHER_NAME");
    /***/
    public static Field address = new Field(TABLE_NAME,"ADDRESS");
    /***/
    public static Field sex = new Field(TABLE_NAME,"SEX");
    /***/
    public static Field xxx = new Field(TABLE_NAME,"XXX");
    /***/
    public static Field hight = new Field(TABLE_NAME,"HIGHT");
 
	public <T> SooncodeTeacherDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeTeacherDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {teacherId});
	}
	 
}
	 
