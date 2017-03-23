package com.sooncode.jdbc4json.entity;

 
/**
*学生
* @author hechen 
* 
*/ 
public class Student { 
	  
	 /** 学生编号 */ 
	 private String studentId; 
	 /** 姓名 */
	 private String name ;
	 /** 年龄 */
	 private Integer age ;
	 /** 性别 */
	 private String sex ;
	 /**  */
	 private String clazzId ;
	 /**  */
	 private String identityId ;

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

	 /** 性别 */
	 public String getSex() { 
	 	 return sex;
	 }
	 /** 性别 */
	 public void setSex(String sex) {
	 	 this.sex = sex;
	 }

	 /**  */
	 public String getClazzId() { 
	 	 return clazzId;
	 }
	 /**  */
	 public void setClazzId(String clazzId) {
	 	 this.clazzId = clazzId;
	 }

	 /**  */
	 public String getIdentityId() { 
	 	 return identityId;
	 }
	 /**  */
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
		return "Student [studentId=" + studentId + ", name=" + name + ", age=" + age + ", sex=" + sex + ", clazzId=" + clazzId + ", identityId=" + identityId + "]";
	}

}

