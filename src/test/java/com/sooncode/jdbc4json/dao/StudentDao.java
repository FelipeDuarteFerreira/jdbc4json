package com.sooncode.jdbc4json.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sooncode.jdbc4json.Jdbc;
import com.sooncode.jdbc4json.Entity;
import com.sooncode.jdbc4json.entity.Identity;
import com.sooncode.jdbc4json.entity.Student;
import com.sooncode.jdbc4json.sql.Parameter;
import com.sooncode.jdbc4json.sql.xml.SqlXml;

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
