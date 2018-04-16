package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.SuspendedRecordDao;
import com.sys.entity.SuspendedRecord;


@Component(value="suspendedRecordDaoImpl")
public class SuspendedRecordDaoImpl implements SuspendedRecordDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public SuspendedRecord getById(Integer id) {
		return hibernateTemplate.get(SuspendedRecord.class, id);
	}
	
	@Override
	public SuspendedRecord add(SuspendedRecord suspendedRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(suspendedRecord);
		return suspendedRecord;
	}
	
	@Override
	public void update(SuspendedRecord suspendedRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(suspendedRecord);
	}
	
	@Override
	public void delete(SuspendedRecord suspendedRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(suspendedRecord);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<SuspendedRecord> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<SuspendedRecord> newSuspendedRecords = new ArrayList<SuspendedRecord>();
		if(page != null && pageSize != null){
			newSuspendedRecords = (List<SuspendedRecord>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newSuspendedRecords = (List<SuspendedRecord>) hibernateTemplate.findByCriteria(criteria);
		}
		return newSuspendedRecords;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SuspendedRecord.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("suspendedRecordId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(SuspendedRecord.class);
		return criteria;
	}
	
	
	
	
}

