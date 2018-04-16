package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.SystemTransferRecord;


public interface SystemTransferRecordDao {
	public SystemTransferRecord add(SystemTransferRecord systemTransferRecord);
	public void update(SystemTransferRecord systemTransferRecord);
	public void delete(SystemTransferRecord systemTransferRecord);
	public SystemTransferRecord getById(Integer id);
	public List<SystemTransferRecord> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
	public DetachedCriteria getCriteriaByUser(
			SystemTransferRecord systemTransferRecord);
}
