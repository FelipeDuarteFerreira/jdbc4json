package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SOONCODE_DICTIONARY
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SooncodeDictionaryDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SOONCODE_DICTIONARY";

    /**创建时间*/
    public static Field createDate = new Field(TABLE_NAME,"CREATE_DATE");
    /**字典名称*/
    public static Field dictionaryName = new Field(TABLE_NAME,"DICTIONARY_NAME");
    /**字段编号*/
    public static Field dictionaryId = new Field(TABLE_NAME,"DICTIONARY_ID");
    /**字典代码*/
    public static Field dictionaryCode = new Field(TABLE_NAME,"DICTIONARY_CODE");
    /**数据状态*/
    public static Field dictionaryState = new Field(TABLE_NAME,"DICTIONARY_STATE");
    /**组编号*/
    public static Field groupId = new Field(TABLE_NAME,"GROUP_ID");
 
	public <T> SooncodeDictionaryDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SooncodeDictionaryDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {dictionaryId});
	}
	 
}
	 
