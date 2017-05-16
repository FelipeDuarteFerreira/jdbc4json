
package com.sooncode.soonjdbc.entity;
import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class SooncodeSchool implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 学习编号 */ 
	 private Integer schoolId; 
	 /** 校长编号 */
	 private Integer principalId ;
	 /** 学校名称 */
	 private String schoolName ;

	 /** 校长编号 */
	 public Integer getPrincipalId() { 
	 	 return principalId;
	 }
	 /** 校长编号 */
	 public void setPrincipalId(Integer principalId) {
	 	 this.principalId = principalId;
	 }

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

}
