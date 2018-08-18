package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 课程
* SOONCODE_COURSE
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeCourseDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_COURSE";

    /**课程编号*/
    public  DbField<String> courseId = new DbField<String>(TABLE_NAME,"COURSE_ID"){
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
    public  DbField<Integer> score = new DbField<Integer>(TABLE_NAME,"SCORE"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**课程名称*/
    public  DbField<String> courseName = new DbField<String>(TABLE_NAME,"COURSE_NAME"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
 
	public <T> SooncodeCourseDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeCourseDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(courseId);
		return primaryKeys;
	}
	 
}
	 
