
package com.sooncode.soonjdbc.entity;
import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class SooncodeTeacher implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 老师编号 */ 
	 private Integer teacherId; 
	 /**  */
	 private Integer xxx ;
	 /**  */
	 private Integer hight ;
	 /** 老师名称 */
	 private String teacherName ;
	 /**  */
	 private String sex ;
	 /**  */
	 private String address ;
	 /** 老师年龄 */
	 private Integer teacherAge ;
	 /** 创建时间 */
	 private java.util.Date createDate ;

	 /** 老师编号 */
	 public Integer getTeacherId() { 
	 	 return teacherId;
	 }
	 /** 老师编号 */
	 public void setTeacherId(Integer teacherId) {
	 	 this.teacherId = teacherId;
	 }

	 /**  */
	 public Integer getXxx() { 
	 	 return xxx;
	 }
	 /**  */
	 public void setXxx(Integer xxx) {
	 	 this.xxx = xxx;
	 }

	 /**  */
	 public Integer getHight() { 
	 	 return hight;
	 }
	 /**  */
	 public void setHight(Integer hight) {
	 	 this.hight = hight;
	 }

	 /** 老师名称 */
	 public String getTeacherName() { 
	 	 return teacherName;
	 }
	 /** 老师名称 */
	 public void setTeacherName(String teacherName) {
	 	 this.teacherName = teacherName;
	 }

	 /**  */
	 public String getSex() { 
	 	 return sex;
	 }
	 /**  */
	 public void setSex(String sex) {
	 	 this.sex = sex;
	 }

	 /**  */
	 public String getAddress() { 
	 	 return address;
	 }
	 /**  */
	 public void setAddress(String address) {
	 	 this.address = address;
	 }

	 /** 老师年龄 */
	 public Integer getTeacherAge() { 
	 	 return teacherAge;
	 }
	 /** 老师年龄 */
	 public void setTeacherAge(Integer teacherAge) {
	 	 this.teacherAge = teacherAge;
	 }

	 /** 创建时间 */
	 public java.util.Date getCreateDate() { 
	 	 return createDate;
	 }
	 /** 创建时间 */
	 public void setCreateDate(java.util.Date createDate) {
	 	 this.createDate = createDate;
	 }

}
