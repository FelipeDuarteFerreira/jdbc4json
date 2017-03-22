package com.sooncode.jdbc4json.entity;

import java.io.Serializable;
/**
*选课
* @author hechen 
* 
*/ 
public class ChooseCourse implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 选课编号 */ 
	 private String chooseId; 
	 /** 课程编号 */
	 private String courseId ;
	 /** 等级 */
	 private Integer grade ;
	 /**  */
	 private Integer score ;
	 /** 学生编号 */
	 private String studentId ;

	 /** 课程编号 */
	 public String getCourseId() { 
	 	 return courseId;
	 }
	 /** 课程编号 */
	 public void setCourseId(String courseId) {
	 	 this.courseId = courseId;
	 }

	 /** 等级 */
	 public Integer getGrade() { 
	 	 return grade;
	 }
	 /** 等级 */
	 public void setGrade(Integer grade) {
	 	 this.grade = grade;
	 }

	 /**  */
	 public Integer getScore() { 
	 	 return score;
	 }
	 /**  */
	 public void setScore(Integer score) {
	 	 this.score = score;
	 }

	 /** 选课编号 */
	 public String getChooseId() { 
	 	 return chooseId;
	 }
	 /** 选课编号 */
	 public void setChooseId(String chooseId) {
	 	 this.chooseId = chooseId;
	 }

	 /** 学生编号 */
	 public String getStudentId() { 
	 	 return studentId;
	 }
	 /** 学生编号 */
	 public void setStudentId(String studentId) {
	 	 this.studentId = studentId;
	 }
	@Override
	public String toString() {
		return "ChooseCourse [chooseId=" + chooseId + ", courseId=" + courseId + ", grade=" + grade + ", score=" + score + ", studentId=" + studentId + "]";
	}

}

