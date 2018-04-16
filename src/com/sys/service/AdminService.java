package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sys.dao.AdminDao;
import com.sys.entity.Admin;
import com.sys.entity.Admin;
import com.sys.entity.User;
import com.sys.util.PublicUtils;


@Transactional
@Component(value="adminService")
public class AdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	
	public AdminDao getAdminDao() {
		return adminDao;
	}
	
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	public List<Map<String, Object>> getAdminByKeys(String keys,List<Admin> admins){
		if (keys == null) {
			keys = "";
		}else{
			
		}
		String[] keysArrStrings = keys.split("\\+");	
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			String[] tempKeys = {
					"adminId",
					"userName",
					"name",
					"phone",
					"headImg"
			};
			keysArrStrings = tempKeys;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (Admin admin : admins) {
			map = new HashMap<String, Object>();
			for (String key : keysArrStrings) {
				map.put(key, getAttributeByString(admin,key));
			}
			list.add(map);
		}
		return list;
	}
	
	public Object getAttributeByString(Admin admin,String str){
		Map<String, Object> theMap;
		List<Map<String, Object>> list;
		switch (str) {
		case "adminId":
			return admin.getAdminId();
		case "userName":
			return admin.getUserName();
		case "name":
			return admin.getName();
		case "phone":
			return admin.getPhone();
		case "headImg":
			if(admin.getHeadImg() == "" || admin.getHeadImg() == null){
				return "/task_sys/assets/images/100.jpg/";
			}
			return admin.getHeadImg();
		default:
			return null;
		}
	}
	
	public Map<String, Object> getMapByKeysAndPage(String keys,
			Integer page, Integer pageSize,DetachedCriteria criteria){
		int total = adminDao.getAllCountByCriteria(criteria);
		List<Admin> admins = adminDao.getDataByCriteria(page, pageSize, criteria);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = getAdminByKeys(keys,admins);
		map.put("total", total);
		map.put("resultList", list);
		return map;
	}
	
	public Admin add(Admin admin) {
		return adminDao.add(admin);
		
	}
	

	public Map<String, Object> getAdminByIds(String keys,Integer page,Integer pageSize,Integer[] ids) {
		DetachedCriteria criteria = adminDao.getCriteriaByIds(ids);
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}

	public Map<String, Object> updateByIds(String keys, Integer[] idsIntegers,
			Admin admin, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 201);
		for (Integer integer : idsIntegers) {
			Admin admin3 = adminDao.getById(integer);
			admin3 = getNewAdminByKeys(admin3,admin,keys);
			if(admin3 != null){
				adminDao.update(admin3);
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

	private Admin getNewAdminByKeys(Admin admin, Admin newAdmin, String keys) {
		if(admin == null || newAdmin == null || keys == null){
			return null;
		}
		String[] keysArrStrings = keys.split("\\+");
		if(keys.equals("") || keysArrStrings == null || keysArrStrings.length == 0){
			return null;
		}
		for (String key : keysArrStrings) {
			switch (key) {
				case "name":
					admin.setName(newAdmin.getName());
					break;
				case "phone":
					admin.setPhone(newAdmin.getPhone());
					break;
				case "password":
					admin.setPassword(PublicUtils.getMD5(newAdmin.getPassword()));
					break;
				case "headImg":
					String headImg = admin.getHeadImg();
					if(newAdmin.getHeadImg().substring(0, 4).equals("data")){
						PublicUtils.deleteFileFromServer(headImg);
						Map<String, Object> headImgMap = PublicUtils.generateImage(newAdmin.getHeadImg(), "/upload/headImg");
						if(headImgMap != null){
							newAdmin.setHeadImg((String)headImgMap.get("path"));
						}else{
							newAdmin.setHeadImg("");
						}
						
					}
					admin.setHeadImg(newAdmin.getHeadImg());
					break;
				default:
					
			}
		}
		
		return admin;
	}

	public Admin getById(Integer adminId) {
		// TODO 自动生成的方法存根
		return adminDao.getById(adminId);
	}

	public List<Admin> getAdminByUserName(String userName) {
		List<Admin> admins = adminDao.getDataByCriteria(null, null, adminDao.getCriteriaByUserName(userName));
		//System.out.println(admins.get(0).getNotices().size());
		return admins;
	}

	public Map<String, Object> deleteByIds(Integer[] idsIntegers, User loginUser, Admin loginAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> theMap = null;
		map.put("code", 204);
		for (Integer integer : idsIntegers) {
			Admin admin2 = adminDao.getById(integer);
			if(admin2 != null){
				if(loginAdmin != null){
					adminDao.delete(admin2);
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
		DetachedCriteria criteria = adminDao.getCriteriaForAll();
		Map<String, Object> map = getMapByKeysAndPage(keys,page,pageSize,criteria);
		return map;
	}
	public void updatePassword(Admin admin, Integer adminId) {
		Admin admin2 = adminDao.getById(adminId);
		if(admin2 != null){
			admin2.setPassword(admin.getPassword());
			adminDao.update(admin2);
		}
	}
}
