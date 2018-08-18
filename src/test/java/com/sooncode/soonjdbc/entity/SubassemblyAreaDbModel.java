package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 全国地区表
* SUBASSEMBLY_AREA
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SubassemblyAreaDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SUBASSEMBLY_AREA";

    /**电话区号*/
    public  DbField<String> phoneCode = new DbField<String>(TABLE_NAME,"PHONE_CODE"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**上级编码*/
    public  DbField<String> parentCode = new DbField<String>(TABLE_NAME,"PARENT_CODE"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**地区级别*/
    public  DbField<String> areaRank = new DbField<String>(TABLE_NAME,"AREA_RANK"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**地区名称*/
    public  DbField<String> areaName = new DbField<String>(TABLE_NAME,"AREA_NAME"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**邮政编码*/
    public  DbField<String> postalCode = new DbField<String>(TABLE_NAME,"POSTAL_CODE"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**地区类型*/
    public  DbField<String> areaType = new DbField<String>(TABLE_NAME,"AREA_TYPE"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**地区编码*/
    public  DbField<String> areaCode = new DbField<String>(TABLE_NAME,"AREA_CODE"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**是否为末级*/
    public  DbField<String> isLast = new DbField<String>(TABLE_NAME,"IS_LAST"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**全名称*/
    public  DbField<String> allName = new DbField<String>(TABLE_NAME,"ALL_NAME"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
    /**地区简称*/
    public  DbField<String> areaAbbreviation = new DbField<String>(TABLE_NAME,"AREA_ABBREVIATION"){
    @Override
		public String getValue() {
			return  this.value;
		}

		@Override
		public void setValue(String value) {
			this.value = value;
		}
    };
 
	public <T> SubassemblyAreaDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SubassemblyAreaDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(areaCode);
		return primaryKeys;
	}
	 
}
	 
