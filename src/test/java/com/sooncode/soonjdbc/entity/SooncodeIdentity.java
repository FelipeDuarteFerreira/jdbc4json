package com.sooncode.soonjdbc.entity;
/**
* 
* SOONCODE_IDENTITY
* SooncodeIdentity
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SooncodeIdentity { 

 	 /** 身份编号 */ 
	 private String identityId; 

	 /** 身份证号码 */
	 private String identityNumber ;

	 /** 学生编号 */
	 private String studentId ;

	 /** 身份编号 */
	 public String getIdentityId() { 
	 	 return identityId;
	 }

	 /** 身份编号 */
	 public void setIdentityId(String identityId) {
	 	 this.identityId = identityId;
	 }

	 /** 身份证号码 */
	 public String getIdentityNumber() { 
	 	 return identityNumber;
	 }

	 /** 身份证号码 */
	 public void setIdentityNumber(String identityNumber) {
	 	 this.identityNumber = identityNumber;
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
