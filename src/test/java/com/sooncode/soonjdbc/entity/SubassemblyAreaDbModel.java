package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SUBASSEMBLY_AREA
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SubassemblyAreaDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SUBASSEMBLY_AREA";

    /**电话区号*/
    public static Field phoneCode = new Field(TABLE_NAME,"PHONE_CODE");
    /**上级编码*/
    public static Field parentCode = new Field(TABLE_NAME,"PARENT_CODE");
    /**地区级别*/
    public static Field areaRank = new Field(TABLE_NAME,"AREA_RANK");
    /**地区名称*/
    public static Field areaName = new Field(TABLE_NAME,"AREA_NAME");
    /**邮政编码*/
    public static Field postalCode = new Field(TABLE_NAME,"POSTAL_CODE");
    /**地区类型*/
    public static Field areaType = new Field(TABLE_NAME,"AREA_TYPE");
    /**地区编码*/
    public static Field areaCode = new Field(TABLE_NAME,"AREA_CODE");
    /**是否为末级*/
    public static Field isLast = new Field(TABLE_NAME,"IS_LAST");
    /**全名称*/
    public static Field allName = new Field(TABLE_NAME,"ALL_NAME");
    /**地区简称*/
    public static Field areaAbbreviation = new Field(TABLE_NAME,"AREA_ABBREVIATION");
 
	public <T> SubassemblyAreaDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SubassemblyAreaDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {areaCode});
	}
	 
}
	 
