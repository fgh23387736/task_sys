package com.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.ChatRecordDao;
import com.sys.dao.UserDao;
import com.sys.entity.Admin;
import com.sys.entity.InternalTransferRecord;
import com.sys.entity.ChatRecord;
import com.sys.entity.User;
import com.sys.webSocket.WebSocket;


@Transactional
@Component(value="chatRecordService")
public class ChatRecordService {
	
	@Autowired
	private ChatRecordDao chatRecordDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private InternalTransferRecordService internalTransferRecordService;
	
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public InternalTransferRecordService getInternalTransferRecordService() {
		return internalTransferRecordService;
	}

	public void setInternalTransferRecordService(
			InternalTransferRecordService internalTransferRecordService) {
		this.internalTransferRecordService = internalTransferRecordService;
	}

	public ChatRecordDao getChatRecordDao() {
		return chatRecordDao;
	}

	public void setChatRecordDao(ChatRecordDao chatRecordDao) {
		this.chatRecordDao = chatRecordDao;
	}
	
	public List<Map<String, Object>> getChatRecordByKeys(String keys,List<ChatRecord> chatRecords){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"chatRecordId",
					"receiveUser"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (ChatRecord chatRecord : chatRecords) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(chatRecord,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(ChatRecord chatRecord,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		User theUser = null;
		switch (str) {
		case "chatRecordId":
			return chatRecord.getChatRecordId();
		case "receiveUser":
			theUser = chatRecord.getReceiveUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				theMap.put("phone", theUser.getPhone());
			}
			return theMap;
		
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = chatRecordDao.getAllCountByCriteria(criteria);
		List<ChatRecord> chatRecords = chatRecordDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getChatRecordByKeys(keys,chatRecords);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public ChatRecord add(ChatRecord chatRecord,User loginUser) {
		chatRecord = chatRecordDao.add(chatRecord);
		if(chatRecord.getChatRecordId() != null){
			
			
			
		}
		return chatRecord;
		
	}
	

	public Map<String, Object> getChatRecordByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = chatRecordDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			ChatRecord chatRecord, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			ChatRecord chatRecord3 = chatRecordDao.getById(integer);
			chatRecord3 = getNewChatRecordByKeys(chatRecord3,chatRecord,keys);
			if(chatRecord3 != null){
				chatRecordDao.update(chatRecord3);
			}else{
				map.put("code", 400);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据修改失败;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据修改失败;");
				}
			}
		}
		
		map.put("result", theMap);
		return map;
	}

	private ChatRecord getNewChatRecordByKeys(ChatRecord chatRecord, ChatRecord newChatRecord, String keys) {
		if(chatRecord == null || newChatRecord == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
//				case "name":
//					chatRecord.setName(newChatRecord.getName());
//					break;
				
				default:
					
			}
		}
		
		return chatRecord;
	}

	public ChatRecord getById(Integer chatRecordId) {
		// TODO 自动生成的方法存根
		return chatRecordDao.getById(chatRecordId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			ChatRecord chatRecord2 = chatRecordDao.getById(integer);
			if(chatRecord2 != null){
				if(loginAdmin != null){
					chatRecordDao.delete(chatRecord2);
				}else{
					map.put("code", 401);
					if(theMap == null){
						theMap = new HashMap<String, Object>();
						theMap.put("error", "id为"+integer+"的数据删除失败:您不具有权限;");
					}else{
						theMap.put("error",theMap.get("error")+"id为"+integer+"的数据删除失败:您不具有权限;");
					}
				}
			}else{
				map.put("code", 404);
				if(theMap == null){
					theMap = new HashMap<String, Object>();
					theMap.put("error", "id为"+integer+"的数据删除失败：数据不存在;");
				}else{
					theMap.put("error",theMap.get("error")+"id为"+integer+"的数据删除失败：数据不存在;");
				}
			}
		}
		
		map.put("result", theMap);
		return map;
	}

	public Map<String, Object> getAll(String keys, Integer page,
			Integer pageSize) {
		DetachedCriteria criteria = chatRecordDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public int getAllChatRecordNumber() {
		// TODO Auto-generated method stub
		return chatRecordDao.getAllCountByCriteria(chatRecordDao.getCriteriaForAll());
	}


	public void update(ChatRecord chatRecord) {
		chatRecordDao.update(chatRecord);
		
	}

	public List<User> getChatUserListByUser(User user) {
		DetachedCriteria criteria = chatRecordDao.getCriteriaForChatUserListByUser(user);
		List<ChatRecord> chatRecords = chatRecordDao.getDataByCriteria(null, null, criteria);
		
		List<User> userLists = new ArrayList<User>();
		List<Integer> userIdLists = new ArrayList<Integer>();
		for (ChatRecord chatRecord : chatRecords) {
			User theUser = null;
			if(user.getUserId() == chatRecord.getSendUser().getUserId()){
				theUser = chatRecord.getReceiveUser();
			}else{
				theUser = chatRecord.getSendUser();
			}
			
			if(theUser != null){
				if(!userIdLists.contains(theUser.getUserId())){
					userIdLists.add(theUser.getUserId());
					userLists.add(theUser);
				}
			}
			
		}
		return userLists;
	}



	

}
