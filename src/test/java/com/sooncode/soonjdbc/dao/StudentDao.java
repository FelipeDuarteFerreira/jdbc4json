package com.sooncode.soonjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.xml.SqlXml;

@Repository
public class StudentDao {
	@Autowired
	private Jdbc jdbc;
	
	private SqlXml sqlXml = new SqlXml("studentDao.xml");

	public void creadTabel() {
		Parameter p = sqlXml.getParameter("creadTabel");
		jdbc.update(p);
	}

}
