package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.SuspendedRecordDao;
import com.sys.dao.UserDao;
import com.sys.entity.Admin;
import com.sys.entity.SuspendedRecord;
import com.sys.entity.Task;
import com.sys.entity.User;


@Transactional
@Component(value="suspendedRecordService")
public class SuspendedRecordService {
	
	@Autowired
	private SuspendedRecordDao suspendedRecordDao;
	
	@Autowired
	private UserDao userDao;
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public SuspendedRecordDao getSuspendedRecordDao() {
		return suspendedRecordDao;
	}

	public void setSuspendedRecordDao(SuspendedRecordDao suspendedRecordDao) {
		this.suspendedRecordDao = suspendedRecordDao;
	}
	
	public List<Map<String, Object>> getSuspendedRecordByKeys(String keys,List<SuspendedRecord> suspendedRecords){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"suspendedRecordId",
					"reason",
					"suspendedDeadline",
					"time",
					"user",
					"admin"		
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (SuspendedRecord suspendedRecord : suspendedRecords) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(suspendedRecord,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(SuspendedRecord suspendedRecord,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		User theUser = null;
		Task theTask = null;
		Admin theAdmin = null;
		switch (str) {
		case "suspendedRecordId":
			return suspendedRecord.getSuspendedRecordId();
		case "reason":
			return suspendedRecord.getReason();
		case "suspendedDeadline":
			return suspendedRecord.getSuspendedDeadline();
		case "admin":
			theAdmin = suspendedRecord.getAdmin();
			if(theAdmin != null){
				theMap = new HashMap<String, Object>();
				theMap.put("adminId", theAdmin.getAdminId());
				theMap.put("name", theAdmin.getName());
			}
			return theMap;
		case "user":
			theUser = suspendedRecord.getUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
			}
			return theMap;
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = suspendedRecordDao.getAllCountByCriteria(criteria);
		List<SuspendedRecord> suspendedRecords = suspendedRecordDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getSuspendedRecordByKeys(keys,suspendedRecords);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public SuspendedRecord add(SuspendedRecord suspendedRecord) {
		System.out.println("time:"+suspendedRecord.getTime());
		suspendedRecord = suspendedRecordDao.add(suspendedRecord);
		if(suspendedRecord.getSuspendedRecordId() != null){
			User theUser = userDao.getById(suspendedRecord.getUser().getUserId());
			theUser.setSuspenedDeadline(suspendedRecord.getSuspendedDeadline());
			userDao.update(theUser);
		}
		return suspendedRecord;
		
	}
	

	public Map<String, Object> getSuspendedRecordByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = suspendedRecordDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			SuspendedRecord suspendedRecord, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			SuspendedRecord suspendedRecord3 = suspendedRecordDao.getById(integer);
			suspendedRecord3 = getNewSuspendedRecordByKeys(suspendedRecord3,suspendedRecord,keys);
			if(suspendedRecord3 != null){
				suspendedRecordDao.update(suspendedRecord3);
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

	private SuspendedRecord getNewSuspendedRecordByKeys(SuspendedRecord suspendedRecord, SuspendedRecord newSuspendedRecord, String keys) {
		if(suspendedRecord == null || newSuspendedRecord == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "reason":
					suspendedRecord.setReason(newSuspendedRecord.getReason());
					break;
				case "suspendedDeadline":
					suspendedRecord.setSuspendedDeadline(newSuspendedRecord.getSuspendedDeadline());
					break;
				default:
					
			}
		}
		
		return suspendedRecord;
	}

	public SuspendedRecord getById(Integer suspendedRecordId) {
		// TODO 自动生成的方法存根
		return suspendedRecordDao.getById(suspendedRecordId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			SuspendedRecord suspendedRecord2 = suspendedRecordDao.getById(integer);
			if(suspendedRecord2 != null){
				if(loginAdmin != null){
					suspendedRecordDao.delete(suspendedRecord2);
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
		DetachedCriteria criteria = suspendedRecordDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
