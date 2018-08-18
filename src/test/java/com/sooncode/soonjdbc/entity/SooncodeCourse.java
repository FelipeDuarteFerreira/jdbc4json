package com.sooncode.soonjdbc.entity;
/**
* 课程
* SOONCODE_COURSE
* SooncodeCourse
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SooncodeCourse { 

 	 /** 课程编号 */ 
	 private String courseId; 

	 /**  */
	 private Integer score ;

	 /** 课程名称 */
	 private String courseName ;

	 /**  */
	 public Integer getScore() { 
	 	 return score;
	 }

	 /**  */
	 public void setScore(Integer score) {
	 	 this.score = score;
	 }

	 /** 课程编号 */
	 public String getCourseId() { 
	 	 return courseId;
	 }

	 /** 课程编号 */
	 public void setCourseId(String courseId) {
	 	 this.courseId = courseId;
	 }

	 /** 课程名称 */
	 public String getCourseName() { 
	 	 return courseName;
	 }

	 /** 课程名称 */
	 public void setCourseName(String courseName) {
	 	 this.courseName = courseName;
	 }

}
