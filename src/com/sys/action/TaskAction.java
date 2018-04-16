package com.sys.action;

import java.io.IOException;
import java.util.Date;
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
import com.sys.entity.Task;
import com.sys.entity.User;
import com.sys.service.TaskService;
import com.sys.service.UserService;
import com.sys.util.PublicUtils;

@Component(value="taskAction")
@Scope(value="prototype")
public class TaskAction extends ActionSupport implements ModelDriven<Task>{
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Task task = new Task();
	
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

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

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Override
	public Task getModel() {
		if(task == null){
			task = new Task();	
		}
		return task;
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
			Map<String, Object> map = taskService.getAll(keys,page,pageSize);
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
				idsIntegers = new Integer[0];
			}
			Map<String, Object> map = taskService.getTaskByIds(keys,page,pageSize,idsIntegers);
			
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	public void getByReleaseUserAndReceiveUserAndState(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			if(task.getReleaseUser() != null && task.getReleaseUser().getUserId() == 0){
				task.setReleaseUser(loginUser);
			}
			if(task.getReceiveUser() != null && task.getReceiveUser().getUserId() == 0){
				task.setReceiveUser(loginUser);
			}
			Map<String, Object> map = taskService.getTaskByReleaseUserAndReceiveUserAndState(keys,page,pageSize,task);
			
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
				idsIntegers = new Integer[0];
			}
			Map<String, Object> map = taskService.updateByIds(keys,idsIntegers,task,loginUser,loginAdmin);
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
			Map<String, Object> map = taskService.deleteByIds(idsIntegers,loginUser,loginAdmin);
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
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			User theUser = userService.getById(loginUser.getUserId());
			if(theUser.getMoney() < task.getPrice()){
				responseBean.setStatus(403);
				responseBean.put("error", "您的余额不足");
			}else{
				task.setReleaseUser(loginUser);
				task.setState(0);
				task.setReleaseTime(new Date());
				task.setReleaseIsConfirm(false);
				task.setReleaseIsEnvaluate(false);
				task.setReceiveIsConfirm(false);
				task.setReceiveIsEnvaluate(false);
				task = taskService.add(task,loginUser);
				if(task.getTaskId() != null) {
					responseBean.setStatus(201);
					responseBean.put("taskId", task.getTaskId());
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
	
	public void receiveTask(){
		ResponseBean responseBean =new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Task theTask = taskService.getById(task.getTaskId());
			if(theTask == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				if(theTask.getReceiveUser() == null){
					if(theTask.getReleaseUser().getUserId() == loginUser.getUserId()){
						responseBean.setStatus(401);
						responseBean.put("error", "请勿抢夺自己的订单");
					}else{
						theTask.setReceiveTime(new Date());
						theTask.setReceiveUser(loginUser);
						theTask.setState(1);
						taskService.receiveTask(theTask,loginUser);
					}
				}else{
					responseBean.setStatus(403);
					responseBean.put("error", "该订单已被其他人抢先");
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
	
	public void confirmTask(){
		ResponseBean responseBean =new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Task theTask = taskService.getById(task.getTaskId());
			if(theTask == null){
				responseBean.setStatus(404);
				responseBean.put("error", "订单不存在");
			}else{
				if(theTask.getState() == 1){
					if(theTask.getReleaseUser().getUserId() != loginUser.getUserId() 
							&& theTask.getReceiveUser().getUserId() != loginUser.getUserId()){
						responseBean.setStatus(401);
						responseBean.put("error", "您无权确认本订单");
					}else{
						taskService.confirmTask(theTask,loginUser);
					}
				}else{
					responseBean.setStatus(403);
					responseBean.put("error", "订单无法确认");
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
	
	public void cancelTask(){
		ResponseBean responseBean =new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			Task theTask = taskService.getById(task.getTaskId());
			if(theTask == null){
				responseBean.setStatus(404);
				responseBean.put("error", "订单不存在");
			}else{
				if(theTask.getState() == 0){
					if(theTask.getReleaseUser().getUserId() != loginUser.getUserId()){
						responseBean.setStatus(401);
						responseBean.put("error", "您无权确认本订单");
					}else{
						taskService.cancelTask(theTask,loginUser);
					}
				}else{
					responseBean.setStatus(403);
					responseBean.put("error", "订单无法取消");
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

}
