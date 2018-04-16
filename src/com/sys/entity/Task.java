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

import oracle.sql.DATE;

@Entity
@Table(name = "ts_task")
public class Task {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer taskId;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="content")
	private String content;
	
	@Column(name="name")
	private String name;
	
	@Column(name="address")
	private String address;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="release_user_id")
	private User releaseUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="receive_user_id")
	private User receiveUser;
	
	@Column(name="state")
	private Integer state;
	
	@Column(name="release_is_confirm")
	private Boolean releaseIsConfirm;
	
	@Column(name="receive_is_confirm")
	private Boolean receiveIsConfirm;
	
	@Column(name="release_is_envaluate")
	private Boolean releaseIsEnvaluate;
	
	@Column(name="receive_is_envaluate")
	private Boolean receiveIsEnvaluate;
	
	@Column(name="release_time")
	private Date releaseTime;
	
	@Column(name="receive_time")
	private Date receiveTime;
	
	@Column(name="release_confirm_time")
	private Date releaseConfirmTime;
	
	@Column(name="receive_confirm_time")
	private Date receiveConfirmTime;
	
	@OneToMany(mappedBy="task",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<CancelApply> cancelApplys = new HashSet<CancelApply>();
	
	@OneToMany(mappedBy="task",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<InternalTransferRecord> internalTransferRecords = new HashSet<InternalTransferRecord>();
	
	@OneToMany(mappedBy="task",fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private Set<Envaluate> envaluates = new HashSet<Envaluate>();
	
	
	
	public Boolean getReleaseIsEnvaluate() {
		return releaseIsEnvaluate;
	}

	public void setReleaseIsEnvaluate(Boolean releaseIsEnvaluate) {
		this.releaseIsEnvaluate = releaseIsEnvaluate;
	}

	public Boolean getReceiveIsEnvaluate() {
		return receiveIsEnvaluate;
	}

	public void setReceiveIsEnvaluate(Boolean receiveIsEnvaluate) {
		this.receiveIsEnvaluate = receiveIsEnvaluate;
	}

	public Set<CancelApply> getCancelApplys() {
		return cancelApplys;
	}

	public void setCancelApplys(Set<CancelApply> cancelApplys) {
		this.cancelApplys = cancelApplys;
	}
	
	public Set<Envaluate> getEnvaluates() {
		return envaluates;
	}

	public void setEnvaluates(Set<Envaluate> envaluates) {
		this.envaluates = envaluates;
	}

	public Set<InternalTransferRecord> getInternalTransferRecords() {
		return internalTransferRecords;
	}

	public void setInternalTransferRecords(
			Set<InternalTransferRecord> internalTransferRecords) {
		this.internalTransferRecords = internalTransferRecords;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getReleaseUser() {
		return releaseUser;
	}

	public void setReleaseUser(User releaseUser) {
		this.releaseUser = releaseUser;
	}

	public User getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(User receiveUser) {
		this.receiveUser = receiveUser;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Boolean getReleaseIsConfirm() {
		return releaseIsConfirm;
	}

	public void setReleaseIsConfirm(Boolean releaseIsConfirm) {
		this.releaseIsConfirm = releaseIsConfirm;
	}

	public Boolean getReceiveIsConfirm() {
		return receiveIsConfirm;
	}

	public void setReceiveIsConfirm(Boolean receiveIsConfirm) {
		this.receiveIsConfirm = receiveIsConfirm;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getReleaseConfirmTime() {
		return releaseConfirmTime;
	}

	public void setReleaseConfirmTime(Date releaseConfirmTime) {
		this.releaseConfirmTime = releaseConfirmTime;
	}

	public Date getReceiveConfirmTime() {
		return receiveConfirmTime;
	}

	public void setReceiveConfirmTime(Date receiveConfirmTime) {
		this.receiveConfirmTime = receiveConfirmTime;
	}
	
	
}
