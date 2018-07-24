package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_GROUPS
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeGroupsDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_GROUPS";

    /**组名称*/
    public static Field groupName = new Field(TABLE_NAME,"GROUP_NAME");
    /**创建时间*/
    public static Field createDate = new Field(TABLE_NAME,"CREATE_DATE");
    /**组代码*/
    public static Field groupCode = new Field(TABLE_NAME,"GROUP_CODE");
    /**组状态*/
    public static Field groupState = new Field(TABLE_NAME,"GROUP_STATE");
    /**数据字典分组编号*/
    public static Field groupId = new Field(TABLE_NAME,"GROUP_ID");
 
	public <T> SooncodeGroupsDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeGroupsDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {groupId});
	}
	 
}
	 
