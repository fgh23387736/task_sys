package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.PrivateMessage;


public interface PrivateMessageDao {
	public PrivateMessage add(PrivateMessage privateMessage);
	public void update(PrivateMessage privateMessage);
	public void delete(PrivateMessage privateMessage);
	public PrivateMessage getById(Integer id);
	public List<PrivateMessage> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
	public DetachedCriteria getCriteriaByTitleAndUse(
			PrivateMessage privateMessage);
}
