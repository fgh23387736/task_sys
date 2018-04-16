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
import com.sys.entity.User;
import com.sys.util.PublicUtils;
import com.sys.webSocket.WebSocket;

@Component(value="webSocketAction")
@Scope(value="prototype")
public class WebSocketAction extends ActionSupport{
	
	private String type;
	private Integer project;
	private String recordId;
	private String device;
	
	

	public String getDevice() {
		return device;
	}



	public void setDevice(String device) {
		this.device = device;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public Integer getProject() {
		return project;
	}



	public void setProject(Integer project) {
		this.project = project;
	}



	public String getRecordId() {
		return recordId;
	}



	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}



	@Override
	public String execute() throws Exception {
		System.out.println("action....");
		return NONE;
	}


	
	
	

}
