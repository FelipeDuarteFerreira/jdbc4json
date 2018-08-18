package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 用户
* SYSTEM_USER
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SystemUserDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SYSTEM_USER";

    /***/
    public  DbField<java.util.Date> createDate = new DbField<java.util.Date>(TABLE_NAME,"CREATE_DATE"){
    @Override
		public java.util.Date getValue() {
			return  this.value;
		}

		@Override
		public void setValue(java.util.Date value) {
			this.value = value;
		}
    };
    /**余额*/
    public  DbField<java.util.Date> updateDate = new DbField<java.util.Date>(TABLE_NAME,"UPDATE_DATE"){
    @Override
		public java.util.Date getValue() {
			return  this.value;
		}

		@Override
		public void setValue(java.util.Date value) {
			this.value = value;
		}
    };
    /***/
    public  DbField<String> address = new DbField<String>(TABLE_NAME,"ADDRESS"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /***/
    public  DbField<String> sex = new DbField<String>(TABLE_NAME,"SEX"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**编号*/
    public  DbField<Integer> userId = new DbField<Integer>(TABLE_NAME,"USER_ID"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /***/
    public  DbField<String> type = new DbField<String>(TABLE_NAME,"TYPE"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**姓名*/
    public  DbField<String> name = new DbField<String>(TABLE_NAME,"NAME"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /***/
    public  DbField<Integer> age = new DbField<Integer>(TABLE_NAME,"AGE"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
 
	public <T> SystemUserDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SystemUserDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(userId);
		return primaryKeys;
	}
	 
}
	 
