
package com.sooncode.soonjdbc.entity;
import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class StudentTotal implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /**  */ 
	 private Integer id; 
	 /**  */
	 private Integer chinese ;
	 /**  */
	 private Integer total ;
	 /**  */
	 private Integer math ;

	 /**  */
	 public Integer getChinese() { 
	 	 return chinese;
	 }
	 /**  */
	 public void setChinese(Integer chinese) {
	 	 this.chinese = chinese;
	 }

	 /**  */
	 public Integer getId() { 
	 	 return id;
	 }
	 /**  */
	 public void setId(Integer id) {
	 	 this.id = id;
	 }

	 /**  */
	 public Integer getTotal() { 
	 	 return total;
	 }
	 /**  */
	 public void setTotal(Integer total) {
	 	 this.total = total;
	 }

	 /**  */
	 public Integer getMath() { 
	 	 return math;
	 }
	 /**  */
	 public void setMath(Integer math) {
	 	 this.math = math;
	 }

}
