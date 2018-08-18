package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 授课表

* SOONCODE_TEACHING
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeTeachingDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_TEACHING";

    /***/
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
    /**授课编号*/
    public  DbField<Integer> teachingId = new DbField<Integer>(TABLE_NAME,"TEACHING_ID"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
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
 
	public <T> SooncodeTeachingDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeTeachingDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(teachingId);
		return primaryKeys;
	}
	 
}
	 
