package com.sys.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "ts_suspended_record")
public class SuspendedRecord {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer suspendedRecordId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="admin_id")
	private Admin admin;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="time")
	private Date time;
	
	@Column(name="suspended_deadline")
	private Date suspendedDeadline;
	
	public Integer getSuspendedRecordId() {
		return suspendedRecordId;
	}

	public void setSuspendedRecordId(Integer suspendedRecordId) {
		this.suspendedRecordId = suspendedRecordId;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getSuspendedDeadline() {
		return suspendedDeadline;
	}

	public void setSuspendedDeadline(Date suspendedDeadline) {
		this.suspendedDeadline = suspendedDeadline;
	}
	
}
