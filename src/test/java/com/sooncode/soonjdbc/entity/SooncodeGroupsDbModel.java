package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 组件模块_数据字典分组
* SOONCODE_GROUPS
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeGroupsDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_GROUPS";

    /**组名称*/
    public  DbField<String> groupName = new DbField<String>(TABLE_NAME,"GROUP_NAME"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
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
    /**组代码*/
    public  DbField<String> groupCode = new DbField<String>(TABLE_NAME,"GROUP_CODE"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**组状态*/
    public  DbField<Integer> groupState = new DbField<Integer>(TABLE_NAME,"GROUP_STATE"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**数据字典分组编号*/
    public  DbField<Integer> groupId = new DbField<Integer>(TABLE_NAME,"GROUP_ID"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
 
	public <T> SooncodeGroupsDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeGroupsDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(groupId);
		return primaryKeys;
	}
	 
}
	 
