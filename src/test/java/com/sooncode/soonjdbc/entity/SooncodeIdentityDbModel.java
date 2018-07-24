package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_IDENTITY
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeIdentityDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_IDENTITY";

    /**身份编号*/
    public static Field identityId = new Field(TABLE_NAME,"IDENTITY_ID");
    /**身份证号码*/
    public static Field identityNumber = new Field(TABLE_NAME,"IDENTITY_NUMBER");
    /**学生编号*/
    public static Field studentId = new Field(TABLE_NAME,"STUDENT_ID");
 
	public <T> SooncodeIdentityDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeIdentityDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {identityId});
	}
	 
}
	 
