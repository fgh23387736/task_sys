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
@Table(name = "ts_envaluate")
public class Envaluate {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer envaluateId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="envaluate_user_id")
	private User envaluateUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="be_envaluated_user_id")
	private User beEnvaluatedUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_id")
	private Task task;
	
	@Column(name="type")
	private Integer type;
	
	@Column(name="content")
	private String content;
	
	@Column(name="time")
	private Date time;
	
	
	
	public Integer getEnvaluateId() {
		return envaluateId;
	}

	public void setEnvaluateId(Integer envaluateId) {
		this.envaluateId = envaluateId;
	}

	public User getEnvaluateUser() {
		return envaluateUser;
	}

	public void setEnvaluateUser(User envaluateUser) {
		this.envaluateUser = envaluateUser;
	}

	public User getBeEnvaluatedUser() {
		return beEnvaluatedUser;
	}

	public void setBeEnvaluatedUser(User beEnvaluatedUser) {
		this.beEnvaluatedUser = beEnvaluatedUser;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
