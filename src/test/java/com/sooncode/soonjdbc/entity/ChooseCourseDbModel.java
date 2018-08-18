package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 选课
* CHOOSE_COURSE
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class ChooseCourseDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "CHOOSE_COURSE";

    /**选课编号*/
    public  DbField<String> chooseId = new DbField<String>(TABLE_NAME,"CHOOSE_ID"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
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
    /**等级*/
    public  DbField<Integer> grade = new DbField<Integer>(TABLE_NAME,"GRADE"){
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
 
	public <T> ChooseCourseDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public ChooseCourseDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(chooseId);
		return primaryKeys;
	}
	 
}
	 
