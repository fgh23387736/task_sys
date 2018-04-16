package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.Envaluate;


public interface EnvaluateDao {
	public Envaluate add(Envaluate envaluate);
	public void update(Envaluate envaluate);
	public void delete(Envaluate envaluate);
	public Envaluate getById(Integer id);
	public List<Envaluate> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
}
