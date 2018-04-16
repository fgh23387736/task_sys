package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.SystemTransferRecordDao;
import com.sys.entity.SystemTransferRecord;


@Component(value="systemTransferRecordDaoImpl")
public class SystemTransferRecordDaoImpl implements SystemTransferRecordDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public SystemTransferRecord getById(Integer id) {
		return hibernateTemplate.get(SystemTransferRecord.class, id);
	}
	
	@Override
	public SystemTransferRecord add(SystemTransferRecord systemTransferRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(systemTransferRecord);
		return systemTransferRecord;
	}
	
	@Override
	public void update(SystemTransferRecord systemTransferRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(systemTransferRecord);
	}
	
	@Override
	public void delete(SystemTransferRecord systemTransferRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(systemTransferRecord);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<SystemTransferRecord> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<SystemTransferRecord> newSystemTransferRecords = new ArrayList<SystemTransferRecord>();
		if(page != null && pageSize != null){
			newSystemTransferRecords = (List<SystemTransferRecord>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newSystemTransferRecords = (List<SystemTransferRecord>) hibernateTemplate.findByCriteria(criteria);
		}
		return newSystemTransferRecords;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SystemTransferRecord.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("systemTransferRecordId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(SystemTransferRecord.class);
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByUser(
			SystemTransferRecord systemTransferRecord) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SystemTransferRecord.class);
		if(systemTransferRecord.getTransactionUser() != null){
			criteria.add(Property.forName("transactionUser").eq( systemTransferRecord.getTransactionUser() ) );
		}
		
		return criteria;
	}
	
	
	
	
}

