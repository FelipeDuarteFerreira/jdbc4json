package com.sooncode.soonjdbc.entity;
import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class School implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /** 学习编号 */ 
	 private Integer schoolId; 
	 /** 学校名称 */
	 private String schoolName ;

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
	@Override
	public String toString() {
		return "School [schoolId=" + schoolId + ", schoolName=" + schoolName + "]";
	}

}

