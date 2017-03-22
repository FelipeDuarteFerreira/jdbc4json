package com.sooncode.jdbc4json.entity;
import java.io.Serializable;
/**
*用户
* @author hechen 
* 
*/ 
public class User implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	
	 /** 姓名 */
	 private String name ;
	 /**  */
	 private Integer age ;
	 /** 余额 */
	 private java.util.Date updateDate ;
	 /**  */
	 private String sex ;
	 /**  */
	 private String address ;
	 /**  */
	 private java.util.Date createDate ;

	 /** 编号 */ 
	 private Integer id; 
	 
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

	 /** 余额 */
	 public java.util.Date getUpdateDate() { 
	 	 return updateDate;
	 }
	 /** 余额 */
	 public void setUpdateDate(java.util.Date updateDate) {
	 	 this.updateDate = updateDate;
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

	 /** 编号 */
	 public Integer getId() { 
	 	 return id;
	 }
	 /** 编号 */
	 public void setId(Integer id) {
	 	 this.id = id;
	 }

	 /**  */
	 public java.util.Date getCreateDate() { 
	 	 return createDate;
	 }
	 /**  */
	 public void setCreateDate(java.util.Date createDate) {
	 	 this.createDate = createDate;
	 }
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", updateDate=" + updateDate + ", sex=" + sex + ", address=" + address + ", createDate=" + createDate + ", id=" + id + "]";
	}

}
