package com.sooncode.soonjdbc.entity;
/**
* 学生
* SOONCODE_STUDENT
* SooncodeStudent
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SooncodeStudent { 

 	 /** 学生编号 */ 
	 private String studentId; 

	 /**  */
	 private String identityId ;

	 /**  */
	 private String clazzId ;

	 /** 性别 */
	 private String sex ;

	 /** 姓名 */
	 private String name ;

	 /** 年龄 */
	 private Integer age ;

	 /**  */
	 public String getIdentityId() { 
	 	 return identityId;
	 }

	 /**  */
	 public void setIdentityId(String identityId) {
	 	 this.identityId = identityId;
	 }

	 /**  */
	 public String getClazzId() { 
	 	 return clazzId;
	 }

	 /**  */
	 public void setClazzId(String clazzId) {
	 	 this.clazzId = clazzId;
	 }

	 /** 性别 */
	 public String getSex() { 
	 	 return sex;
	 }

	 /** 性别 */
	 public void setSex(String sex) {
	 	 this.sex = sex;
	 }

	 /** 学生编号 */
	 public String getStudentId() { 
	 	 return studentId;
	 }

	 /** 学生编号 */
	 public void setStudentId(String studentId) {
	 	 this.studentId = studentId;
	 }

	 /** 姓名 */
	 public String getName() { 
	 	 return name;
	 }

	 /** 姓名 */
	 public void setName(String name) {
	 	 this.name = name;
	 }

	 /** 年龄 */
	 public Integer getAge() { 
	 	 return age;
	 }

	 /** 年龄 */
	 public void setAge(Integer age) {
	 	 this.age = age;
	 }

}
