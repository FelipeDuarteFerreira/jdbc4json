package com.sooncode.soonjdbc.model;

import com.sooncode.soonjdbc.entity.SooncodeIdentity;
import com.sooncode.soonjdbc.entity.SooncodeStudent;

public class StudentAndIdentity {

	private SooncodeStudent student;
	private SooncodeIdentity identity;
	public SooncodeStudent getStudent() {
		return student;
	}
	public void setStudent(SooncodeStudent student) {
		this.student = student;
	}
	public SooncodeIdentity getIdentity() {
		return identity;
	}
	public void setIdentity(SooncodeIdentity identity) {
		this.identity = identity;
	}
	 
	
	
}
