package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 学生
* SOONCODE_STUDENT
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeStudentDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_STUDENT";

    /***/
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
    /***/
    public  DbField<String> clazzId = new DbField<String>(TABLE_NAME,"CLAZZ_ID"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**性别*/
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
    /**年龄*/
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
 
	public <T> SooncodeStudentDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeStudentDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(studentId);
		return primaryKeys;
	}
	 
}
	 
