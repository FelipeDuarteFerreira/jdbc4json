package com.sooncode.soonjdbc;

import java.lang.reflect.Field;
import java.util.List;

import com.sooncode.soonjdbc.model.StudentAndIdentity;
import com.sooncode.soonjdbc.reflect.RObject;

public class RObject__Test {
	public static void main(String[] args) {
		RObject<StudentAndIdentity> rObj = new RObject<>(StudentAndIdentity.class); 
		List<Field> fs =  rObj.getFields();
		System.out.println("ModelTransform.main()"+fs);
	}
}
