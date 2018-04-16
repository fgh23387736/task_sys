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
import com.sys.entity.CancelApply;
import com.sys.entity.InternalTransferRecord;
import com.sys.entity.Task;
import com.sys.entity.User;
import com.sys.service.CancelApplyService;
import com.sys.service.InternalTransferRecordService;
import com.sys.service.TaskService;
import com.sys.util.PublicUtils;

@Component(value="cancelApplyAction")
@Scope(value="prototype")
public class CancelApplyAction extends ActionSupport implements ModelDriven<CancelApply>{
	
	@Autowired
	private CancelApplyService cancelApplyService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private InternalTransferRecordService internalTransferRecordService;
	
	private String ids;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String keys;
	private Integer userId;
	private CancelApply cancelApply = new CancelApply();
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public InternalTransferRecordService getInternalTransferRecordService() {
		return internalTransferRecordService;
	}

	public void setInternalTransferRecordService(
			InternalTransferRecordService internalTransferRecordService) {
		this.internalTransferRecordService = internalTransferRecordService;
	}

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

	public CancelApply getCancelApply() {
		return cancelApply;
	}

	public void setCancelApply(CancelApply cancelApply) {
		this.cancelApply = cancelApply;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}

	public CancelApplyService getCancelApplyService() {
		return cancelApplyService;
	}

	public void setCancelApplyService(CancelApplyService cancelApplyService) {
		this.cancelApplyService = cancelApplyService;
	}

