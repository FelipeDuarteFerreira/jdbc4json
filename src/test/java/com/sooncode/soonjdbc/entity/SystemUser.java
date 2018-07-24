package com.sooncode.soonjdbc.entity;
/**
* 
* SYSTEM_USER
* SystemUser
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SystemUser { 

 	 /** 编号 */ 
	 private Integer userId; 

	 /**  */
	 private java.util.Date createDate ;

	 /** 余额 */
	 private java.util.Date updateDate ;

	 /**  */
	 private String address ;

	 /**  */
	 private String sex ;

	 /**  */
	 private String type ;

	 /** 姓名 */
	 private String name ;

	 /**  */
	 private Integer age ;

	 /**  */
	 public java.util.Date getCreateDate() { 
	 	 return createDate;
	 }

	 /**  */
	 public void setCreateDate(java.util.Date createDate) {
	 	 this.createDate = createDate;
	 }

	 /** 余额 */
	 public java.util.Date getUpdateDate() { 
	 	 return updateDate;
	 }

	 /** 余额 */
	 public void setUpdateDate(java.util.Date updateDate) {
	 	 this.updateDate = updateDate;
	 }

	 /**  */
	 public String getAddress() { 
	 	 return address;
	 }

	 /**  */
	 public void setAddress(String address) {
	 	 this.address = address;
	 }

	 /**  */
	 public String getSex() { 
	 	 return sex;
	 }

	 /**  */
	 public void setSex(String sex) {
	 	 this.sex = sex;
	 }

	 /** 编号 */
	 public Integer getUserId() { 
	 	 return userId;
	 }

	 /** 编号 */
	 public void setUserId(Integer userId) {
	 	 this.userId = userId;
	 }

	 /**  */
	 public String getType() { 
	 	 return type;
	 }

	 /**  */
	 public void setType(String type) {
	 	 this.type = type;
	 }

	 /** 姓名 */
	 public String getName() { 
	 	 return name;
	 }

	 /** 姓名 */
	 public void setName(String name) {
	 	 this.name = name;
	 }

	 /**  */
	 public Integer getAge() { 
	 	 return age;
	 }

	 /**  */
	 public void setAge(Integer age) {
	 	 this.age = age;
	 }

}
