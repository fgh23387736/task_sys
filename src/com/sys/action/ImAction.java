package com.sys.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.sys.entity.ChatRecord;
import com.sys.entity.User;
import com.sys.service.AdminService;
import com.sys.service.ChatRecordService;
import com.sys.service.UserService;
import com.sys.util.PublicUtils;

@Component(value="imAction")
@Scope(value="prototype")
public class ImAction extends ActionSupport {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatRecordService chatRecordServer;
	
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	
	
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	
	
	public ChatRecordService getChatRecordServer() {
		return chatRecordServer;
	}

	public void setChatRecordServer(ChatRecordService chatRecordServer) {
		this.chatRecordServer = chatRecordServer;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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

	
	public void init(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null) {
			responseBean.put("code", 1);
			responseBean.put("msg", "您还未登录，无权获取本信息");
		}else{
			Map<String, Object> mineMap = new HashMap<>();
			Map<String, Object> dataMap = new HashMap<>();
			mineMap.put("username", loginUser.getName());
			mineMap.put("id", loginUser.getUserId());
			mineMap.put("status", "online");
			mineMap.put("sign", "");
			mineMap.put("avatar", loginUser.getHeadImg());
			dataMap.put("mine", mineMap);
			
			List<Map<String, Object>> friendList = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> friendMap = new HashMap<>();
			friendMap.put("groupname", "好友");
			friendMap.put("id", 0);
			
			List<Map<String, Object>> friendListList = new ArrayList<Map<String, Object>>();
			List<User> userList = chatRecordServer.getChatUserListByUser(loginUser);
			for (User user : userList) {
				mineMap = new HashMap<>();
				mineMap.put("username", user.getName());
				mineMap.put("id", user.getUserId());
				mineMap.put("status", "online");
				mineMap.put("sign", "");
				if(user.getHeadImg() == null || user.getHeadImg() == ""){
					user.setHeadImg("/task_sys/assets/images/100.jpg");
				}
				mineMap.put("avatar", user.getHeadImg());
				friendListList.add(mineMap);
			}
			friendMap.put("list", friendListList);
			friendList.add(friendMap);
			
			
			dataMap.put("friend", friendList);
			
			responseBean.put("data", dataMap);
			responseBean.put("code", 0);
			responseBean.put("msg", "");
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
