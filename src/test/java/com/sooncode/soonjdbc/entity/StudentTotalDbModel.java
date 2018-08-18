package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 
* STUDENT_TOTAL
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class StudentTotalDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "STUDENT_TOTAL";

    /***/
    public  DbField<Integer> total = new DbField<Integer>(TABLE_NAME,"TOTAL"){
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
    public  DbField<Integer> chinese = new DbField<Integer>(TABLE_NAME,"CHINESE"){
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
    public  DbField<Integer> id = new DbField<Integer>(TABLE_NAME,"ID"){
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
    public  DbField<Integer> math = new DbField<Integer>(TABLE_NAME,"MATH"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
 
	public <T> StudentTotalDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public StudentTotalDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(id);
		return primaryKeys;
	}
	 
}
	 