	@Override
	public CancelApply getModel() {
		if(cancelApply == null){
			cancelApply = new CancelApply();	
		}
		return cancelApply;
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
			Map<String, Object> map = cancelApplyService.getAll(keys,page,pageSize);
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
			Map<String, Object> map = cancelApplyService.getCancelApplyByIds(keys,page,pageSize,idsIntegers);
			
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	public void getByTaskAndType(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			Map<String, Object> map = cancelApplyService.getCancelApplyByTaskAndType(keys,page,pageSize,cancelApply);
			
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	public void getByTaskAndTypeForReleaseUser(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			User theUser = new User();
			if(userId == null || userId == 0){
				theUser = loginUser;
			}
			Map<String, Object> map = cancelApplyService.getCancelApplyByTaskAndTypeForReleaseUser(keys,page,pageSize,cancelApply,theUser);
			
			responseBean.setObjMap(map);
			try {
				responseBean.writeTheMap();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	public void getByTaskAndTypeForReceiveUser(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权获取本信息");
		}else{
			User theUser = new User();
			if(userId == null || userId == 0){
				theUser = loginUser;
			}
			Map<String, Object> map = cancelApplyService.getCancelApplyByTaskAndTypeForReceiveUser(keys,page,pageSize,cancelApply,theUser);
			
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
			Map<String, Object> map = cancelApplyService.updateByIds(keys,idsIntegers,cancelApply,loginUser,loginAdmin);
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
			Map<String, Object> map = cancelApplyService.deleteByIds(idsIntegers,loginUser,loginAdmin);
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
			if(cancelApply.getTask() == null || cancelApply.getTask().getTaskId() == null){
				responseBean.setStatus(404);
				responseBean.put("error", "订单不存在");
			}else{
				Task theTask = taskService.getById(cancelApply.getTask().getTaskId());
				if(theTask == null){
					responseBean.setStatus(404);
					responseBean.put("error", "订单不存在");
				}else{
					User theReleaseUser = theTask.getReleaseUser();
					User theReceiveUser = theTask.getReceiveUser();
					boolean isOk = false;
					if(loginUser.getUserId().equals(theReleaseUser.getUserId()) ){
						cancelApply.setApplyType(0);
						isOk = true;
					}else if(loginUser.getUserId().equals(theReceiveUser.getUserId())){
						cancelApply.setApplyType(1);
						isOk = true;
					}else{
						responseBean.setStatus(401);
						responseBean.put("error", "您不具有权限");
					}
					if(isOk){
						cancelApply.setType(0);
						cancelApply.setTime(new Date());
						cancelApply = cancelApplyService.add(cancelApply);
						if(cancelApply.getCancelApplyId() != null) {
							responseBean.setStatus(201);
							responseBean.put("cancelApplyId", cancelApply.getCancelApplyId());
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
	
	public void agree(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		String loginType = (String)ServletActionContext.getRequest().getSession().getAttribute("type");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			CancelApply theCancelApply = cancelApplyService.getById(cancelApply.getCancelApplyId());
			if(theCancelApply == null){
				responseBean.setStatus(404);
				responseBean.put("error", "申请不存在");
			}else{
				Task theTask = theCancelApply.getTask();
				responseBean.setStatus(201);
				if(loginUser != null && loginType.equals("user")){
					if(theCancelApply.getType() != 0){
						responseBean.setStatus(403);
						responseBean.put("error", "请勿重复处理");
					}else{
						if((theCancelApply.getApplyType() == 0 && theTask.getReceiveUser().getUserId() == loginUser.getUserId())
								|| (theCancelApply.getApplyType() == 1 && theTask.getReleaseUser().getUserId() == loginUser.getUserId())){
							theCancelApply.setDealReason(cancelApply.getDealReason());
							theCancelApply.setDealTime(new Date());
							if(theTask.getReceiveUser().getUserId() == loginUser.getUserId()){
								theCancelApply.setDealType(1);
							}else{
								theCancelApply.setDealType(2);
							}
							theCancelApply.setType(1);
							cancelApplyService.update(theCancelApply);
							
							InternalTransferRecord theInternalTransferRecord = new InternalTransferRecord();
							theInternalTransferRecord.setTime(new Date());
							theInternalTransferRecord.setMoney(theTask.getPrice());
							theInternalTransferRecord.setType(0);
							theInternalTransferRecord.setTask(theTask);
							theInternalTransferRecord.setTransactionUser(theTask.getReleaseUser());
							theInternalTransferRecord.setReason(2);
							internalTransferRecordService.add(theInternalTransferRecord);
							
							theTask = taskService.getById(theTask.getTaskId());
							theTask.setState(3);
							taskService.update(theTask);
						}else{
							responseBean.setStatus(401);
							responseBean.put("error", "您不具有权限");
						}
					}
				}else if(loginAdmin != null && loginType.equals("admin")){
					if(theCancelApply.getType() == 1){
						responseBean.setStatus(403);
						responseBean.put("error", "请勿重复处理");
					}else{
						theCancelApply.setDealReason(cancelApply.getDealReason());
						theCancelApply.setDealTime(new Date());
						theCancelApply.setDealType(0);
						theCancelApply.setAdmin(loginAdmin);
						theCancelApply.setType(1);
						cancelApplyService.update(theCancelApply);
						
						InternalTransferRecord theInternalTransferRecord = new InternalTransferRecord();
						theInternalTransferRecord.setTime(new Date());
						theInternalTransferRecord.setMoney(theTask.getPrice());
						theInternalTransferRecord.setType(0);
						theInternalTransferRecord.setTask(theTask);
						theInternalTransferRecord.setTransactionUser(theTask.getReleaseUser());
						theInternalTransferRecord.setReason(2);
						internalTransferRecordService.add(theInternalTransferRecord);
						
						theTask = taskService.getById(theTask.getTaskId());
						theTask.setState(3);
						taskService.update(theTask);
					}
				}else{
					responseBean.setStatus(401);
					responseBean.put("error", "您不具有权限");
				}
			}
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void refuse(){
		ResponseBean responseBean = new ResponseBean();
		User loginUser = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		Admin loginAdmin = (Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		String loginType = (String)ServletActionContext.getRequest().getSession().getAttribute("type");
		if (loginUser == null && loginAdmin == null) {
			responseBean.setStatus(401);
			responseBean.put("error", "您还未登录，无权进行本操作");
		}else{
			CancelApply theCancelApply = cancelApplyService.getById(cancelApply.getCancelApplyId());
			if(theCancelApply == null){
				responseBean.setStatus(404);
				responseBean.put("error", "申请不存在");
			}else{
				Task theTask = theCancelApply.getTask();
				responseBean.setStatus(201);
				if(loginUser != null && loginType.equals("user")){
					if(theCancelApply.getType() != 0){
						responseBean.setStatus(403);
						responseBean.put("error", "请勿重复处理");
					}else{
						if((theCancelApply.getApplyType() == 0 && theTask.getReceiveUser().getUserId() == loginUser.getUserId())
								|| (theCancelApply.getApplyType() == 1 && theTask.getReleaseUser().getUserId() == loginUser.getUserId())){
							theCancelApply.setDealReason(cancelApply.getDealReason());
							theCancelApply.setDealTime(new Date());
							if(theTask.getReceiveUser().getUserId() == loginUser.getUserId()){
								theCancelApply.setDealType(1);
							}else{
								theCancelApply.setDealType(2);
							}
							theCancelApply.setType(2);
							cancelApplyService.update(theCancelApply);
							
						}else{
							responseBean.setStatus(401);
							responseBean.put("error", "您不具有权限");
						}
					}
				}else if(loginAdmin != null && loginType.equals("admin")){
					if(theCancelApply.getType() != 0){
						responseBean.setStatus(403);
						responseBean.put("error", "请勿重复处理");
					}else{
						theCancelApply.setDealReason(cancelApply.getDealReason());
						theCancelApply.setDealTime(new Date());
						theCancelApply.setDealType(0);
						theCancelApply.setAdmin(loginAdmin);
						theCancelApply.setType(2);
						cancelApplyService.update(theCancelApply);
					}
				}else{
					responseBean.setStatus(401);
					responseBean.put("error", "您不具有权限");
				}
			}
			
		}
		try {
			responseBean.writeTheMap();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
