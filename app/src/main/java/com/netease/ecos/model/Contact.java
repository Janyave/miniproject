package com.netease.ecos.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.netease.ecos.activity.MyApplication;

/**   
 * @Title: Contact.java 
 * @Description: 
 * @author enlizhang   
 * @date 2015年7月29日 上午9:34:16 
 */
@DatabaseTable(tableName = "contact")
public class Contact {

	/*** 联系人云信accid */
	@DatabaseField(id = true)
	public String contactAccid;
	
	/*** 联系人用户id */
	@DatabaseField
	public String contactUserId;
	
	/*** 联系人用户昵称 */
	@DatabaseField
	public String contactNickName;
	
	/*** 聊天信息id */
	@DatabaseField
	public String messgeId;
	
	/** 最近一条聊天信息*/
	@DatabaseField
	public String messageContent;
	
	/** 最近一条聊天信息发送者id */
	@DatabaseField
	public String fromAccount;
	
	/** 该条信息聊天时间 */
	@DatabaseField
	public long time;
	
	/*** 与该联系人会话的信息未读数 */
	@DatabaseField
	public int unreadedNum;
	
	/***
	 * 判断最近一条信息是否来自自己
	 * @return
	 */
	public boolean isFrmeMySelf(){
		String myAccid = AccountDataService.getSingleAccountDataService(MyApplication.getContext()).getUserAccId();
		return contactAccid.equals(myAccid);
	}

	@Override
	public String toString() {
		return "Contact [contactAccid=" + contactAccid + ", contactUserId="
				+ contactUserId + ", contactNickName=" + contactNickName
				+ ", messgeId=" + messgeId + ", messageContent="
				+ messageContent + ", fromAccount=" + fromAccount + ", time="
				+ time + ", unreadedNum=" + unreadedNum + "]";
	}
	
	
	
}

