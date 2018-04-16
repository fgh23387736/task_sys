package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.PrivateMessageDao;
import com.sys.entity.Admin;
import com.sys.entity.PrivateMessage;
import com.sys.entity.Task;
import com.sys.entity.User;
import com.sys.webSocket.WebSocket;


@Transactional
@Component(value="privateMessageService")
public class PrivateMessageService {
	
	@Autowired
	private PrivateMessageDao privateMessageDao;
	
	
	public PrivateMessageDao getPrivateMessageDao() {
		return privateMessageDao;
	}

	public void setPrivateMessageDao(PrivateMessageDao privateMessageDao) {
		this.privateMessageDao = privateMessageDao;
	}
	
	public List<Map<String, Object>> getPrivateMessageByKeys(String keys,List<PrivateMessage> privateMessages){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"privateMessageId",
					"title",
					"content",
					"time",
					"user",
					"admin"		
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (PrivateMessage privateMessage : privateMessages) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(privateMessage,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(PrivateMessage privateMessage,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		User theUser = null;
		Task theTask = null;
		Admin theAdmin = null;
		switch (str) {
		case "privateMessageId":
			return privateMessage.getPrivateMessageId();
		case "title":
			return privateMessage.getTitle();
		case "content":
			return privateMessage.getContent();
		case "time":
			return privateMessage.getTime();
		case "user":
			theUser = privateMessage.getUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
			}
			return theMap;
		case "admin":
			theAdmin = privateMessage.getAdmin();
			if(theAdmin != null){
				theMap = new HashMap<String, Object>();
				theMap.put("adminId", theAdmin.getAdminId());
				theMap.put("name", theAdmin.getName());
			}
			return theMap;
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = privateMessageDao.getAllCountByCriteria(criteria);
		List<PrivateMessage> privateMessages = privateMessageDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getPrivateMessageByKeys(keys,privateMessages);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public PrivateMessage add(PrivateMessage privateMessage,Admin loginAdmin) {
		privateMessage = privateMessageDao.add(privateMessage);
		if(privateMessage.getPrivateMessageId() != null){
			Map<String, Object> contentMap = new HashMap<>();
			contentMap.put("privateMessageId", privateMessage.getPrivateMessageId());
			contentMap.put("time", privateMessage.getTime());
			contentMap.put("title", privateMessage.getTitle());
			WebSocket.sendByWebSocket(null, "newPrivateMessage", loginAdmin, null, contentMap,null);
		}
		return privateMessage;
		
	}
	

	public Map<String, Object> getPrivateMessageByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = privateMessageDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			PrivateMessage privateMessage, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			PrivateMessage privateMessage3 = privateMessageDao.getById(integer);
			privateMessage3 = getNewPrivateMessageByKeys(privateMessage3,privateMessage,keys);
			if(privateMessage3 != null){
				privateMessageDao.update(privateMessage3);
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

	private PrivateMessage getNewPrivateMessageByKeys(PrivateMessage privateMessage, PrivateMessage newPrivateMessage, String keys) {
		if(privateMessage == null || newPrivateMessage == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "title":
					privateMessage.setTitle(newPrivateMessage.getTitle());
					break;
				case "content":
					privateMessage.setContent(newPrivateMessage.getContent());
					break;
				default:
					
			}
		}
		
		return privateMessage;
	}

	public PrivateMessage getById(Integer privateMessageId) {
		// TODO 自动生成的方法存根
		return privateMessageDao.getById(privateMessageId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			PrivateMessage privateMessage2 = privateMessageDao.getById(integer);
			if(privateMessage2 != null){
				if(loginAdmin != null){
					privateMessageDao.delete(privateMessage2);
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
		DetachedCriteria criteria = privateMessageDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getPrivateMessageByTitleAndUser(String keys,
			Integer page, Integer pageSize, PrivateMessage privateMessage) {
		DetachedCriteria criteria = privateMessageDao.getCriteriaByTitleAndUse(privateMessage);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
