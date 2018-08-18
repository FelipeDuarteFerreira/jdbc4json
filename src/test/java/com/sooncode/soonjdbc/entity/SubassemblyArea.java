package com.sooncode.soonjdbc.entity;
/**
* 全国地区表
* SUBASSEMBLY_AREA
* SubassemblyArea
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SubassemblyArea { 

 	 /** 地区编码 */ 
	 private String areaCode; 

	 /** 电话区号 */
	 private String phoneCode ;

	 /** 上级编码 */
	 private String parentCode ;

	 /** 地区级别 */
	 private String areaRank ;

	 /** 地区名称 */
	 private String areaName ;

	 /** 邮政编码 */
	 private String postalCode ;

	 /** 地区类型 */
	 private String areaType ;

	 /** 是否为末级 */
	 private String isLast ;

	 /** 全名称 */
	 private String allName ;

	 /** 地区简称 */
	 private String areaAbbreviation ;

	 /** 电话区号 */
	 public String getPhoneCode() { 
	 	 return phoneCode;
	 }

	 /** 电话区号 */
	 public void setPhoneCode(String phoneCode) {
	 	 this.phoneCode = phoneCode;
	 }

	 /** 上级编码 */
	 public String getParentCode() { 
	 	 return parentCode;
	 }

	 /** 上级编码 */
	 public void setParentCode(String parentCode) {
	 	 this.parentCode = parentCode;
	 }

	 /** 地区级别 */
	 public String getAreaRank() { 
	 	 return areaRank;
	 }

	 /** 地区级别 */
	 public void setAreaRank(String areaRank) {
	 	 this.areaRank = areaRank;
	 }

	 /** 地区名称 */
	 public String getAreaName() { 
	 	 return areaName;
	 }

	 /** 地区名称 */
	 public void setAreaName(String areaName) {
	 	 this.areaName = areaName;
	 }

	 /** 邮政编码 */
	 public String getPostalCode() { 
	 	 return postalCode;
	 }

	 /** 邮政编码 */
	 public void setPostalCode(String postalCode) {
	 	 this.postalCode = postalCode;
	 }

	 /** 地区类型 */
	 public String getAreaType() { 
	 	 return areaType;
	 }

	 /** 地区类型 */
	 public void setAreaType(String areaType) {
	 	 this.areaType = areaType;
	 }

	 /** 地区编码 */
	 public String getAreaCode() { 
	 	 return areaCode;
	 }

	 /** 地区编码 */
	 public void setAreaCode(String areaCode) {
	 	 this.areaCode = areaCode;
	 }

	 /** 是否为末级 */
	 public String getIsLast() { 
	 	 return isLast;
	 }

	 /** 是否为末级 */
	 public void setIsLast(String isLast) {
	 	 this.isLast = isLast;
	 }

	 /** 全名称 */
	 public String getAllName() { 
	 	 return allName;
	 }

	 /** 全名称 */
	 public void setAllName(String allName) {
	 	 this.allName = allName;
	 }

	 /** 地区简称 */
	 public String getAreaAbbreviation() { 
	 	 return areaAbbreviation;
	 }

	 /** 地区简称 */
	 public void setAreaAbbreviation(String areaAbbreviation) {
	 	 this.areaAbbreviation = areaAbbreviation;
	 }

}
