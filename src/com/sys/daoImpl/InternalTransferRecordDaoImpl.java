package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.InternalTransferRecordDao;
import com.sys.entity.InternalTransferRecord;


@Component(value="internalTransferRecordDaoImpl")
public class InternalTransferRecordDaoImpl implements InternalTransferRecordDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public InternalTransferRecord getById(Integer id) {
		return hibernateTemplate.get(InternalTransferRecord.class, id);
	}
	
	@Override
	public InternalTransferRecord add(InternalTransferRecord internalTransferRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(internalTransferRecord);
		return internalTransferRecord;
	}
	
	@Override
	public void update(InternalTransferRecord internalTransferRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(internalTransferRecord);
	}
	
	@Override
	public void delete(InternalTransferRecord internalTransferRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(internalTransferRecord);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<InternalTransferRecord> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<InternalTransferRecord> newInternalTransferRecords = new ArrayList<InternalTransferRecord>();
		if(page != null && pageSize != null){
			newInternalTransferRecords = (List<InternalTransferRecord>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newInternalTransferRecords = (List<InternalTransferRecord>) hibernateTemplate.findByCriteria(criteria);
		}
		return newInternalTransferRecords;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(InternalTransferRecord.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("internalTransferRecordId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(InternalTransferRecord.class);
		return criteria;
	}
	
	
	
	
}

