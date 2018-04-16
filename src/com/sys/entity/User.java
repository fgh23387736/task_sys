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
@Table(name = "ts_user")
public class User {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer userId;
	
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
	
	@Column(name="release_task_number")
	private Integer releaseTaskNumber;
	
	@Column(name="receive_task_number")
	private Integer receiveTaskNumber;
	
	@Column(name="suspended_deadline")
	private Date suspenedDeadline;
	
	@Column(name="money")
	private Double money;
	
	@Column(name="good_envaluate_number")
	private Integer goodEnvaluateNumber;
	
	@Column(name="middle_envaluate_number")
	private Integer middleEnvaluateNumber;
	
	@Column(name="bad_envaluate_number")
	private Integer badEnvaluateNumber;
	
	@OneToMany(mappedBy="releaseUser",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Task> releaseTasks = new HashSet<Task>();
	
	@OneToMany(mappedBy="receiveUser",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Task> receiveTasks = new HashSet<Task>();
	
	@OneToMany(mappedBy="transactionUser",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<InternalTransferRecord> internalTransferRecords = new HashSet<InternalTransferRecord>();
	
	@OneToMany(mappedBy="transactionUser",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<SystemTransferRecord> systemTransferRecords = new HashSet<SystemTransferRecord>();
	
	@OneToMany(mappedBy="envaluateUser",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Envaluate> envalustes = new HashSet<Envaluate>();
	
	@OneToMany(mappedBy="sendUser",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<ChatRecord> chatSendUsers = new HashSet<ChatRecord>();
	
	@OneToMany(mappedBy="receiveUser",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<ChatRecord> chatReceiveUsers = new HashSet<ChatRecord>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<PrivateMessage> privateMessages = new HashSet<PrivateMessage>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<SuspendedRecord> suspendedRecords = new HashSet<SuspendedRecord>();
	
	@Transient
	private String rePassword;
	
	@Transient
	private String newPassword;
	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public User(Integer userId) {
		super();
		this.userId = userId;
	}


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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Set<SuspendedRecord> getSuspendedRecords() {
		return suspendedRecords;
	}

	public void setSuspendedRecords(Set<SuspendedRecord> suspendedRecords) {
		this.suspendedRecords = suspendedRecords;
	}

	public Set<PrivateMessage> getPrivateMessages() {
		return privateMessages;
	}

	public void setPrivateMessages(Set<PrivateMessage> privateMessages) {
		this.privateMessages = privateMessages;
	}

	

	public Set<ChatRecord> getChatSendUsers() {
		return chatSendUsers;
	}

	public void setChatSendUsers(Set<ChatRecord> chatSendUsers) {
		this.chatSendUsers = chatSendUsers;
	}

	public Set<ChatRecord> getChatReceiveUsers() {
		return chatReceiveUsers;
	}

	public void setChatReceiveUsers(Set<ChatRecord> chatReceiveUsers) {
		this.chatReceiveUsers = chatReceiveUsers;
	}

	public Set<Envaluate> getEnvalustes() {
		return envalustes;
	}

	public void setEnvalustes(Set<Envaluate> envalustes) {
		this.envalustes = envalustes;
	}

	public Set<Task> getReleaseTasks() {
		return releaseTasks;
	}

	public void setReleaseTasks(Set<Task> releaseTasks) {
		this.releaseTasks = releaseTasks;
	}

	public Set<Task> getReceiveTasks() {
		return receiveTasks;
	}

	public void setReceiveTasks(Set<Task> receiveTasks) {
		this.receiveTasks = receiveTasks;
	}

	public Set<InternalTransferRecord> getInternalTransferRecords() {
		return internalTransferRecords;
	}

	public void setInternalTransferRecords(
			Set<InternalTransferRecord> internalTransferRecords) {
		this.internalTransferRecords = internalTransferRecords;
	}

	public Set<SystemTransferRecord> getSystemTransferRecords() {
		return systemTransferRecords;
	}

	public void setSystemTransferRecords(
			Set<SystemTransferRecord> systemTransferRecords) {
		this.systemTransferRecords = systemTransferRecords;
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

	public Integer getReleaseTaskNumber() {
		return releaseTaskNumber;
	}

	public void setReleaseTaskNumber(Integer releaseTaskNumber) {
		this.releaseTaskNumber = releaseTaskNumber;
	}

	public Integer getReceiveTaskNumber() {
		return receiveTaskNumber;
	}

	public void setReceiveTaskNumber(Integer receiveTaskNumber) {
		this.receiveTaskNumber = receiveTaskNumber;
	}

	public Date getSuspenedDeadline() {
		return suspenedDeadline;
	}

	public void setSuspenedDeadline(Date suspenedDeadline) {
		this.suspenedDeadline = suspenedDeadline;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getGoodEnvaluateNumber() {
		return goodEnvaluateNumber;
	}

	public void setGoodEnvaluateNumber(Integer goodEnvaluateNumber) {
		this.goodEnvaluateNumber = goodEnvaluateNumber;
	}

	public Integer getMiddleEnvaluateNumber() {
		return middleEnvaluateNumber;
	}

	public void setMiddleEnvaluateNumber(Integer middleEnvaluateNumber) {
		this.middleEnvaluateNumber = middleEnvaluateNumber;
	}

	public Integer getBadEnvaluateNumber() {
		return badEnvaluateNumber;
	}

	public void setBadEnvaluateNumber(Integer badEnvaluateNumber) {
		this.badEnvaluateNumber = badEnvaluateNumber;
	}

	
	
	
}
