package com.sooncode.soonjdbc.entity;
import java.util.Arrays;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.Field;


/**
* 
* SYSTEM_FRIEND
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SystemFriendDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SYSTEM_FRIEND";

    /***/
    public static Field friendUserId = new Field(TABLE_NAME,"FRIEND_USER_ID");
    /***/
    public static Field friendId = new Field(TABLE_NAME,"FRIEND_ID");
    /***/
    public static Field meUserId = new Field(TABLE_NAME,"ME_USER_ID");
 
	public <T> SystemFriendDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SystemFriendDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}

    @Override
	public List<Field> primaryKeys() {
		return Arrays.asList(new Field[] {friendId});
	}
	 
}
	 
