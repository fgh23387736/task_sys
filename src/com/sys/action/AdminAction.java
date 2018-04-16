package com.sys.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sys.bean.ResponseBean;
import com.sys.entity.Admin;
import com.sys.entity.User;
import com.sys.service.AdminService;
import com.sys.util.PublicUtils;

@Component(value="adminAction")
@Scope(value="prototype")
public class AdminAction extends ActionSupport implements ModelDriven<Admin>{
	
	@Autowired
	private AdminService adminService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Admin admin = new Admin();
	
	
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	@Override
	public Admin getModel() {
		if(admin == null){
			admin = new Admin();	
		}
		return admin;
	}
	
	public void getAll(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else if(loginAdmin == null){
			responseBean.setStatus(401);
			responseBean.put("error", "您不具有权限");
		}else{
			Map<String, Object> map = adminService.getAll(keys,page,pageSize);
			responseBean.setObjMap(map);
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void getByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			if(idsIntegers == null || idsIntegers.length == 0){
				idsIntegers = new Integer[1];
				idsIntegers[0] = loginAdmin.getAdminId();
			}
			Map<String, Object> map = adminService.getAdminByIds(keys,page,pageSize,idsIntegers);
			
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	public void updateByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			if(idsIntegers == null || idsIntegers.length == 0){
				idsIntegers = new Integer[1];
				idsIntegers[0] = loginAdmin.getAdminId();
			}
			Map<String, Object> map = adminService.updateByIds(keys,idsIntegers,admin,loginUser,loginAdmin);
			responseBean.setStatus((int)map.get("code"));
			responseBean.setObjMap((Map<String, Object>)map.get("result"));
			if(idsIntegers[0] == loginAdmin.getAdminId()){
				Admin theadmin = adminService.getById(loginAdmin.getAdminId());
				if(theadmin.getHeadImg() == null || theadmin.getHeadImg() == ""){
					theadmin.setHeadImg("/task_sys/assets/images/100.jpg");
				}
				ServletActionContext.getRequest().getSession().setAttribute("admin", theadmin);
			}
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void deleteByIds(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else if(loginAdmin == null){
			responseBean.setStatus(401);
			responseBean.put("error", "您不具有权限操作");
		}else{
			Integer[] idsIntegers = PublicUtils.getIdsByString(ids, "\\+");
			Map<String, Object> map = adminService.deleteByIds(idsIntegers,loginUser,loginAdmin);
			responseBean.setStatus((int)map.get("code"));
			responseBean.setObjMap((Map<String, Object>)map.get("result"));
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void add(){
		ResponseBean responseBean =new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			List<Admin> adminList = adminService.getAdminByUserName(admin.getUserName());
			if(adminList != null && adminList.size() > 0){
				responseBean.put("error", "用户名已存在");
				responseBean.setStatus(401);
			}else{
				admin.setPassword(PublicUtils.getMD5(admin.getPassword()));
				admin = adminService.add(admin);
				if(admin.getAdminId() != null) {
					responseBean.setStatus(201);
					responseBean.put("adminId", admin.getAdminId());
				} else {
					responseBean.put("error", "添加失败，系统错误");
					responseBean.setStatus(500);
				}
			}
		}
		
		try {
			responseBean.write(responseBean.getJsonString());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	
	public void login(){
		ResponseBean response =new ResponseBean();
		List<Admin> adminList = adminService.getAdminByUserName(admin.getUserName());
		if(adminList != null && adminList.size() > 0){
			Admin theadmin = adminList.get(0);
			if(theadmin != null){
				if(PublicUtils.getMD5(admin.getPassword()).equals(theadmin.getPassword())){
					response.setStatus(201);
					if(theadmin.getHeadImg() == null || theadmin.getHeadImg() == ""){
						theadmin.setHeadImg("/task_sys/assets/images/100.jpg");
					}
					response.put("adminId", theadmin.getAdminId());
					ServletActionContext.getRequest().getSession().setAttribute("admin", theadmin);
					ServletActionContext.getRequest().getSession().setAttribute("type", "admin");
				}else{
					response.put("error", "密码错误");
					response.setStatus(401);
				}
			}else{
				response.put("error", "用户不存在");
				response.setStatus(401);
			}
		}else{
			response.put("error", "用户不存在");
			response.setStatus(401);
		}
		try {
			response.write(response.getJsonString());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void signOut(){
		ResponseBean responseBean = new ResponseBean();
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			ServletActionContext.getRequest().getSession().setAttribute("admin", null);
			ServletActionContext.getRequest().getSession().setAttribute("type", null);
			responseBean.setStatus(201);
			responseBean.setObjMap(null);
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void changePassword(){
		ResponseBean responseBean = new ResponseBean();
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginAdmin == null) {
			responseBean.setStatus(400);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(!admin.getRePassword().equals(admin.getNewPassword())){
				responseBean.setStatus(400);
				responseBean.put("error", "两次新密码输入不一致");
			}else if(!PublicUtils.getMD5(admin.getPassword()).equals(loginAdmin.getPassword())){
				responseBean.setStatus(400);
				responseBean.put("error", "原密码错误");
			}else{
				admin.setPassword(PublicUtils.getMD5(admin.getNewPassword()));
				adminService.updatePassword(admin,loginAdmin.getAdminId());
				responseBean.setStatus(200);
				ServletActionContext.getRequest().getSession().setAttribute("admin", null);
				ServletActionContext.getRequest().getSession().setAttribute("type", null);
			}
			
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
	
}
