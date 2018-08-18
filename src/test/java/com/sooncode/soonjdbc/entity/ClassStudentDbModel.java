package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 
* CLASS_STUDENT
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class ClassStudentDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "CLASS_STUDENT";

    /***/
    public  DbField<Integer> classId = new DbField<Integer>(TABLE_NAME,"CLASS_ID"){
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
    public  DbField<Integer> studentId = new DbField<Integer>(TABLE_NAME,"STUDENT_ID"){
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
    public  DbField<String> studentName = new DbField<String>(TABLE_NAME,"STUDENT_NAME"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
 
	public <T> ClassStudentDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public ClassStudentDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(classId);
		primaryKeys.add(studentId);
		return primaryKeys;
	}
	 
}
	 
