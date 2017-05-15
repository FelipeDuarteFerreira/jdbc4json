package com.sooncode.soonjdbc.entity;

import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class ChineseDog implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /**  */ 
	 private Integer id; 
	 /**  */
	 private String dogName ;
	 /**  */
	 private Integer manId ;

	 /**  */
	 public Integer getId() { 
	 	 return id;
	 }
	 /**  */
	 public void setId(Integer id) {
	 	 this.id = id;
	 }

	 /**  */
	 public String getDogName() { 
	 	 return dogName;
	 }
	 /**  */
	 public void setDogName(String dogName) {
	 	 this.dogName = dogName;
	 }

	 /**  */
	 public Integer getManId() { 
	 	 return manId;
	 }
	 /**  */
	 public void setManId(Integer manId) {
	 	 this.manId = manId;
	 }

}

