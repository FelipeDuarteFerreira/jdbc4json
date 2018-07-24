package com.sooncode.soonjdbc.entity;
/**
* 
* SOONCODE_DICTIONARY
* SooncodeDictionary
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SooncodeDictionary { 

 	 /** 字段编号 */ 
	 private Integer dictionaryId; 

	 /** 创建时间 */
	 private java.util.Date createDate ;

	 /** 字典名称 */
	 private String dictionaryName ;

	 /** 字典代码 */
	 private String dictionaryCode ;

	 /** 数据状态 */
	 private Integer dictionaryState ;

	 /** 组编号 */
	 private Integer groupId ;

	 /** 创建时间 */
	 public java.util.Date getCreateDate() { 
	 	 return createDate;
	 }

	 /** 创建时间 */
	 public void setCreateDate(java.util.Date createDate) {
	 	 this.createDate = createDate;
	 }

	 /** 字典名称 */
	 public String getDictionaryName() { 
	 	 return dictionaryName;
	 }

	 /** 字典名称 */
	 public void setDictionaryName(String dictionaryName) {
	 	 this.dictionaryName = dictionaryName;
	 }

	 /** 字段编号 */
	 public Integer getDictionaryId() { 
	 	 return dictionaryId;
	 }

	 /** 字段编号 */
	 public void setDictionaryId(Integer dictionaryId) {
	 	 this.dictionaryId = dictionaryId;
	 }

	 /** 字典代码 */
	 public String getDictionaryCode() { 
	 	 return dictionaryCode;
	 }

	 /** 字典代码 */
	 public void setDictionaryCode(String dictionaryCode) {
	 	 this.dictionaryCode = dictionaryCode;
	 }

	 /** 数据状态 */
	 public Integer getDictionaryState() { 
	 	 return dictionaryState;
	 }

	 /** 数据状态 */
	 public void setDictionaryState(Integer dictionaryState) {
	 	 this.dictionaryState = dictionaryState;
	 }

	 /** 组编号 */
	 public Integer getGroupId() { 
	 	 return groupId;
	 }

	 /** 组编号 */
	 public void setGroupId(Integer groupId) {
	 	 this.groupId = groupId;
	 }

}
