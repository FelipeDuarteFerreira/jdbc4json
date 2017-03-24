package com.sooncode.soonjdbc.entity;

import java.io.Serializable;
/**
*
* @author hechen 
* 
*/ 
public class Friend implements Serializable{ 
	 private static final long serialVersionUID = 1L;
	 /**  */ 
	 private Integer friendId; 
	 /**  */
	 private Integer friendUserId ;
	 /**  */
	 private Integer meUserId ;

	 /**  */
	 public Integer getFriendUserId() { 
	 	 return friendUserId;
	 }
	 /**  */
	 public void setFriendUserId(Integer friendUserId) {
	 	 this.friendUserId = friendUserId;
	 }

	 /**  */
	 public Integer getFriendId() { 
	 	 return friendId;
	 }
	 /**  */
	 public void setFriendId(Integer friendId) {
	 	 this.friendId = friendId;
	 }

	 /**  */
	 public Integer getMeUserId() { 
	 	 return meUserId;
	 }
	 /**  */
	 public void setMeUserId(Integer meUserId) {
	 	 this.meUserId = meUserId;
	 }
	@Override
	public String toString() {
		return "Friend [friendId=" + friendId + ", friendUserId=" + friendUserId + ", meUserId=" + meUserId + "]";
	}

}

