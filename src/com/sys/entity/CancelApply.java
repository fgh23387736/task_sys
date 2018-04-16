package com.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import oracle.sql.DATE;

@Entity
@Table(name = "ts_cancel_apply")
public class CancelApply {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer cancelApplyId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_id")
	private Task task;
	
	@Column(name="apply_type")
	private Integer applyType;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="type")
	private Integer type;
	
	@Column(name="deal_reason")
	private String dealReason;
	
	@Column(name="time")
	private Date time;
	
	@Column(name="deal_time")
	private Date dealTime;
	
	@Column(name="deal_type")
	private Integer dealType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="admin_id")
	private Admin admin;
	
	
	public Integer getCancelApplyId() {
		return cancelApplyId;
	}

	public void setTaskId(Integer cancelApplyId) {
		this.cancelApplyId = cancelApplyId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Integer getApplyType() {
		return applyType;
	}

	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDealReason() {
		return dealReason;
	}

	public void setDealReason(String dealReason) {
		this.dealReason = dealReason;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public void setCancelApplyId(Integer cancelApplyId) {
		this.cancelApplyId = cancelApplyId;
	}

	
	
}
