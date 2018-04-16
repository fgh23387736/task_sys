package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.InternalTransferRecord;


public interface InternalTransferRecordDao {
	public InternalTransferRecord add(InternalTransferRecord internalTransferRecord);
	public void update(InternalTransferRecord internalTransferRecord);
	public void delete(InternalTransferRecord internalTransferRecord);
	public InternalTransferRecord getById(Integer id);
	public List<InternalTransferRecord> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
}
