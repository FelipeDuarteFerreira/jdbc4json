package com.sooncode.soonjdbc.model;

import com.sooncode.soonjdbc.entity.Identity;
import com.sooncode.soonjdbc.entity.Student;

public class StudentAndIdentity {

	private Student student;
	private Identity identity;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Identity getIdentity() {
		return identity;
	}
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
	
}
