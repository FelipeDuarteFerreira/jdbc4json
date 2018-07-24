package com.sooncode.soonjdbc.entity;
/**
* 
* SYSTEM_FRIEND
* SystemFriend
* @author SOONJDBC CODE GENERATOR 
* 
*/ 
public class SystemFriend { 
	 private Integer friendId; 
	 private Integer friendUserId ;
	 private Integer meUserId ;

	 public Integer getFriendUserId() { 
	 	 return friendUserId;
	 }
	 public void setFriendUserId(Integer friendUserId) {
	 	 this.friendUserId = friendUserId;
	 }

	 public Integer getMeUserId() { 
	 	 return meUserId;
	 }
	 public void setMeUserId(Integer meUserId) {
	 	 this.meUserId = meUserId;
	 }

	 public Integer getFriendId() { 
	 	 return friendId;
	 }
	 public void setFriendId(Integer friendId) {
	 	 this.friendId = friendId;
	 }

}
