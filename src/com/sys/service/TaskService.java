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

import com.sys.dao.TaskDao;
import com.sys.dao.UserDao;
import com.sys.entity.Admin;
import com.sys.entity.InternalTransferRecord;
import com.sys.entity.Task;
import com.sys.entity.User;
import com.sys.webSocket.WebSocket;


@Transactional
@Component(value="taskService")
public class TaskService {
	
	@Autowired
	private TaskDao taskDao;
	
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

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	public List<Map<String, Object>> getTaskByKeys(String keys,List<Task> tasks){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"taskId",
					"price",
					"content",
					"name",
					"address",
					"releaseUser",
					"receiveUser",
					"state",
					"releaseIsConfirm",
					"receiveIsConfirm",
					"releaseIsEnvaluate",
					"receiveIsEnvaluate",
					"releaseConfirmTime",
					"receiveConfirmTime",
					"releaseTime",
					"receiveTime"
					
					
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Task task : tasks) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(task,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Task task,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		User theUser = null;
		switch (str) {
		case "taskId":
			return task.getTaskId();
		case "price":
			return task.getPrice();
		case "content":
			return task.getContent();
		case "name":
			return task.getName();
		case "address":
			return task.getAddress();
		case "releaseUser":
			theUser = task.getReleaseUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				theMap.put("phone", theUser.getPhone());
				if(theUser.getHeadImg() == null || theUser.getHeadImg() == ""){
					theUser.setHeadImg("/task_sys/assets/images/100.jpg");
				}
				theMap.put("headImg", theUser.getHeadImg());
			}
			return theMap;
		case "receiveUser":
			theUser = task.getReceiveUser();
			if(theUser != null){
				theMap = new HashMap<String, Object>();
				theMap.put("userId", theUser.getUserId());
				theMap.put("name", theUser.getName());
				theMap.put("phone", theUser.getPhone());
				if(theUser.getHeadImg() == null || theUser.getHeadImg() == ""){
					theUser.setHeadImg("/task_sys/assets/images/100.jpg");
				}
				theMap.put("headImg", theUser.getHeadImg());
			}
			return theMap;
		case "state":
			return task.getState();
		case "releaseIsConfirm":
			return task.getReleaseIsConfirm();
		case "receiveIsConfirm":
			return task.getReceiveIsConfirm();
		case "releaseIsEnvaluate":
			return task.getReleaseIsEnvaluate();
		case "receiveIsEnvaluate":
			return task.getReceiveIsEnvaluate();
		case "releaseConfirmTime":
			return task.getReleaseConfirmTime();
		case "receiveConfirmTime":
			return task.getReleaseConfirmTime();
		case "releaseTime":
			return task.getReleaseTime();
		case "receiveTime":
			return task.getReceiveTime();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = taskDao.getAllCountByCriteria(criteria);
		List<Task> tasks = taskDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getTaskByKeys(keys,tasks);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Task add(Task task,User loginUser) {
		task = taskDao.add(task);
		if(task.getTaskId() != null){
			InternalTransferRecord internalTransferRecord = new InternalTransferRecord();
			User theUser = new User();
			theUser.setUserId(task.getReleaseUser().getUserId());
			internalTransferRecord.setTransactionUser(theUser);
			internalTransferRecord.setTime(new Date());
			internalTransferRecord.setMoney(task.getPrice());
			internalTransferRecord.setReason(0);
			internalTransferRecord.setType(1);
			internalTransferRecord.setTask(task);
			internalTransferRecordService.add(internalTransferRecord);
			
			theUser = userDao.getById(task.getReleaseUser().getUserId());
			theUser.setReleaseTaskNumber(theUser.getReleaseTaskNumber() + 1);
			userDao.update(theUser);
			
			//通知用户
			Map<String, Object> contentMap = new HashMap<>();
			contentMap.put("taskId", task.getTaskId());
			contentMap.put("name", task.getName());
			contentMap.put("price", task.getPrice());
			contentMap.put("address", task.getAddress());
			contentMap.put("content", task.getContent());
			
			Map<String, Object> releaseUserMap = new HashMap<>();
			theUser = userDao.getById(task.getReleaseUser().getUserId());
			releaseUserMap.put("userId", theUser.getUserId());
			releaseUserMap.put("name", theUser.getName());
			releaseUserMap.put("goodEnvaluateNumber", theUser.getGoodEnvaluateNumber());
			releaseUserMap.put("middleEnvaluateNumber", theUser.getMiddleEnvaluateNumber());
			releaseUserMap.put("badEnvaluateNumber", theUser.getBadEnvaluateNumber());
			if(theUser.getHeadImg() == null || theUser.getHeadImg() == ""){
				theUser.setHeadImg("/task_sys/assets/images/100.jpg");
			}
			releaseUserMap.put("headImg", theUser.getHeadImg());
			contentMap.put("releaseUser", releaseUserMap);
			WebSocket.sendByWebSocket(null, "newTask", null, task.getReleaseUser(), contentMap,loginUser,null);
		}
		return task;
		
	}
	

	public Map<String, Object> getTaskByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = taskDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Task task, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			Task task3 = taskDao.getById(integer);
			task3 = getNewTaskByKeys(task3,task,keys);
			if(task3 != null){
				taskDao.update(task3);
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

	private Task getNewTaskByKeys(Task task, Task newTask, String keys) {
		if(task == null || newTask == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					task.setName(newTask.getName());
					break;
				
				default:
					
			}
		}
		
		return task;
	}

	public Task getById(Integer taskId) {
		// TODO 自动生成的方法存根
		return taskDao.getById(taskId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			Task task2 = taskDao.getById(integer);
			if(task2 != null){
				if(loginAdmin != null){
					taskDao.delete(task2);
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
		DetachedCriteria criteria = taskDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public int getAllTaskNumber() {
		// TODO Auto-generated method stub
		return taskDao.getAllCountByCriteria(taskDao.getCriteriaForAll());
	}

	public Map<String, Object> getTaskByReleaseUserAndReceiveUserAndState(
			String keys, Integer page, Integer pageSize, Task task) {
		DetachedCriteria criteria = taskDao.getCriteriaByReleaseUserAndReceiveUserAndState(task);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public void update(Task task) {
		taskDao.update(task);
		
	}

	public void confirmTask(Task task,User loginUser) {
		task = taskDao.getById(task.getTaskId());
		if(task != null){
			//发布者确认
			if(task.getReleaseUser().getUserId() == loginUser.getUserId()){
				task.setReleaseConfirmTime(new Date());
				task.setReleaseIsConfirm(true);
				task.setState(2);
				taskDao.update(task);
				
				InternalTransferRecord internalTransferRecord = new InternalTransferRecord();
				User theUser = new User();
				theUser.setUserId(task.getReceiveUser().getUserId());
				internalTransferRecord.setTransactionUser(theUser);
				internalTransferRecord.setTime(new Date());
				internalTransferRecord.setMoney(task.getPrice());
				internalTransferRecord.setReason(1);
				internalTransferRecord.setType(0);
				internalTransferRecord.setTask(task);
				internalTransferRecordService.add(internalTransferRecord);
			}else{
				task.setReceiveConfirmTime(new Date());
				task.setReceiveIsConfirm(true);
				taskDao.update(task);
			}
		}
	}

	public void receiveTask(Task task, User loginUser) {
		taskDao.update(task);
		loginUser = userDao.getById(loginUser.getUserId());
		loginUser.setReceiveTaskNumber(loginUser.getReceiveTaskNumber() + 1);
		userDao.update(loginUser);
	}

	public void cancelTask(Task task, User loginUser) {
		task = taskDao.getById(task.getTaskId());
		if(task != null){
			//发布者确认
			if(task.getReleaseUser().getUserId() == loginUser.getUserId()){
				task.setState(3);
				taskDao.update(task);
				
				InternalTransferRecord internalTransferRecord = new InternalTransferRecord();
				User theUser = new User();
				theUser.setUserId(task.getReleaseUser().getUserId());
				internalTransferRecord.setTransactionUser(theUser);
				internalTransferRecord.setTime(new Date());
				internalTransferRecord.setMoney(task.getPrice());
				internalTransferRecord.setReason(2);
				internalTransferRecord.setType(0);
				internalTransferRecord.setTask(task);
				internalTransferRecordService.add(internalTransferRecord);
			}
		}
		
	}

}
