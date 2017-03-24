package com.sooncode.soonjdbc.entity;

import java.io.Serializable;
/**
*身份
* @author hechen 
* 
*/ 
public class Identity implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 身份编号 */ 
	 private String identityId; 
	 /** 身份证号码 */
	 private String identityNumber ;
	 /** 学生编号 */
	 private String studentId ;

	 /** 身份证号码 */
	 public String getIdentityNumber() { 
	 	 return identityNumber;
	 }
	 /** 身份证号码 */
	 public void setIdentityNumber(String identityNumber) {
	 	 this.identityNumber = identityNumber;
	 }

	 /** 身份编号 */
	 public String getIdentityId() { 
	 	 return identityId;
	 }
	 /** 身份编号 */
	 public void setIdentityId(String identityId) {
	 	 this.identityId = identityId;
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
		return "Identity [identityId=" + identityId + ", identityNumber=" + identityNumber + ", studentId=" + studentId + "]";
	}

	 
	 
}

