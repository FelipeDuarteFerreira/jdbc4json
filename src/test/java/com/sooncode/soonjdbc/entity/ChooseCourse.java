package com.sooncode.soonjdbc.entity;
/**
* 
* CHOOSE_COURSE
* ChooseCourse
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class ChooseCourse { 

 	 /** 选课编号 */ 
	 private String chooseId; 

	 /**  */
	 private Integer score ;

	 /** 课程编号 */
	 private String courseId ;

	 /** 等级 */
	 private Integer grade ;

	 /** 学生编号 */
	 private String studentId ;

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

	 /** 学生编号 */
	 public String getStudentId() { 
	 	 return studentId;
	 }

	 /** 学生编号 */
	 public void setStudentId(String studentId) {
	 	 this.studentId = studentId;
	 }

}
