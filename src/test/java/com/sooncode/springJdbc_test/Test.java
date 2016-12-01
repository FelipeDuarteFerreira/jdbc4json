package com.sooncode.springJdbc_test;

import org.hamcrest.core.IsInstanceOf;

public class Test implements Interface1 ,Interface2 {
	
	
	public static void main(String[] args) {
		Test t = new Test();
		if(t instanceof Interface2){
			System.out.println("Interface2");
		}
	}

}
