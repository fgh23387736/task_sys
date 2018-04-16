package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.EnvaluateDao;
import com.sys.dao.TaskDao;
import com.sys.dao.UserDao;
import com.sys.entity.Admin;
import com.sys.entity.Envaluate;
import com.sys.entity.Task;
import com.sys.entity.User;


@Transactional
@Component(value="envaluateService")
public class EnvaluateService {
	
	@Autowired
	private EnvaluateDao envaluateDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TaskDao taskDao;
	
	public EnvaluateDao getEnvaluateDao() {
		return envaluateDao;
	}

	public void setEnvaluateDao(EnvaluateDao envaluateDao) {
		this.envaluateDao = envaluateDao;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public List<Map<String, Object>> getEnvaluateByKeys(String keys,List<Envaluate> envaluates){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"envaluateId",
					"task",
					"beEnvaluateUser",
					"content",
					"type",
					"envaluateUser",
					"time"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Envaluate envaluate : envaluates) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(envaluate,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Envaluate envaluate,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		User theUser = null;
		Task theTask = null;
		Admin theAdmin = null;
		switch (str) {
		case "envaluateId":
			return envaluate.getEnvaluateId();
		case "task":
			theTask = envaluate.getTask();
			if(theTask != null){
				theMap = new HashMap<String, Object>();
				theMap.put("taskId", theTask.getTaskId());
			}
			return theMap;
		case "beEnvaluateUser":
			theUser = envaluate.getBeEnvaluatedUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
			}
			return theMap;
		case "content":
			return envaluate.getContent();
		case "type":
			return envaluate.getType();
		case "envaluateUser":
			theUser = envaluate.getEnvaluateUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
			}
			return theMap;
		case "time":
			return envaluate.getTime();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = envaluateDao.getAllCountByCriteria(criteria);
		List<Envaluate> envaluates = envaluateDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getEnvaluateByKeys(keys,envaluates);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Envaluate add(Envaluate envaluate,User loginUser) {
		User beEnvaluatedUser = userDao.getById(envaluate.getBeEnvaluatedUser().getUserId());
		if(envaluate.getType() == 0){
			beEnvaluatedUser.setGoodEnvaluateNumber(beEnvaluatedUser.getGoodEnvaluateNumber() + 1);
		}else if(envaluate.getType() == 1){
			beEnvaluatedUser.setMiddleEnvaluateNumber(beEnvaluatedUser.getMiddleEnvaluateNumber() + 1);
		}else if(envaluate.getType() == 2){
			beEnvaluatedUser.setBadEnvaluateNumber(beEnvaluatedUser.getBadEnvaluateNumber() + 1);;
		}
		userDao.update(beEnvaluatedUser);
		
		Task theTask = taskDao.getById(envaluate.getTask().getTaskId());
		if(theTask.getReleaseUser().getUserId() == loginUser.getUserId()){
			theTask.setReleaseIsEnvaluate(true);
		}else if(theTask.getReceiveUser().getUserId() == loginUser.getUserId()){
			theTask.setReceiveIsEnvaluate(true);;
		}
		taskDao.update(theTask);
		return envaluateDao.add(envaluate);
		
	}
	

	public Map<String, Object> getEnvaluateByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = envaluateDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Envaluate envaluate, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			Envaluate envaluate3 = envaluateDao.getById(integer);
			envaluate3 = getNewEnvaluateByKeys(envaluate3,envaluate,keys);
			if(envaluate3 != null){
				envaluateDao.update(envaluate3);
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

	private Envaluate getNewEnvaluateByKeys(Envaluate envaluate, Envaluate newEnvaluate, String keys) {
		if(envaluate == null || newEnvaluate == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		User beEnvaluatedUser = null;
		for (String key : keysArrStrings) {
			switch (key) {
				case "type":
					beEnvaluatedUser = envaluate.getBeEnvaluatedUser();
					if(envaluate.getType() == 0){
						beEnvaluatedUser.setGoodEnvaluateNumber(beEnvaluatedUser.getGoodEnvaluateNumber() - 1);
					}else if(envaluate.getType() == 1){
						beEnvaluatedUser.setMiddleEnvaluateNumber(beEnvaluatedUser.getMiddleEnvaluateNumber() - 1);
					}else if(envaluate.getType() == 2){
						beEnvaluatedUser.setBadEnvaluateNumber(beEnvaluatedUser.getBadEnvaluateNumber() - 1);;
					}
					if(newEnvaluate.getType() == 0){
						beEnvaluatedUser.setGoodEnvaluateNumber(beEnvaluatedUser.getGoodEnvaluateNumber() + 1);
					}else if(newEnvaluate.getType() == 1){
						beEnvaluatedUser.setMiddleEnvaluateNumber(beEnvaluatedUser.getMiddleEnvaluateNumber() + 1);
					}else if(newEnvaluate.getType() == 2){
						beEnvaluatedUser.setBadEnvaluateNumber(beEnvaluatedUser.getBadEnvaluateNumber() + 1);;
					}
					userDao.update(beEnvaluatedUser);
					envaluate.setType(newEnvaluate.getType());
					break;
				case "content":
					envaluate.setContent(newEnvaluate.getContent());
					break;
				default:
					
			}
		}
		
		return envaluate;
	}

	public Envaluate getById(Integer envaluateId) {
		// TODO 自动生成的方法存根
		return envaluateDao.getById(envaluateId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			Envaluate envaluate2 = envaluateDao.getById(integer);
			if(envaluate2 != null){
				if(loginAdmin != null){
					envaluateDao.delete(envaluate2);
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
		DetachedCriteria criteria = envaluateDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
