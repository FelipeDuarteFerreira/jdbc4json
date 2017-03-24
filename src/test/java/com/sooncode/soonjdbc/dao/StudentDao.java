package com.sooncode.soonjdbc.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sooncode.soonjdbc.Entity;
import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.entity.Identity;
import com.sooncode.soonjdbc.entity.Student;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.xml.SqlXml;

@Repository
public class StudentDao {
	@Autowired
	private Jdbc jdbc;

	private SqlXml sqlXml = new SqlXml("com/sooncode/jdbc4json/dao/studentDao.xml");

	public Student getStudent(String studentId) {

		Student student = new Student();
		student.setStudentId(studentId);
		Parameter para = sqlXml.getParameter("getStudentByStudentId", student);

		Map<String, Object> map = jdbc.get(para);

		Student s = Entity.findEntity(map, Student.class);
		return s;

	}

	 

	public Student getStudentAndIdentity(String studentId) {

		Student student = new Student();
		student.setStudentId(studentId);
		Parameter para = sqlXml.getParameter("getStudentAndIdentity", student);

		Map<String, Object> map = jdbc.get(para);

		Student s = Entity.findEntity(map, Student.class);
		Identity i = Entity.findEntity(map, Identity.class);
		System.out.println("-------------------" + i + "---------------------");
		return s;

	}

}
