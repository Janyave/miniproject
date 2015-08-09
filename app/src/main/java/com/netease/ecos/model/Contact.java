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

	/*** 本人imid和对方imid */
	@DatabaseField(id = true)
	private String MyImIdPlusContactImiId;

	/*** 联系人云信((聊天对象))accid */
	@DatabaseField
	public String contactAccid;
	
	/*** 联系人(聊天对象)用户id */
	@DatabaseField
	public String contactUserId;
	
	/*** 联系人(聊天对象)用户昵称 */
	@DatabaseField
	public String contactNickName;

	/*** 联系人(聊天对象)头像url */
	@DatabaseField
	public String avatarUrl;
	
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
		return "Contact{" +
				"MyImIdPlusContactImiId='" + MyImIdPlusContactImiId + '\'' +
				", contactAccid='" + contactAccid + '\'' +
				", contactUserId='" + contactUserId + '\'' +
				", contactNickName='" + contactNickName + '\'' +
				", messgeId='" + messgeId + '\'' +
				", messageContent='" + messageContent + '\'' +
				", avatarUrl='" + avatarUrl + '\'' +
				", messgeId='" + messgeId + '\'' +
				", messageContent='" + messageContent + '\'' +
				", fromAccount='" + fromAccount + '\'' +
				", time=" + time +
				", unreadedNum=" + unreadedNum +
				'}';
	}

	public void setId(String myImId,String contactImId){

		MyImIdPlusContactImiId = myImId + contactImId;
	}


	public Contact(){
	}

}

