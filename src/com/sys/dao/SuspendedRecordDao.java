package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.SuspendedRecord;


public interface SuspendedRecordDao {
	public SuspendedRecord add(SuspendedRecord suspendedRecord);
	public void update(SuspendedRecord suspendedRecord);
	public void delete(SuspendedRecord suspendedRecord);
	public SuspendedRecord getById(Integer id);
	public List<SuspendedRecord> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
}
