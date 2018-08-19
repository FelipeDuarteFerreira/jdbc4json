package com.sooncode.soonjdbc.dao;



import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.entity.SystemFriendDbModel;
import com.sooncode.soonjdbc.entity.SystemUserDbModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class AdvancedDbService_Test {
	@Autowired
	private DbService dbService;
	private static Logger logger = Logger.getLogger("JdbcDao_Test.class");

	 

	 
	
	
	@Test
	public void update3() {
		SystemUserDbModel sudm = new SystemUserDbModel(); 
		sudm.userId.setValue(2213);
		sudm.age.setValue(34);
		sudm.createDate.setValue(new Date());
		
		SystemFriendDbModel sfdm = new SystemFriendDbModel();
		
		a.result(sudm.userId);
		a.result(sfdm.friendId);
		a.result(sudm.createDate);
		a.form(SystemFriendDbModel);
		a.leftJoin(SystemFriendDbModel).on(sudm.userId,sfdm.friendId).and();
		a.rightJoinOn();
		a.con(sudm.userId,like , "hechn");
		
		r
		 
		
	 
	}

	 
 

}
