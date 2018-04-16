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
import com.sys.entity.SuspendedRecord;
import com.sys.entity.User;
import com.sys.service.SuspendedRecordService;
import com.sys.service.UserService;
import com.sys.util.PublicUtils;

@Component(value="suspendedRecordAction")
@Scope(value="prototype")
public class SuspendedRecordAction extends ActionSupport implements ModelDriven<SuspendedRecord>{
	
	@Autowired
	private SuspendedRecordService suspendedRecordService;
	
	@Autowired
	private UserService userService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	
	private SuspendedRecord suspendedRecord = new SuspendedRecord();
	
	
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
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

	public SuspendedRecord getSuspendedRecord() {
		return suspendedRecord;
	}

	public void setSuspendedRecord(SuspendedRecord suspendedRecord) {
		this.suspendedRecord = suspendedRecord;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public SuspendedRecordService getSuspendedRecordService() {
		return suspendedRecordService;
	}

	public void setSuspendedRecordService(SuspendedRecordService suspendedRecordService) {
		this.suspendedRecordService = suspendedRecordService;
	}

	@Override
	public SuspendedRecord getModel() {
		if(suspendedRecord == null){
			suspendedRecord = new SuspendedRecord();	
		}
		return suspendedRecord;
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
			Map<String, Object> map = suspendedRecordService.getAll(keys,page,pageSize);
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
			Map<String, Object> map = suspendedRecordService.getSuspendedRecordByIds(keys,page,pageSize,idsIntegers);
			
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
			Map<String, Object> map = suspendedRecordService.updateByIds(keys,idsIntegers,suspendedRecord,loginUser,loginAdmin);
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
			Map<String, Object> map = suspendedRecordService.deleteByIds(idsIntegers,loginUser,loginAdmin);
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
		if (loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			if(suspendedRecord.getUser() == null || suspendedRecord.getUser().getUserId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "用户不存在");
			}else{
				User theUser = userService.getById(suspendedRecord.getUser().getUserId());
				if(theUser == null){
					responseBean.setStatus(404);
					responseBean.put("error", "用户不存在");
				}else{
					suspendedRecord.setTime(new Date());
					suspendedRecord.setAdmin(loginAdmin);
					suspendedRecord = suspendedRecordService.add(suspendedRecord);
					if(suspendedRecord.getSuspendedRecordId() != null) {
						responseBean.setStatus(201);
						responseBean.put("suspendedRecordId", suspendedRecord.getSuspendedRecordId());
					} else {
						responseBean.put("error", "添加失败，系统错误");
						responseBean.setStatus(500);
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
