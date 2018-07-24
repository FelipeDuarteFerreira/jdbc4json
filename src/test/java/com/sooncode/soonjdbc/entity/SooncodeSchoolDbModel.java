package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_SCHOOL
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeSchoolDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_SCHOOL";

    /**学校名称*/
    public static Field schoolName = new Field(TABLE_NAME,"SCHOOL_NAME");
    /**学习编号*/
    public static Field schoolId = new Field(TABLE_NAME,"SCHOOL_ID");
    /**校长编号*/
    public static Field principalId = new Field(TABLE_NAME,"PRINCIPAL_ID");
 
	public <T> SooncodeSchoolDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeSchoolDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {schoolId});
	}
	 
}
	 
