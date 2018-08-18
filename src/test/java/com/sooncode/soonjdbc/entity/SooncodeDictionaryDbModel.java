package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 组件模块_数据字典
* SOONCODE_DICTIONARY
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeDictionaryDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_DICTIONARY";

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
    /**字典名称*/
    public  DbField<String> dictionaryName = new DbField<String>(TABLE_NAME,"DICTIONARY_NAME"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**字段编号*/
    public  DbField<Integer> dictionaryId = new DbField<Integer>(TABLE_NAME,"DICTIONARY_ID"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**字典代码*/
    public  DbField<String> dictionaryCode = new DbField<String>(TABLE_NAME,"DICTIONARY_CODE"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**数据状态*/
    public  DbField<Integer> dictionaryState = new DbField<Integer>(TABLE_NAME,"DICTIONARY_STATE"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
    /**组编号*/
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
 
	public <T> SooncodeDictionaryDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeDictionaryDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(dictionaryId);
		return primaryKeys;
	}
	 
}
	 
