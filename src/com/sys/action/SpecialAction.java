package com.sys.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.websocket.Session;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.sys.bean.ResponseBean;
import com.sys.entity.Admin;
import com.sys.entity.User;
import com.sys.service.SystemTransferRecordService;
import com.sys.service.TaskService;
import com.sys.service.UserService;
import com.sys.webSocket.WebSocket;

@Component(value="specialAction")
@Scope(value="prototype")
public class SpecialAction extends ActionSupport{
    
    @Autowired
	private TaskService taskService;
    
    @Autowired
	private UserService userService;
    
    @Autowired
	private SystemTransferRecordService systemTransferRecordService;
    
    
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SystemTransferRecordService getSystemTransferRecordService() {
		return systemTransferRecordService;
	}

	public void setSystemTransferRecordService(
			SystemTransferRecordService systemTransferRecordService) {
		this.systemTransferRecordService = systemTransferRecordService;
	}
	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		
		return NONE;
	}
	
	public void getMenu(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		String loginType = (String)ServletActionContext.getRequest().getSession().getAttribute("type");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else if(loginType.equals("admin") || loginType.equals("user")){
			BufferedReader reader = null;
			String laststr = "";
			try{
				String path;
				if(loginType.equals("admin")){
					path = ServletActionContext.getServletContext().getRealPath("end/admin/menu.json");
				}else{
					path = ServletActionContext.getServletContext().getRealPath("end/user/menu.json");
				}
				FileInputStream fileInputStream = new FileInputStream(path);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
				reader = new BufferedReader(inputStreamReader);
				String tempString = null;
				while((tempString = reader.readLine()) != null){
					laststr += tempString;
				}
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(reader != null){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} 
			responseBean.put("data", responseBean.jsonStringToJsonArray(laststr,"data") );
			
		}else{
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	
	public void adminGetWebInfo(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		String loginType = (String)ServletActionContext.getRequest().getSession().getAttribute("type");
		if (loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			responseBean.put("userNumber", userService.getAllUserNumber());
			responseBean.put("taskNumber", taskService.getAllTaskNumber());
			responseBean.put("systemMoney", systemTransferRecordService.getAllMoney());
			responseBean.put("suspenedUserNumber", userService.getAllSuspenedUserNumber());
			
		}
		
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}
