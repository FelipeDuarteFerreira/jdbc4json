package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 老师
* SOONCODE_TEACHER
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeTeacherDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_TEACHER";

    /**老师编号*/
    public  DbField<Integer> teacherId = new DbField<Integer>(TABLE_NAME,"TEACHER_ID"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**老师年龄*/
    public  DbField<Integer> teacherAge = new DbField<Integer>(TABLE_NAME,"TEACHER_AGE"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**创建时间*/
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
    /**老师名称*/
    public  DbField<String> teacherName = new DbField<String>(TABLE_NAME,"TEACHER_NAME"){
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
    /***/
    public  DbField<Integer> xxx = new DbField<Integer>(TABLE_NAME,"XXX"){
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
    public  DbField<Integer> hight = new DbField<Integer>(TABLE_NAME,"HIGHT"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
 
	public <T> SooncodeTeacherDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeTeacherDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(teacherId);
		return primaryKeys;
	}
	 
}
	 
