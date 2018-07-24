
package com.sooncode.soonjdbc;
import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class SystemUser implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 用户编号 */ 
	 private String systemUserId; 
	 /** 用户组编号 */
	 private String userGroupId ;
	 /** 电话号码 */
	 private String telephone ;
	 /** 密码MD5 */
	 private String password ;
	 /** 角色编号 */
	 private String roleId ;
	 /** 性别 */
	 private String gender ;
	 /** 用户姓名 */
	 private String userName ;
	 /** 创建时间 */
	 private java.util.Date createTime ;
	 /** 更新时间 */
	 private java.util.Date updateTime ;
	 /** 用户邮件 */
	 private String email ;

	 /** 用户组编号 */
	 public String getUserGroupId() { 
	 	 return userGroupId;
	 }
	 /** 用户组编号 */
	 public void setUserGroupId(String userGroupId) {
	 	 this.userGroupId = userGroupId;
	 }

	 /** 电话号码 */
	 public String getTelephone() { 
	 	 return telephone;
	 }
	 /** 电话号码 */
	 public void setTelephone(String telephone) {
	 	 this.telephone = telephone;
	 }

	 /** 密码MD5 */
	 public String getPassword() { 
	 	 return password;
	 }
	 /** 密码MD5 */
	 public void setPassword(String password) {
	 	 this.password = password;
	 }

	 /** 角色编号 */
	 public String getRoleId() { 
	 	 return roleId;
	 }
	 /** 角色编号 */
	 public void setRoleId(String roleId) {
	 	 this.roleId = roleId;
	 }

	 /** 性别 */
	 public String getGender() { 
	 	 return gender;
	 }
	 /** 性别 */
	 public void setGender(String gender) {
	 	 this.gender = gender;
	 }

	 /** 用户姓名 */
	 public String getUserName() { 
	 	 return userName;
	 }
	 /** 用户姓名 */
	 public void setUserName(String userName) {
	 	 this.userName = userName;
	 }

	 /** 创建时间 */
	 public java.util.Date getCreateTime() { 
	 	 return createTime;
	 }
	 /** 创建时间 */
	 public void setCreateTime(java.util.Date createTime) {
	 	 this.createTime = createTime;
	 }

	 /** 更新时间 */
	 public java.util.Date getUpdateTime() { 
	 	 return updateTime;
	 }
	 /** 更新时间 */
	 public void setUpdateTime(java.util.Date updateTime) {
	 	 this.updateTime = updateTime;
	 }

	 /** 用户邮件 */
	 public String getEmail() { 
	 	 return email;
	 }
	 /** 用户邮件 */
	 public void setEmail(String email) {
	 	 this.email = email;
	 }

	 /** 用户编号 */
	 public String getSystemUserId() { 
	 	 return systemUserId;
	 }
	 /** 用户编号 */
	 public void setSystemUserId(String systemUserId) {
	 	 this.systemUserId = systemUserId;
	 }

}
