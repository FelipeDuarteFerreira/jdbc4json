
package com.sooncode.soonjdbc.entity;
import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class SooncodeTeaching implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 授课编号 */ 
	 private Integer teachingId; 
	 /**  */
	 private Integer teacherId ;
	 /** 班级编号 */
	 private String clazzId ;

	 /** 授课编号 */
	 public Integer getTeachingId() { 
	 	 return teachingId;
	 }
	 /** 授课编号 */
	 public void setTeachingId(Integer teachingId) {
	 	 this.teachingId = teachingId;
	 }

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

}
