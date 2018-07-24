package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_TEACHING
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeTeachingDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_TEACHING";

    /***/
    public static Field teacherId = new Field(TABLE_NAME,"TEACHER_ID");
    /**授课编号*/
    public static Field teachingId = new Field(TABLE_NAME,"TEACHING_ID");
    /**班级编号*/
    public static Field clazzId = new Field(TABLE_NAME,"CLAZZ_ID");
 
	public <T> SooncodeTeachingDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeTeachingDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {teachingId});
	}
	 
}
	 
