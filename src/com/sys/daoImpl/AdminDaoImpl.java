package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.AdminDao;
import com.sys.entity.Admin;


@Component(value="adminDaoImpl")
public class AdminDaoImpl implements AdminDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Admin getById(Integer id) {
		return hibernateTemplate.get(Admin.class, id);
	}
	
	@Override
	public Admin add(Admin admin) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(admin);
		return admin;
	}
	
	@Override
	public void update(Admin admin) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(admin);
	}
	
	@Override
	public void delete(Admin admin) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(admin);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Admin> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Admin> newAdmins = new ArrayList<Admin>();
		if(page != null && pageSize != null){
			newAdmins = (List<Admin>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newAdmins = (List<Admin>) hibernateTemplate.findByCriteria(criteria);
		}
		return newAdmins;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Admin.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("adminId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Admin.class);
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByUserName(String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Admin.class);
		criteria.add(Property.forName("userName").eq( userName ) );
		return criteria;
	}
	
	
	
	
}

