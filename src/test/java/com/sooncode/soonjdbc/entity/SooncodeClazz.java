package com.sooncode.soonjdbc.entity;
/**
* 
* SOONCODE_CLAZZ
* SooncodeClazz
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SooncodeClazz { 

 	 /** 班级编号 */ 
	 private String clazzId; 

	 /** 班级名称 */
	 private String clazzName ;

	 /**  */
	 private Integer studentNumber ;

	 /** 学校编号 */
	 private Integer schoolId ;

	 /** 班主任编号 */
	 private Integer mainTeacherId ;

	 /** 班长编号 */
	 private String monitorId ;

	 /** 班级编号 */
	 public String getClazzId() { 
	 	 return clazzId;
	 }

	 /** 班级编号 */
	 public void setClazzId(String clazzId) {
	 	 this.clazzId = clazzId;
	 }

	 /** 班级名称 */
	 public String getClazzName() { 
	 	 return clazzName;
	 }

	 /** 班级名称 */
	 public void setClazzName(String clazzName) {
	 	 this.clazzName = clazzName;
	 }

	 /**  */
	 public Integer getStudentNumber() { 
	 	 return studentNumber;
	 }

	 /**  */
	 public void setStudentNumber(Integer studentNumber) {
	 	 this.studentNumber = studentNumber;
	 }

	 /** 学校编号 */
	 public Integer getSchoolId() { 
	 	 return schoolId;
	 }

	 /** 学校编号 */
	 public void setSchoolId(Integer schoolId) {
	 	 this.schoolId = schoolId;
	 }

	 /** 班主任编号 */
	 public Integer getMainTeacherId() { 
	 	 return mainTeacherId;
	 }

	 @Override
	public String toString() {
		return "SooncodeClazz [clazzId=" + clazzId + ", clazzName=" + clazzName + ", studentNumber=" + studentNumber + ", schoolId=" + schoolId + ", mainTeacherId=" + mainTeacherId + ", monitorId=" + monitorId + "]";
	}

	/** 班主任编号 */
	 public void setMainTeacherId(Integer mainTeacherId) {
	 	 this.mainTeacherId = mainTeacherId;
	 }

	 /** 班长编号 */
	 public String getMonitorId() { 
	 	 return monitorId;
	 }

	 /** 班长编号 */
	 public void setMonitorId(String monitorId) {
	 	 this.monitorId = monitorId;
	 }
	 
	 

}
