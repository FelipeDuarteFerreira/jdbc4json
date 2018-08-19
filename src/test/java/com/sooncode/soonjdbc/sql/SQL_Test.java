package com.sooncode.soonjdbc.sql;
 
import org.apache.log4j.Logger;

import com.sooncode.soonjdbc.entity.SystemFriendDbModel;
import com.sooncode.soonjdbc.entity.SystemUserDbModel;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.condition.And;
import com.sooncode.soonjdbc.sql.condition.Cond;
import com.sooncode.soonjdbc.sql.condition.Or;
import com.sooncode.soonjdbc.sql.condition.sign.EqualSign;
import com.sooncode.soonjdbc.sql.condition.sign.LikeSign;
 

public class SQL_Test{
	private static final Logger logger = Logger.getLogger("Cond_Test");
	public static void main(String[] args) {
		
		SystemUserDbModel sudm = new SystemUserDbModel();
		SystemFriendDbModel sfdm = new SystemFriendDbModel();
		SQL sql = new SQL();
		
		sql.SELECT(sudm.userId,sudm.age)
		.FROM(sudm)
		.INNER_JOIN(sfdm)
		.ON(sudm.userId,sfdm.friendUserId)
		.WHERE(sudm.userId,sfdm.friendUserId)
		.AND();
		 
		
	}

}
