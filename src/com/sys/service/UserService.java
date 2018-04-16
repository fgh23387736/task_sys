package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.UserDao;
import com.sys.entity.Admin;
import com.sys.entity.User;
import com.sys.util.PublicUtils;


@Transactional
@Component(value="userService")
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public List<Map<String, Object>> getUserByKeys(String keys,List<User> users){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"userId",
					"userName",
					"name",
					"phone",
					"releaseTaskNumber",
					"receiveTaskNumber",
					"suspendedDeadline",
					"money",
					"goodEnvaluateNumber",
					"middleEnvaluateNumber",
					"badEnvaluateNumber",
					"headImg"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (User user : users) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(user,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(User user,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
		case "userId":
			return user.getUserId();
		case "userName":
			return user.getUserName();
		case "name":
			return user.getName();
		case "phone":
			return user.getPhone();
		case "releaseTaskNumber":
			return user.getReleaseTaskNumber();
		case "receiveTaskNumber":
			return user.getReceiveTaskNumber();
		case "suspendedDeadline":
			return user.getSuspenedDeadline();
		case "money":
			return user.getMoney();
		case "headImg":
			if(user.getHeadImg() == "" || user.getHeadImg() == null){
				return "/task_sys/assets/images/100.jpg/";
			}
			return user.getHeadImg();
		case "goodEnvaluateNumber":
			return user.getGoodEnvaluateNumber();
		case "middleEnvaluateNumber":
			return user.getMiddleEnvaluateNumber();
		case "badEnvaluateNumber":
			return user.getBadEnvaluateNumber();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = userDao.getAllCountByCriteria(criteria);
		List<User> users = userDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getUserByKeys(keys,users);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public User add(User user) {
		return userDao.add(user);
		
	}
	

	public Map<String, Object> getUserByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = userDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			User user, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			User user3 = userDao.getById(integer);
			user3 = getNewUserByKeys(user3,user,keys);
			if(user3 != null){
				userDao.update(user3);
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

	private User getNewUserByKeys(User user, User newUser, String keys) {
		if(user == null || newUser == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					user.setName(newUser.getName());
					break;
				case "phone":
					user.setPhone(newUser.getPhone());
					break;
				case "suspendedDeadline":
					user.setSuspenedDeadline(newUser.getSuspenedDeadline());
					break;
				case "password":
					user.setPassword(PublicUtils.getMD5(newUser.getPassword()));
					break;
				case "headImg":
					String headImg = user.getHeadImg();
					if(newUser.getHeadImg().substring(0, 4).equals("data")){
						PublicUtils.deleteFileFromServer(headImg);
						Map<String, Object> headImgMap = PublicUtils.generateImage(newUser.getHeadImg(), "/upload/headImg");
						if(headImgMap != null){
							newUser.setHeadImg((String)headImgMap.get("path"));
						}else{
							newUser.setHeadImg("");
						}
						
					}
					user.setHeadImg(newUser.getHeadImg());
					break;
				default:
					
			}
		}
		
		return user;
	}

	public User getById(Integer userId) {
		// TODO 自动生成的方法存根
		return userDao.getById(userId);
	}

	public List<User> getUserByUserName(String userName) {
		List<User> users = userDao.getDataByCriteria(null, null, userDao.getCriteriaByUserName(userName));
		//System.out.println(users.get(0).getNotices().size());
		return users;
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			User user2 = userDao.getById(integer);
			if(user2 != null){
				if(loginAdmin != null){
					userDao.delete(user2);
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
		DetachedCriteria criteria = userDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> getByNameAndUserName(String keys, Integer page,
			Integer pageSize, User user) {
		DetachedCriteria criteria = userDao.getByNameAndUserName(user);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public int getAllUserNumber() {
		// TODO Auto-generated method stub
		return userDao.getAllCountByCriteria(userDao.getCriteriaForAll());
	}

	public int getAllSuspenedUserNumber() {
		// TODO Auto-generated method stub
		return userDao.getAllCountByCriteria(userDao.getCriteriaForSuspened());
	}
	
	public void updatePassword(User user, Integer userId) {
		User user2 = userDao.getById(userId);
		if(user2 != null){
			user2.setPassword(user.getPassword());
			userDao.update(user2);
		}
	}

}
