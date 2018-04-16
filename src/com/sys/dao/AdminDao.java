package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.Admin;


public interface AdminDao {
	public Admin add(Admin admin);
	public void update(Admin admin);
	public void delete(Admin admin);
	public Admin getById(Integer id);
	public List<Admin> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
	public DetachedCriteria getCriteriaByUserName(String userName);
}
