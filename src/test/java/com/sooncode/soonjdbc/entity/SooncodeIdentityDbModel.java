package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 身份
* SOONCODE_IDENTITY
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeIdentityDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_IDENTITY";

    /**身份编号*/
    public  DbField<String> identityId = new DbField<String>(TABLE_NAME,"IDENTITY_ID"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**身份证号码*/
    public  DbField<String> identityNumber = new DbField<String>(TABLE_NAME,"IDENTITY_NUMBER"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**学生编号*/
    public  DbField<String> studentId = new DbField<String>(TABLE_NAME,"STUDENT_ID"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
 
	public <T> SooncodeIdentityDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeIdentityDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(identityId);
		return primaryKeys;
	}
	 
}
	 
