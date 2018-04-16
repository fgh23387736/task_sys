package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.Notice;


public interface NoticeDao {
	public Notice add(Notice notice);
	public void update(Notice notice);
	public void delete(Notice notice);
	public Notice getById(Integer id);
	public List<Notice> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
	public DetachedCriteria getCriteriaByTitle(Notice notice);
}
