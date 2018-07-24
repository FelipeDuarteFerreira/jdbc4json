package com.sooncode.type;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class Test_A {

	@Autowired
	private TypesManager tm ;
	
	@Test
	public void test() {
		
		String code = "B";
		
		Type t = tm.getType(code);
		
		System.out.println("Test.test()"+t.getTypeCode());
		System.out.println("Test.test()"+ t.getNumber());
		
		
	}
}
