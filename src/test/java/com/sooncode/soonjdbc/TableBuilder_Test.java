package com.sooncode.soonjdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sooncode.soonjdbc.util.create_entity.JavaBeanBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TableBuilder_Test {
	@Autowired
	private JavaBeanBuilder javaBeanBuilder;
	
	@Test
	public void builderJavaBean(){
	 
	}
	
}
