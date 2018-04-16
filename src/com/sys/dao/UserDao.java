package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.User;


public interface UserDao {
	public User add(User user);
	public void update(User user);
	public void delete(User user);
	public User getById(Integer id);
	public List<User> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaByUserName(String userName);
	public DetachedCriteria getCriteriaForAll();
	public DetachedCriteria getByNameAndUserName(User user);
	public DetachedCriteria getCriteriaForSuspened();
}
