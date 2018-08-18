package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 班级
* SOONCODE_CLAZZ
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeClazzDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_CLAZZ";

    /**班级编号*/
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
    /**班级名称*/
    public  DbField<String> clazzName = new DbField<String>(TABLE_NAME,"CLAZZ_NAME"){
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
    public  DbField<Integer> studentNumber = new DbField<Integer>(TABLE_NAME,"STUDENT_NUMBER"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**学校编号*/
    public  DbField<Integer> schoolId = new DbField<Integer>(TABLE_NAME,"SCHOOL_ID"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**班主任编号*/
    public  DbField<Integer> mainTeacherId = new DbField<Integer>(TABLE_NAME,"MAIN_TEACHER_ID"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**班长编号*/
    public  DbField<String> monitorId = new DbField<String>(TABLE_NAME,"MONITOR_ID"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
 
	public <T> SooncodeClazzDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeClazzDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(clazzId);
		return primaryKeys;
	}
	 
}
	 
