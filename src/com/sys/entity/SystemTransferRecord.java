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
@Table(name = "ts_system_transfer_record")
public class SystemTransferRecord {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer systemTransferRecordId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="transaction_user_id")
	private User transactionUser;
	
	@Column(name="type")
	private Integer type;
	
	@Column(name="money")
	private Double money;
	
	@Column(name="time")
	private Date time;
	
	
	
	public Integer getSystemTransferRecordId() {
		return systemTransferRecordId;
	}

	public void setSystemTransferRecordId(Integer systemTransferRecordId) {
		this.systemTransferRecordId = systemTransferRecordId;
	}

	public User getTransactionUser() {
		return transactionUser;
	}

	public void setTransactionUser(User transactionUser) {
		this.transactionUser = transactionUser;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
	
}
