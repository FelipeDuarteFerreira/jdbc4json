package com.sooncode.soonjdbc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sooncode.soonjdbc.Entity;
import com.sooncode.soonjdbc.Jdbc;
import com.sooncode.soonjdbc.entity.CtbBookType;
import com.sooncode.soonjdbc.entity.Question;
import com.sooncode.soonjdbc.sql.Parameter;
import com.sooncode.soonjdbc.sql.xml.SqlXml;

@Repository
public class StudentDao {
	@Autowired
	private Jdbc jdbc;
	@Autowired
	private JdbcDao jdbcDao;

	private SqlXml sqlXml = new SqlXml("studentDao.xml");

	public void addQuestion(String xxx_question, String subjectCode, String gradeCode, String booktypeName) {

		CtbBookType dbt = new CtbBookType();
		dbt.setSubject(subjectCode);
		dbt.setGrade(gradeCode);
		dbt.setBookType(booktypeName);

		CtbBookType cb = jdbcDao.get(dbt);

		Map<String, String> map = new HashMap<>();
		map.put("xxx_question", xxx_question);
		map.put("subjectCode", subjectCode);
		map.put("gradeCode", gradeCode);
		map.put("booktypeCode", cb.getCtbCode());

		Parameter para = sqlXml.getParameter("sqlId", map);

		List<Map<String, Object>> list = jdbc.gets(para);
		List<Question> qtes = Entity.findEntity(list, Question.class);

		Map<String, List<Question>> m = new HashMap<>();
		int ss = 0;
		for (Question question : qtes) {
			List<Question> qes = m.get(question.getKnowledges());
			if (qes == null) {
				qes = new ArrayList<>();
			}
			qes.add(question);

			ss++;

			m.put(question.getKnowledges(), qes);
		}

		int n = 0;
		for (; n < 1500;) {
			n = fo(m, n);
		}

		System.out.println("#####################################################" + list.size());
	}

	private int fo(Map<String, List<Question>> m, int n) {
		for (Entry<String, List<Question>> en : m.entrySet()) {

			List<Question> questes = en.getValue();
			if (questes.size() == 0) {
				continue;
			}
			Question q = questes.get(questes.size() - 1);
			if (q != null) {
				jdbcDao.save(q);
				questes.remove(questes.size() - 1);
				n++;
			}

			if (n == 1500) {
				break;
			}

		}
		return n;
	}

}
