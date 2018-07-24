package com.sooncode.soonjdbc.entity;
/**
* 
* STUDENT_TOTAL
* StudentTotal
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class StudentTotal { 
	 private Integer id; 
	 private Integer total ;
	 private Integer chinese ;
	 private Integer math ;

	 public Integer getTotal() { 
	 	 return total;
	 }
	 public void setTotal(Integer total) {
	 	 this.total = total;
	 }

	 public Integer getChinese() { 
	 	 return chinese;
	 }
	 public void setChinese(Integer chinese) {
	 	 this.chinese = chinese;
	 }

	 public Integer getId() { 
	 	 return id;
	 }
	 public void setId(Integer id) {
	 	 this.id = id;
	 }

	 public Integer getMath() { 
	 	 return math;
	 }
	 public void setMath(Integer math) {
	 	 this.math = math;
	 }

}
