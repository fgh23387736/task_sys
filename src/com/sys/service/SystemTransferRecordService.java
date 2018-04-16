package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.SystemTransferRecordDao;
import com.sys.dao.UserDao;
import com.sys.entity.Admin;
import com.sys.entity.SystemTransferRecord;
import com.sys.entity.Task;
import com.sys.entity.User;


@Transactional
@Component(value="systemTransferRecordService")
public class SystemTransferRecordService {
	
	@Autowired
	private SystemTransferRecordDao systemTransferRecordDao;
	
	@Autowired
	private UserDao userDao;
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public SystemTransferRecordDao getSystemTransferRecordDao() {
		return systemTransferRecordDao;
	}

	public void setSystemTransferRecordDao(SystemTransferRecordDao systemTransferRecordDao) {
		this.systemTransferRecordDao = systemTransferRecordDao;
	}
	
	public List<Map<String, Object>> getSystemTransferRecordByKeys(String keys,List<SystemTransferRecord> systemTransferRecords){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"systemTransferRecordId",
					"transactionUser",
					"type",
					"money",
					"time"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (SystemTransferRecord systemTransferRecord : systemTransferRecords) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(systemTransferRecord,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(SystemTransferRecord systemTransferRecord,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		User theUser = null;
		Task theTask = null;
		Admin theAdmin = null;
		switch (str) {
		case "systemTransferRecordId":
			return systemTransferRecord.getSystemTransferRecordId();
		case "transactionUser":
			theUser = systemTransferRecord.getTransactionUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
				theMap.put("userName", theUser.getUserName());
				theMap.put("name", theUser.getName());
			}
			return theMap;
		case "type":
			return systemTransferRecord.getType();
		case "money":
			return systemTransferRecord.getMoney();
		case "time":
			return systemTransferRecord.getTime();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = systemTransferRecordDao.getAllCountByCriteria(criteria);
		List<SystemTransferRecord> systemTransferRecords = systemTransferRecordDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getSystemTransferRecordByKeys(keys,systemTransferRecords);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public SystemTransferRecord add(SystemTransferRecord systemTransferRecord) {
		User theUser = userDao.getById(systemTransferRecord.getTransactionUser().getUserId());
		if(systemTransferRecord.getType() == 0){
			theUser.setMoney(theUser.getMoney() + systemTransferRecord.getMoney());
		}else if(systemTransferRecord.getType() == 1){
			theUser.setMoney(theUser.getMoney() - systemTransferRecord.getMoney());
		}
		userDao.update(theUser);
		return systemTransferRecordDao.add(systemTransferRecord);
		
	}
	

	public Map<String, Object> getSystemTransferRecordByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = systemTransferRecordDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			SystemTransferRecord systemTransferRecord, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			SystemTransferRecord systemTransferRecord3 = systemTransferRecordDao.getById(integer);
			systemTransferRecord3 = getNewSystemTransferRecordByKeys(systemTransferRecord3,systemTransferRecord,keys);
			if(systemTransferRecord3 != null){
				systemTransferRecordDao.update(systemTransferRecord3);
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

	private SystemTransferRecord getNewSystemTransferRecordByKeys(SystemTransferRecord systemTransferRecord, SystemTransferRecord newSystemTransferRecord, String keys) {
		if(systemTransferRecord == null || newSystemTransferRecord == null || keys == null){
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
		
		return systemTransferRecord;
	}

	public SystemTransferRecord getById(Integer systemTransferRecordId) {
		// TODO 自动生成的方法存根
		return systemTransferRecordDao.getById(systemTransferRecordId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			SystemTransferRecord systemTransferRecord2 = systemTransferRecordDao.getById(integer);
			if(systemTransferRecord2 != null){
				if(loginAdmin != null){
					systemTransferRecordDao.delete(systemTransferRecord2);
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
		DetachedCriteria criteria = systemTransferRecordDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public int getAllMoney() {
		DetachedCriteria criteria = systemTransferRecordDao.getCriteriaForAll();
		List<SystemTransferRecord> systemTransferRecords = systemTransferRecordDao.getDataByCriteria(null, null, criteria);
		int sum = 0;
		for (SystemTransferRecord systemTransferRecord : systemTransferRecords) {
			if(systemTransferRecord.getType() == 0){
				sum += systemTransferRecord.getMoney();
			}else if(systemTransferRecord.getType() == 1){
				sum -= systemTransferRecord.getMoney();
			}
		}
		return sum;
	}

	public Map<String, Object> getSystemTransferRecordByUser(String keys,
			Integer page, Integer pageSize,
			SystemTransferRecord systemTransferRecord) {
		DetachedCriteria criteria = systemTransferRecordDao.getCriteriaByUser(systemTransferRecord);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
