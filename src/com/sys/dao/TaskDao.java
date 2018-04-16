package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.Task;


public interface TaskDao {
	public Task add(Task task);
	public void update(Task task);
	public void delete(Task task);
	public Task getById(Integer id);
	public List<Task> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
	public DetachedCriteria getCriteriaByReleaseUserAndReceiveUserAndState(
			Task task);
}
