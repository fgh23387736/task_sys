package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.CancelApplyDao;
import com.sys.entity.Admin;
import com.sys.entity.CancelApply;
import com.sys.entity.Task;
import com.sys.entity.User;


@Transactional
@Component(value="cancelApplyService")
public class CancelApplyService {
	
	@Autowired
	private CancelApplyDao cancelApplyDao;
	
	
	public CancelApplyDao getCancelApplyDao() {
		return cancelApplyDao;
	}

	public void setCancelApplyDao(CancelApplyDao cancelApplyDao) {
		this.cancelApplyDao = cancelApplyDao;
	}
	
	public List<Map<String, Object>> getCancelApplyByKeys(String keys,List<CancelApply> cancelApplys){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"cancelApplyId",
					"task",
					"applyType",
					"reason",
					"type",
					"dealReason",
					"time",
					"dealTime",
					"dealType",
					"admin"		
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (CancelApply cancelApply : cancelApplys) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(cancelApply,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(CancelApply cancelApply,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		User theUser = null;
		Task theTask = null;
		Admin theAdmin = null;
		switch (str) {
		case "cancelApplyId":
			return cancelApply.getCancelApplyId();
		case "task":
			theTask = cancelApply.getTask();
			if(theTask != null){
				theMap = new HashMap<String, Object>();
				theMap.put("taskId", theTask.getTaskId());
				theMap.put("name", theTask.getName());
			}
			return theMap;
		case "applyType":
			return cancelApply.getApplyType();
		case "reason":
			return cancelApply.getReason();
		case "type":
			return cancelApply.getType();
		case "dealReason":
			return cancelApply.getDealReason();
		case "time":
			return cancelApply.getTime();
		case "dealTime":
			return cancelApply.getDealTime();
		case "dealType":
			return cancelApply.getDealType();
		case "admin":
			theAdmin = cancelApply.getAdmin();
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
		int total = cancelApplyDao.getAllCountByCriteria(criteria);
		List<CancelApply> cancelApplys = cancelApplyDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getCancelApplyByKeys(keys,cancelApplys);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public CancelApply add(CancelApply cancelApply) {
		return cancelApplyDao.add(cancelApply);
		
	}
	

	public Map<String, Object> getCancelApplyByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = cancelApplyDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			CancelApply cancelApply, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			CancelApply cancelApply3 = cancelApplyDao.getById(integer);
			cancelApply3 = getNewCancelApplyByKeys(cancelApply3,cancelApply,keys);
			if(cancelApply3 != null){
				cancelApplyDao.update(cancelApply3);
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

	private CancelApply getNewCancelApplyByKeys(CancelApply cancelApply, CancelApply newCancelApply, String keys) {
		if(cancelApply == null || newCancelApply == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "dealReason":
					cancelApply.setDealReason(newCancelApply.getDealReason());
					break;
				
				default:
					
			}
		}
		
		return cancelApply;
	}

	public CancelApply getById(Integer cancelApplyId) {
		// TODO 自动生成的方法存根
		return cancelApplyDao.getById(cancelApplyId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			CancelApply cancelApply2 = cancelApplyDao.getById(integer);
			if(cancelApply2 != null){
				if(loginAdmin != null){
					cancelApplyDao.delete(cancelApply2);
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
		DetachedCriteria criteria = cancelApplyDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getCancelApplyByTaskAndType(String keys,
			Integer page, Integer pageSize, CancelApply cancelApply) {
		DetachedCriteria criteria = cancelApplyDao.getCriteriaByTaskAndType(cancelApply);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public void update(CancelApply cancelApply) {
		cancelApplyDao.update(cancelApply);
		
	}

	public Map<String, Object> getCancelApplyByTaskAndTypeForReleaseUser(
			String keys, Integer page, Integer pageSize,
			CancelApply cancelApply, User user) {
		DetachedCriteria criteria = cancelApplyDao.getCriteriaByTaskAndTypeForReleaseUser(cancelApply,user);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getCancelApplyByTaskAndTypeForReceiveUser(
			String keys, Integer page, Integer pageSize,
			CancelApply cancelApply, User user) {
		DetachedCriteria criteria = cancelApplyDao.getCriteriaByTaskAndTypeForReceiveUser(cancelApply,user);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
