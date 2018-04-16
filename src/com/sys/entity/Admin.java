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
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "ts_admin")
public class Admin {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer adminId;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="name")
	private String name;
	
	@Column(name="head_img")
	private String headImg;
	
	@Column(name="phone")
	private String phone;
	
	@OneToMany(mappedBy="admin",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<CancelApply> cancelApply = new HashSet<CancelApply>();
	
	@OneToMany(mappedBy="admin",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<PrivateMessage> privateMessages = new HashSet<PrivateMessage>();
	
	@OneToMany(mappedBy="admin",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Notice> notices = new HashSet<Notice>();
	
	@OneToMany(mappedBy="admin",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<SuspendedRecord> suspendedRecords = new HashSet<SuspendedRecord>();
	
	
	@Transient
	private String rePassword;
	
	@Transient
	private String newPassword;
	
	
	
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Set<Notice> getNotices() {
		return notices;
	}

	public void setNotices(Set<Notice> notices) {
		this.notices = notices;
	}
	

	public Set<SuspendedRecord> getSuspendedRecords() {
		return suspendedRecords;
	}

	public void setSuspendedRecords(Set<SuspendedRecord> suspendedRecords) {
		this.suspendedRecords = suspendedRecords;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	public Set<PrivateMessage> getPrivateMessages() {
		return privateMessages;
	}

	public void setPrivateMessages(Set<PrivateMessage> privateMessages) {
		this.privateMessages = privateMessages;
	}

	public Set<CancelApply> getCancelApply() {
		return cancelApply;
	}

	public void setCancelApply(Set<CancelApply> cancelApply) {
		this.cancelApply = cancelApply;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
