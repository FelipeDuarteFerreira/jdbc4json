package com.sooncode.soonjdbc.entity;

import java.io.Serializable;
/**
*用户
* @author hechen 
* 
*/ 
public class SystemUser implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 编号 */ 
	 private Integer id; 
	 /** 姓名 */
	 private String name ;
	 /**  */
	 private Integer age ;
	 /**  */
	 private java.math.BigDecimal numer ;
	 /** 余额 */
	 private java.util.Date updateDate ;
	 /**  */
	 private String sex ;
	 /**  */
	 private String address ;
	 /**  */
	 private Double doog ;
	 /**  */
	 private java.util.Date createDate ;
	 /**  */
	 private String type ;

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

	 /**  */
	 public java.math.BigDecimal getNumer() { 
	 	 return numer;
	 }
	 /**  */
	 public void setNumer(java.math.BigDecimal numer) {
	 	 this.numer = numer;
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
	 public Double getDoog() { 
	 	 return doog;
	 }
	 /**  */
	 public void setDoog(Double doog) {
	 	 this.doog = doog;
	 }

	 /**  */
	 public java.util.Date getCreateDate() { 
	 	 return createDate;
	 }
	 /**  */
	 public void setCreateDate(java.util.Date createDate) {
	 	 this.createDate = createDate;
	 }

	 /**  */
	 public String getType() { 
	 	 return type;
	 }
	 /**  */
	 public void setType(String type) {
	 	 this.type = type;
	 }
	@Override
	public String toString() {
		return "SystemUser [id=" + id + ", name=" + name + ", age=" + age + ", numer=" + numer + ", updateDate=" + updateDate + ", sex=" + sex + ", address=" + address + ", doog=" + doog + ", createDate=" + createDate + ", type=" + type + "]";
	}

}
