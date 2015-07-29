package com.netease.ecos.database;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.netease.ecos.model.Contact;

import java.util.List;

/**   
 * @Title: ContactDBService.java 
 * @Description: 联系人列表操作类
 * @author enlizhang   
 * @date 2015年7月29日 上午10:29:58 
 */

public class ContactDBService {
	private static final String TAG = "联系人最近会话存储";
	
	private RuntimeExceptionDao<Contact, String> mContactDAO;
	
	public static ContactDBService singleContactDBService;
	private static Context mContext;
	
	private ContactDBService(Context context)
	{
		mContext = context;
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		mContactDAO = dbHelper.getContactDao();
	}
	

	/**
	 * 获取当前类实例
	 * @param context
	 * @return 当前类实例对象
	 */
	public static ContactDBService getInstance(Context context)
	{
		//若当前类实例不存在，则创建新类实例对象
		if(singleContactDBService == null)
		{
			singleContactDBService = new ContactDBService(context);
		}
		else //若当前类实例存在，但当前上下文对象已经产生变化，也重新创建类实例对象
			if( mContext == null)
			{
				singleContactDBService = new ContactDBService(context);
			}
		
		return singleContactDBService;
	}
	
	/***
	 * 添加联系人
	 */
	public void addContact(Contact contact){
		if(contact!=null)
			mContactDAO.createOrUpdate(contact);
		else
			Log.e(TAG, "addContact(Contact contact)" + "contact=null");
	}
	
	/***
	 * 获取会话列表
	 * @return
	 */
	public List<Contact> getContactList() {
		// TODO Auto-generated method stub
		return mContactDAO.queryForAll();
	}


	/***
	 * 根据会话id获取会话对象，若contactAccId为null或无与contactAccId对应的会话对象，则返回null
	 * @param contactAccId
	 * @return
	 */
	public Contact getContact(String contactAccId) {
		
		if(contactAccId == null || "".equals(contactAccId))
		{
			Log.e(TAG, "getContact(String contactAccId),contactAccId=null");
			return null;
		}
		
		Contact contact = new Contact();
		
		//以省名作为检索条件
		contact.contactAccid = contactAccId;
		
		List<Contact> contactList=mContactDAO.queryForMatchingArgs(contact);
		
		return contactList.size()>0?contactList.get(0):null;
	}
	
}

