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
@Table(name = "ts_chat_record")
public class ChatRecord {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer chatRecordId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="send_user_id")
	private User sendUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="receive_user_id")
	private User receiveUser;
	
	@Column(name="content")
	private String content;
	
	@Column(name="is_read")
	private Boolean isRead;
	
	@Column(name="time")
	private Date time;
	
	
	
	public Integer getChatRecordId() {
		return chatRecordId;
	}

	public void setChatRecordId(Integer chatRecordId) {
		this.chatRecordId = chatRecordId;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}

	public User getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(User receiveUser) {
		this.receiveUser = receiveUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
	
}
