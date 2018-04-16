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
import com.sys.entity.InternalTransferRecord;
import com.sys.entity.Task;
import com.sys.entity.User;
import com.sys.service.InternalTransferRecordService;
import com.sys.service.TaskService;
import com.sys.util.PublicUtils;

@Component(value="internalTransferRecordAction")
@Scope(value="prototype")
public class InternalTransferRecordAction extends ActionSupport implements ModelDriven<InternalTransferRecord>{
	
	@Autowired
	private InternalTransferRecordService internalTransferRecordService;
	
	@Autowired
	private TaskService taskService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private InternalTransferRecord internalTransferRecord = new InternalTransferRecord();
	
	
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

	public InternalTransferRecord getInternalTransferRecord() {
		return internalTransferRecord;
	}

	public void setInternalTransferRecord(InternalTransferRecord internalTransferRecord) {
		this.internalTransferRecord = internalTransferRecord;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public InternalTransferRecordService getInternalTransferRecordService() {
		return internalTransferRecordService;
	}

	public void setInternalTransferRecordService(InternalTransferRecordService internalTransferRecordService) {
		this.internalTransferRecordService = internalTransferRecordService;
	}

	@Override
	public InternalTransferRecord getModel() {
		if(internalTransferRecord == null){
			internalTransferRecord = new InternalTransferRecord();	
		}
		return internalTransferRecord;
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
			Map<String, Object> map = internalTransferRecordService.getAll(keys,page,pageSize);
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
			Map<String, Object> map = internalTransferRecordService.getInternalTransferRecordByIds(keys,page,pageSize,idsIntegers);
			
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
			Map<String, Object> map = internalTransferRecordService.updateByIds(keys,idsIntegers,internalTransferRecord,loginUser,loginAdmin);
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
			Map<String, Object> map = internalTransferRecordService.deleteByIds(idsIntegers,loginUser,loginAdmin);
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
			if(internalTransferRecord.getTask() == null || internalTransferRecord.getTask().getTaskId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "项目不存在");
			}else{
				Task theTask = taskService.getById(internalTransferRecord.getTask().getTaskId());
				if(theTask == null){
					responseBean.setStatus(404);
					responseBean.put("error", "项目不存在");
				}else{
					User theReleaseUser = theTask.getReleaseUser();
					User theReceiveUser = theTask.getReceiveUser();
					boolean isOk = false;
					if(loginUser.getUserId().equals(theReleaseUser.getUserId()) ){
						isOk = true;
					}else if(loginUser.getUserId().equals(theReceiveUser.getUserId())){
						isOk = true;
					}else{
						responseBean.setStatus(401);
						responseBean.put("error", "您不具有权限");
					}
					if(isOk){
						internalTransferRecord.setTransactionUser(loginUser);
						
						internalTransferRecord.setTime(new Date());
						internalTransferRecord = internalTransferRecordService.add(internalTransferRecord);
						if(internalTransferRecord.getInternalTransferRecordId() != null) {
							responseBean.setStatus(201);
							responseBean.put("internalTransferRecordId", internalTransferRecord.getInternalTransferRecordId());
						} else {
							responseBean.put("error", "添加失败，系统错误");
							responseBean.setStatus(500);
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
