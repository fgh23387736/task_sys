package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.EnvaluateDao;
import com.sys.entity.Envaluate;


@Component(value="envaluateDaoImpl")
public class EnvaluateDaoImpl implements EnvaluateDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Envaluate getById(Integer id) {
		return hibernateTemplate.get(Envaluate.class, id);
	}
	
	@Override
	public Envaluate add(Envaluate envaluate) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(envaluate);
		return envaluate;
	}
	
	@Override
	public void update(Envaluate envaluate) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(envaluate);
	}
	
	@Override
	public void delete(Envaluate envaluate) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(envaluate);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Envaluate> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Envaluate> newEnvaluates = new ArrayList<Envaluate>();
		if(page != null && pageSize != null){
			newEnvaluates = (List<Envaluate>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newEnvaluates = (List<Envaluate>) hibernateTemplate.findByCriteria(criteria);
		}
		return newEnvaluates;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Envaluate.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("envaluateId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Envaluate.class);
		return criteria;
	}
	
	
	
	
}

