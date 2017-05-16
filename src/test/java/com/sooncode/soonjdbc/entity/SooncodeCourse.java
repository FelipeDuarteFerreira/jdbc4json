
package com.sooncode.soonjdbc.entity;
import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class SooncodeCourse implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 课程编号 */ 
	 private String courseId; 
	 /** 课程名称 */
	 private String courseName ;
	 /**  */
	 private Integer score ;

	 /** 课程名称 */
	 public String getCourseName() { 
	 	 return courseName;
	 }
	 /** 课程名称 */
	 public void setCourseName(String courseName) {
	 	 this.courseName = courseName;
	 }

	 /** 课程编号 */
	 public String getCourseId() { 
	 	 return courseId;
	 }
	 /** 课程编号 */
	 public void setCourseId(String courseId) {
	 	 this.courseId = courseId;
	 }

	 /**  */
	 public Integer getScore() { 
	 	 return score;
	 }
	 /**  */
	 public void setScore(Integer score) {
	 	 this.score = score;
	 }

}
