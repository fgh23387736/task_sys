package com.sys.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sys.bean.ResponseBean;
import com.sys.entity.Admin;
import com.sys.entity.Envaluate;
import com.sys.entity.Task;
import com.sys.entity.User;
import com.sys.service.EnvaluateService;
import com.sys.service.TaskService;
import com.sys.util.PublicUtils;

@Component(value="envaluateAction")
@Scope(value="prototype")
public class EnvaluateAction extends ActionSupport implements ModelDriven<Envaluate>{
	
	@Autowired
	private EnvaluateService envaluateService;
	
	@Autowired
	private TaskService taskService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private Envaluate envaluate = new Envaluate();
	
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
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

	public Envaluate getEnvaluate() {
		return envaluate;
	}

	public void setEnvaluate(Envaluate envaluate) {
		this.envaluate = envaluate;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public EnvaluateService getEnvaluateService() {
		return envaluateService;
	}

	public void setEnvaluateService(EnvaluateService envaluateService) {
		this.envaluateService = envaluateService;
	}

	@Override
	public Envaluate getModel() {
		if(envaluate == null){
			envaluate = new Envaluate();	
		}
		return envaluate;
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
			Map<String, Object> map = envaluateService.getAll(keys,page,pageSize);
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
			Map<String, Object> map = envaluateService.getEnvaluateByIds(keys,page,pageSize,idsIntegers);
			
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
			Map<String, Object> map = envaluateService.updateByIds(keys,idsIntegers,envaluate,loginUser,loginAdmin);
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
			Map<String, Object> map = envaluateService.deleteByIds(idsIntegers,loginUser,loginAdmin);
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
			if(envaluate.getTask() == null || envaluate.getTask().getTaskId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				Task theTask = taskService.getById(envaluate.getTask().getTaskId());
				if(theTask == null){
					responseBean.setStatus(404);
					responseBean.put("error", "项目不存在");
				}else{
					User theReleaseUser = theTask.getReleaseUser();
					User theReceiveUser = theTask.getReceiveUser();
					boolean isOk = false;
					if(loginUser.getUserId().equals(theReleaseUser.getUserId()) ){
						envaluate.setEnvaluateUser(theReleaseUser);
						envaluate.setBeEnvaluatedUser(theReceiveUser);
						isOk = true;
					}else if(loginUser.getUserId().equals(theReceiveUser.getUserId())){
						envaluate.setEnvaluateUser(theReceiveUser);
						envaluate.setBeEnvaluatedUser(theReleaseUser);
						isOk = true;
					}else{
						responseBean.setStatus(401);
						responseBean.put("error", "您不具有权限");
						isOk = false;
					}
//					if(envaluate.getBeEnvaluatedUser().getUserId().equals(theReleaseUser.getUserId()) ){
//						isOk = true;
//					}else if(envaluate.getBeEnvaluatedUser().getUserId().equals(theReceiveUser.getUserId())){
//						isOk = true;
//					}else{
//						responseBean.setStatus(401);
//						responseBean.put("error", "您不具有权限");
//						isOk = false;
//					}
					
					
					if(isOk){
						if(theTask.getState() != 2){
							responseBean.put("error", "该订单当前不允许评论");
							responseBean.setStatus(403);
						}else{
							boolean isEnvaluated = false;
							Set<Envaluate> envaluates = theTask.getEnvaluates();
							for (Envaluate envaluate : envaluates) {
								if(envaluate.getEnvaluateUser().getUserId() == loginUser.getUserId()){
									isEnvaluated = true;
									break;
								}
							}
							if(isEnvaluated){
								responseBean.put("error", "请勿重复评论");
								responseBean.setStatus(403);
							}else{
								envaluate.setTime(new Date());
								envaluate = envaluateService.add(envaluate,loginUser);
								if(envaluate.getEnvaluateId() != null) {
									responseBean.setStatus(201);
									responseBean.put("envaluateId", envaluate.getEnvaluateId());
								} else {
									responseBean.put("error", "添加失败，系统错误");
									responseBean.setStatus(500);
								}
							}
							
						}
						
					}
					
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
