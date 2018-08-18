package com.sooncode.soonjdbc.entity;
/**
* 组件模块_数据字典分组
* SOONCODE_GROUPS
* SooncodeGroups
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SooncodeGroups { 

 	 /** 数据字典分组编号 */ 
	 private Integer groupId; 

	 /** 组名称 */
	 private String groupName ;

	 /** 创建时间 */
	 private java.util.Date createDate ;

	 /** 组代码 */
	 private String groupCode ;

	 /** 组状态 */
	 private Integer groupState ;

	 /** 组名称 */
	 public String getGroupName() { 
	 	 return groupName;
	 }

	 /** 组名称 */
	 public void setGroupName(String groupName) {
	 	 this.groupName = groupName;
	 }

	 /** 创建时间 */
	 public java.util.Date getCreateDate() { 
	 	 return createDate;
	 }

	 /** 创建时间 */
	 public void setCreateDate(java.util.Date createDate) {
	 	 this.createDate = createDate;
	 }

	 /** 组代码 */
	 public String getGroupCode() { 
	 	 return groupCode;
	 }

	 /** 组代码 */
	 public void setGroupCode(String groupCode) {
	 	 this.groupCode = groupCode;
	 }

	 /** 组状态 */
	 public Integer getGroupState() { 
	 	 return groupState;
	 }

	 /** 组状态 */
	 public void setGroupState(Integer groupState) {
	 	 this.groupState = groupState;
	 }

	 /** 数据字典分组编号 */
	 public Integer getGroupId() { 
	 	 return groupId;
	 }

	 /** 数据字典分组编号 */
	 public void setGroupId(Integer groupId) {
	 	 this.groupId = groupId;
	 }

}
