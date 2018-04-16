package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.InternalTransferRecordDao;
import com.sys.dao.UserDao;
import com.sys.entity.Admin;
import com.sys.entity.InternalTransferRecord;
import com.sys.entity.Task;
import com.sys.entity.User;


@Transactional
@Component(value="internalTransferRecordService")
public class InternalTransferRecordService {
	
	@Autowired
	private InternalTransferRecordDao internalTransferRecordDao;
	
	@Autowired
	private UserDao userDao;
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public InternalTransferRecordDao getInternalTransferRecordDao() {
		return internalTransferRecordDao;
	}

	public void setInternalTransferRecordDao(InternalTransferRecordDao internalTransferRecordDao) {
		this.internalTransferRecordDao = internalTransferRecordDao;
	}
	
	public List<Map<String, Object>> getInternalTransferRecordByKeys(String keys,List<InternalTransferRecord> internalTransferRecords){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"internalTransferRecordId",
					"task",
					"transactionUser",
					"reason",
					"type",
					"money",
					"time"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (InternalTransferRecord internalTransferRecord : internalTransferRecords) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(internalTransferRecord,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(InternalTransferRecord internalTransferRecord,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		User theUser = null;
		Task theTask = null;
		Admin theAdmin = null;
		switch (str) {
		case "internalTransferRecordId":
			return internalTransferRecord.getInternalTransferRecordId();
		case "task":
			theTask = internalTransferRecord.getTask();
			if(theTask != null){
				theMap = new HashMap<String, Object>();
				theMap.put("taskId", theTask.getTaskId());
			}
			return theMap;
		case "transactionUser":
			theUser = internalTransferRecord.getTransactionUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
			}
			return theMap;
		case "reason":
			return internalTransferRecord.getReason();
		case "type":
			return internalTransferRecord.getType();
		case "money":
			return internalTransferRecord.getMoney();
		case "time":
			return internalTransferRecord.getTime();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = internalTransferRecordDao.getAllCountByCriteria(criteria);
		List<InternalTransferRecord> internalTransferRecords = internalTransferRecordDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getInternalTransferRecordByKeys(keys,internalTransferRecords);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public InternalTransferRecord add(InternalTransferRecord internalTransferRecord) {
		internalTransferRecord = internalTransferRecordDao.add(internalTransferRecord);
		if(internalTransferRecord.getInternalTransferRecordId() != null){
			internalTransferRecord = internalTransferRecordDao.getById(internalTransferRecord.getInternalTransferRecordId());
			User theUser = userDao.getById(internalTransferRecord.getTransactionUser().getUserId());
			if(internalTransferRecord.getType() == 0){
				theUser.setMoney(theUser.getMoney() + internalTransferRecord.getMoney());
			}else if(internalTransferRecord.getType() == 1){
				theUser.setMoney(theUser.getMoney() - internalTransferRecord.getMoney());
			}
			userDao.update(theUser);
		}
		return internalTransferRecord;
		
	}
	

	public Map<String, Object> getInternalTransferRecordByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = internalTransferRecordDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			InternalTransferRecord internalTransferRecord, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			InternalTransferRecord internalTransferRecord3 = internalTransferRecordDao.getById(integer);
			internalTransferRecord3 = getNewInternalTransferRecordByKeys(internalTransferRecord3,internalTransferRecord,keys);
			if(internalTransferRecord3 != null){
				internalTransferRecordDao.update(internalTransferRecord3);
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

	private InternalTransferRecord getNewInternalTransferRecordByKeys(InternalTransferRecord internalTransferRecord, InternalTransferRecord newInternalTransferRecord, String keys) {
		if(internalTransferRecord == null || newInternalTransferRecord == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				
				default:
					
			}
		}
		
		return internalTransferRecord;
	}

	public InternalTransferRecord getById(Integer internalTransferRecordId) {
		// TODO 自动生成的方法存根
		return internalTransferRecordDao.getById(internalTransferRecordId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			InternalTransferRecord internalTransferRecord2 = internalTransferRecordDao.getById(integer);
			if(internalTransferRecord2 != null){
				if(loginAdmin != null){
					internalTransferRecordDao.delete(internalTransferRecord2);
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
		DetachedCriteria criteria = internalTransferRecordDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
