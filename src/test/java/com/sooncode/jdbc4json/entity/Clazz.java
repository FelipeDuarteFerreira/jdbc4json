package com.sooncode.jdbc4json.entity;

import java.io.Serializable;
/**
*班级
* @author hechen 
* 
*/ 
public class Clazz implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 班级编号 */ 
	 private String clazzId; 
	 /** 班级名称 */
	 private String clazzName ;
	 /**  */
	 private Integer studentNumber ;

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

	 /** 班级编号 */
	 public String getClazzId() { 
	 	 return clazzId;
	 }
	 /** 班级编号 */
	 public void setClazzId(String clazzId) {
	 	 this.clazzId = clazzId;
	 }
	@Override
	public String toString() {
		return "Clazz [clazzId=" + clazzId + ", clazzName=" + clazzName + ", studentNumber=" + studentNumber + "]";
	}

}

