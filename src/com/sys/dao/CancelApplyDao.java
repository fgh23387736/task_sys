package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.CancelApply;
import com.sys.entity.User;


public interface CancelApplyDao {
	public CancelApply add(CancelApply cancelApply);
	public void update(CancelApply cancelApply);
	public void delete(CancelApply cancelApply);
	public CancelApply getById(Integer id);
	public List<CancelApply> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
	public DetachedCriteria getCriteriaByTaskAndType(CancelApply cancelApply);
	public DetachedCriteria getCriteriaByTaskAndTypeForReleaseUser(
			CancelApply cancelApply, User user);
	public DetachedCriteria getCriteriaByTaskAndTypeForReceiveUser(
			CancelApply cancelApply, User user);
}
