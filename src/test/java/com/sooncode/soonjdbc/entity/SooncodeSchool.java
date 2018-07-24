package com.sooncode.soonjdbc.entity;
/**
* 
* SOONCODE_SCHOOL
* SooncodeSchool
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SooncodeSchool { 

 	 /** 学习编号 */ 
	 private Integer schoolId; 

	 /** 学校名称 */
	 private String schoolName ;

	 /** 校长编号 */
	 private Integer principalId ;

	 /** 学校名称 */
	 public String getSchoolName() { 
	 	 return schoolName;
	 }

	 /** 学校名称 */
	 public void setSchoolName(String schoolName) {
	 	 this.schoolName = schoolName;
	 }

	 /** 学习编号 */
	 public Integer getSchoolId() { 
	 	 return schoolId;
	 }

	 /** 学习编号 */
	 public void setSchoolId(Integer schoolId) {
	 	 this.schoolId = schoolId;
	 }

	 /** 校长编号 */
	 public Integer getPrincipalId() { 
	 	 return principalId;
	 }

	 /** 校长编号 */
	 public void setPrincipalId(Integer principalId) {
	 	 this.principalId = principalId;
	 }

}
