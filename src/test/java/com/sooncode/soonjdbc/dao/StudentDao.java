package com.sooncode.soonjdbc.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sooncode.soonjdbc.Entity;
import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.entity.SooncodeIdentity;
import com.sooncode.soonjdbc.entity.SooncodeStudent;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.xml.SqlXml;

@Repository
public class StudentDao {
	@Autowired
	private Jdbc jdbc;

	private SqlXml sqlXml = new SqlXml("com/sooncode/jdbc4json/dao/studentDao.xml");

	public SooncodeStudent getStudent(String studentId) {

		SooncodeStudent student = new SooncodeStudent();
		student.setStudentId(studentId);
		Parameter para = sqlXml.getParameter("getStudentByStudentId", student);

		Map<String, Object> map = jdbc.get(para);

		SooncodeStudent s = Entity.findEntity(map, SooncodeStudent.class);
		return s;

	}

	 

	public SooncodeStudent getStudentAndIdentity(String studentId) {

		SooncodeStudent student = new SooncodeStudent();
		student.setStudentId(studentId);
		Parameter para = sqlXml.getParameter("getStudentAndIdentity", student);

		Map<String, Object> map = jdbc.get(para);

		SooncodeStudent s = Entity.findEntity(map, SooncodeStudent.class);
		SooncodeIdentity i = Entity.findEntity(map, SooncodeIdentity.class);
		System.out.println("-------------------" + i + "---------------------");
		return s;

	}

}
