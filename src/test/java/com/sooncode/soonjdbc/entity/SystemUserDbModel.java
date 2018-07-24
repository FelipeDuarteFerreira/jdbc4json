package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SYSTEM_USER
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SystemUserDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SYSTEM_USER";

    /***/
    public  Field createDate = new Field(TABLE_NAME,"CREATE_DATE");
    /**余额*/
    public  Field updateDate = new Field(TABLE_NAME,"UPDATE_DATE");
    /***/
    public  Field address = new Field(TABLE_NAME,"ADDRESS");
    /***/
    public  Field sex = new Field(TABLE_NAME,"SEX");
    /**编号*/
    public  Field userId = new Field(TABLE_NAME,"USER_ID");
    /***/
    public  Field type = new Field(TABLE_NAME,"TYPE");
    /**姓名*/
    public  Field name = new Field(TABLE_NAME,"NAME");
    /***/
    public  Field age = new Field(TABLE_NAME,"AGE");
 
	public <T> SystemUserDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SystemUserDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {userId});
	}
	 
}
	 
