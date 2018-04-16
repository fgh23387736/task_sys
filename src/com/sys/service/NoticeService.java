package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.NoticeDao;
import com.sys.entity.Admin;
import com.sys.entity.Notice;
import com.sys.entity.Task;
import com.sys.entity.User;
import com.sys.webSocket.WebSocket;


@Transactional
@Component(value="noticeService")
public class NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;
	
	
	public NoticeDao getNoticeDao() {
		return noticeDao;
	}

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	
	public List<Map<String, Object>> getNoticeByKeys(String keys,List<Notice> notices){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"noticeId",
					"title",
					"content",
					"time",
					"admin"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Notice notice : notices) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(notice,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Notice notice,String str){
		Map<String, Object> theMap = null;
		List<Map<String, Object>> list = null;
		Admin theAdmin = null;
		switch (str) {
		case "noticeId":
			return notice.getNoticeId();
		case "title":
			return notice.getTitle();
		case "content":
			return notice.getContent();
		case "time":
			return notice.getTime();
		case "admin":
			theAdmin = notice.getAdmin();
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
		int total = noticeDao.getAllCountByCriteria(criteria);
		List<Notice> notices = noticeDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getNoticeByKeys(keys,notices);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Notice add(Notice notice,Admin loginAdmin) {
		notice = noticeDao.add(notice);
		if(notice.getNoticeId() != null){
			Map<String, Object> contentMap = new HashMap<>();
			contentMap.put("noticeId", notice.getNoticeId());
			contentMap.put("time", notice.getTime());
			contentMap.put("title", notice.getTitle());
			WebSocket.sendByWebSocket(null, "newNotice", loginAdmin, null, contentMap,null);
		}
		return notice;
		
	}
	

	public Map<String, Object> getNoticeByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = noticeDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Notice notice, User loginUser, Admin loginAdmink) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			Notice notice3 = noticeDao.getById(integer);
			notice3 = getNewNoticeByKeys(notice3,notice,keys);
			if(notice3 != null){
				noticeDao.update(notice3);
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

	private Notice getNewNoticeByKeys(Notice notice, Notice newNotice, String keys) {
		if(notice == null || newNotice == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "content":
					notice.setContent(newNotice.getContent());
					break;
				case "time":
					notice.setTime(newNotice.getTime());;
					break;
				default:
					
			}
		}
		
		return notice;
	}

	public Notice getById(Integer noticeId) {
		// TODO 自动生成的方法存根
		return noticeDao.getById(noticeId);
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			Notice notice2 = noticeDao.getById(integer);
			if(notice2 != null){
				if(loginAdmin != null){
					noticeDao.delete(notice2);
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
		DetachedCriteria criteria = noticeDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getByTitle(String keys, Integer page,
			Integer pageSize, Notice notice) {
		DetachedCriteria criteria = noticeDao.getCriteriaByTitle(notice);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

}
