package com.sooncode.soonjdbc.entity;
import java.util.ArrayList;
import java.util.List;

import com.sooncode.soonjdbc.util.DbModel;
import com.sooncode.soonjdbc.util.DbField;


/**
* 好友
* SYSTEM_FRIEND
* @author SOONJDBC CODE GENERATOR  
* 
*/ 
 
public class SystemFriendDbModel extends DbModel {
	
	private static final String  TABLE_NAME = "SYSTEM_FRIEND";

    /***/
    public  DbField<Integer> friendUserId = new DbField<Integer>(TABLE_NAME,"FRIEND_USER_ID"){
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
    public  DbField<Integer> friendId = new DbField<Integer>(TABLE_NAME,"FRIEND_ID"){
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
    public  DbField<Integer> meUserId = new DbField<Integer>(TABLE_NAME,"ME_USER_ID"){
    @Override
		public Integer getValue() {
			return  this.value;
		}

		@Override
		public void setValue(Integer value) {
			this.value = value;
		}
    };
 
	public <T> SystemFriendDbModel (T javaBean) {
		super.init(javaBean);
	}
	
	public SystemFriendDbModel () {
		
	}

	@Override
	public String tableName() {
		return TABLE_NAME;
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbField<?>> primaryKeys() {
		List<DbField<?>> primaryKeys = new ArrayList<>();
		primaryKeys.add(friendId);
		return primaryKeys;
	}
	 
}
	 
