package com.sooncode.soonjdbc.entity;
/**
* 授课表

* SOONCODE_TEACHING
* SooncodeTeaching
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SooncodeTeaching { 

 	 /** 授课编号 */ 
	 private Integer teachingId; 

	 /**  */
	 private Integer teacherId ;

	 /** 班级编号 */
	 private String clazzId ;

	 /**  */
	 public Integer getTeacherId() { 
	 	 return teacherId;
	 }

	 /**  */
	 public void setTeacherId(Integer teacherId) {
	 	 this.teacherId = teacherId;
	 }

	 /** 班级编号 */
	 public String getClazzId() { 
	 	 return clazzId;
	 }

	 /** 班级编号 */
	 public void setClazzId(String clazzId) {
	 	 this.clazzId = clazzId;
	 }

	 /** 授课编号 */
	 public Integer getTeachingId() { 
	 	 return teachingId;
	 }

	 /** 授课编号 */
	 public void setTeachingId(Integer teachingId) {
	 	 this.teachingId = teachingId;
	 }

}
